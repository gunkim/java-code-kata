package io.github.gunkim.engine.storage;

import io.github.gunkim.engine.storage.exception.StorageReadException;
import io.github.gunkim.engine.storage.exception.StorageWriteException;
import io.github.gunkim.engine.storage.serializer.JsonSerializer;
import io.github.gunkim.engine.storage.serializer.Serializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 해시 인덱스를 사용한 Key-Value 스토리지 구현체.
 * 파일에 데이터를 쓸 때 해당 키의 시작 위치를 해시맵에 저장하여, 읽기 성능을 크게 향상시킴.
 * 이 구현은 각 키의 파일 내 위치를 인덱싱하여 탐색 비용을 O(1)로 줄임.
 * 그러나, 파일에 쓰기 작업 시 기존에 존재하는 키에 대한 데이터를 덮어쓰지 않고 새로운 데이터를 파일 끝에 추가함.
 * 따라서, 파일 크기가 커질 수 있으며, 이는 저장 공간 효율성에 영향을 줄 수 있음. (해결을 위한 컴팩션은 이 클래스에서는 고려하지 않음)
 * RandomAccessFile을 사용하여, 저장된 위치로 직접 점프하여 데이터를 읽어오는 방식으로 효율적인 읽기를 구현함.
 * <p>
 * 인메모리에 모든 키가 저장된다는 조건을 전제로 고성능 읽기, 쓰기를 보장할 수 있다.
 */
public class HashIndexStorage<T> implements Storage<T> {
    private static final String SAVE_FILE_NAME = "database";
    private static final char END_CHAR = '\n';
    private static final String KEY_VALUE_SEPARATOR = ",";
    private static final String SAVE_ROW_FORMAT = "%s,%s" + END_CHAR;
    private static final int BACKUP_INTERVAL_MINUTES = 2;
    private static final Serializer jsonSerializer = new JsonSerializer();

    private final BackupManager<Map<String, Long>> backupManager = new BackupManager<>("./backup.ser");
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private final Map<String, Long> cache = new HashMap<>();
    private final String persistPath;

    HashIndexStorage(String path) {
        persistPath = "%s/%s".formatted(path, SAVE_FILE_NAME);

        load();
        scheduleBackup();
    }

    @Override
    public void save(String key, T value) {
        var file = new File(persistPath);

        long startPosition = file.length();
        try (var writer = new FileWriter(file, true)) {
            var jsonValue = jsonSerializer.serialize(value);
            var content = SAVE_ROW_FORMAT.formatted(key, jsonValue);

            writer.append(content);
            writer.flush();
            cache.put(key, startPosition);
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }

    @Override
    public Optional<T> find(String key) {
        long offset = cache.get(key);
        try (var file = new RandomAccessFile(persistPath, "r")) {
            file.seek(offset);

            String value = extractValueFrom(file.readLine());
            return Optional.ofNullable(jsonSerializer.deserialize(value));
        } catch (IOException e) {
            throw new StorageReadException(e.getMessage());
        }
    }

    private String extractValueFrom(String pair) {
        return pair.substring(pair.indexOf(KEY_VALUE_SEPARATOR) + 1);
    }

    private void scheduleBackup() {
        scheduler.scheduleAtFixedRate(() -> backupManager.backup(cache), BACKUP_INTERVAL_MINUTES, 1, TimeUnit.MINUTES);
    }

    private void load() {
        backupManager.restore().ifPresent(cache::putAll);
    }
}

package io.github.gunkim.storage;

import io.github.gunkim.storage.exception.StorageWriteException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * TODO: 백그라운드에서 sstable을 컴팩션하는 로직이 고려돼야 함.
 */
public class LsmTreeStorage<T> implements Storage<T> {
    private static final String SEGMENT_FILE_NAME = "database-%s";
    private static final String SSTABLE_METADATA_FILE_NAME = "sstable-metadata";
    private static final int THRESHOLD = 1;

    private final Map<String, T> memTable = new TreeMap<>();
    private final String path;

    public LsmTreeStorage(String path) {
        this.path = path;
    }

    @Override
    public void save(String key, T value) {
        if (memTable.size() == THRESHOLD) {
            flush();
        }
        memTable.put(key, value);
    }


    @Override
    public Optional<T> find(String key) {
        //TODO: memtable에 없을 경우 sstable에서 찾는 로직과 블룸 필터에 대한 로직이 고려돼야 함.
        return Optional.ofNullable(memTable.get(key));
    }

    private void flush() {
        var file = new File(path + SEGMENT_FILE_NAME.formatted(LocalDateTime.now().toString()));
        try (var fileWriter = new FileWriter(file, false)) {
            for (var entry : memTable.entrySet()) {
                String key = entry.getKey();
                T value = entry.getValue();

                var content = "%s,%s\n".formatted(key, value);
                fileWriter.append(content);
            }
            fileWriter.flush();
            flushMetadata(file.getName());
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
        memTable.clear();
    }

    private void flushMetadata(String fileName) {
        var file = new File(path + SSTABLE_METADATA_FILE_NAME);
        try (var fileWriter = new FileWriter(file, true)) {
            fileWriter.append(fileName).append("\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }
}

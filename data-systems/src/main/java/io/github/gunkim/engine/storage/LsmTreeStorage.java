package io.github.gunkim.engine.storage;

import io.github.gunkim.engine.storage.exception.StorageWriteException;
import io.github.gunkim.engine.storage.serializer.JsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * TODO: 백그라운드에서 sstable을 컴팩션하는 로직이 고려돼야 함.
 */
public class LsmTreeStorage<T> implements Storage<T> {
    private static final String SSTABLE_DIRECTORY_NAME = "/sstable";
    private static final String SSTABLE_FILE_NAME = SSTABLE_DIRECTORY_NAME + "/level-%d/sstable-%s";
    private static final String METADATA_DIRECTORY_NAME = "/metadata";
    private static final String SSTABLE_METADATA_FILE_NAME = METADATA_DIRECTORY_NAME + "/level0-metadata";
    private static final int THRESHOLD = 5;

    private final Map<String, T> memTable = new TreeMap<>();
    private final JsonSerializer jsonSerializer = new JsonSerializer();
    private final String path;

    public LsmTreeStorage(String path) {
        this.path = "%s/%s".formatted(path, "lsm-tree");
    }

    @Override
    public void save(String key, T value) {
        if (memTable.size() == THRESHOLD) {
            flush();
        }
        memTable.put(key, value);
    }

    //TODO: memtable에 없을 경우 sstable에서 찾는 로직과 블룸 필터에 대한 로직이 고려돼야 함.
    @Override
    public Optional<T> find(String key) {
        return Optional.ofNullable(memTable.get(key))
                .or(() -> findInSSTable(key));
    }

    /**
     * 컴팩션이 구현되면 레벨 0만이 아니라 레벨 1, 2, 3, ... 에 대해서도 탐색해야 함.
     * 탐색 로직을 조금 더 최적화할 필요가 있어보임.
     */
    private Optional<T> findInSSTable(String key) {
        var file = new File(path + SSTABLE_METADATA_FILE_NAME);
        if (file.exists()) {
            try (var lines = Files.lines(file.toPath())) {
                return lines
                        .map(line -> path + SSTABLE_DIRECTORY_NAME + "/level-0/" + line)
                        .map(File::new)
                        .filter(File::exists)
                        .map(File::toPath)
                        .flatMap(this::getPathByStringStream)
                        .filter(line -> line.startsWith("%s,".formatted(key)))
                        .reduce((previous, current) -> current)
                        .map(line -> line.substring(line.indexOf(',') + 1))
                        .map(jsonSerializer::deserialize);
            } catch (IOException e) {
                throw new StorageWriteException(e.getMessage());
            }
        }
        return Optional.empty();
    }

    private Stream<String> getPathByStringStream(Path path) {
        try {
            return Files.lines(path);
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }

    private void flush() {
        var file = new File(path + SSTABLE_FILE_NAME.formatted(0, generateIdentifier()));
        existsDirectory(file);

        try (var fileWriter = new FileWriter(file, false)) {
            for (var entry : memTable.entrySet()) {
                String key = entry.getKey();
                String value = jsonSerializer.serialize(entry.getValue());

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

    private void existsDirectory(File file) {
        var directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    private String generateIdentifier() {
        var identifierFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        return identifierFormat.format(new Date());
    }

    private void flushMetadata(String fileName) {
        var file = new File(path + SSTABLE_METADATA_FILE_NAME);
        existsDirectory(file);
        try (var fileWriter = new FileWriter(file, true)) {
            fileWriter.append(fileName).append("\n");
            fileWriter.flush();
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }
}

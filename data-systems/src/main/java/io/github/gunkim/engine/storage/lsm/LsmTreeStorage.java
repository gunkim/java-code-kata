package io.github.gunkim.engine.storage.lsm;

import io.github.gunkim.engine.storage.Storage;
import io.github.gunkim.engine.storage.exception.StorageReadException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * <p>LSM-Tree 개념 학습을 위해 구현하는 객체이기 때문에 동시성을 위한 동기화 로직은 고려하지 않는다.</p>
 */
public class LsmTreeStorage<T> implements Storage<T> {
    private static final int THRESHOLD = 5;

    private static final String ROOT_DIRECTORY_NAME = "/lsm-tree";
    private static final String SS_TABLE_DIRECTORY_RELATIVE_PATH = "/sstable/data/level-%d";
    private static final String SS_TABLE_FILE_BASE_NAME = "/sstable-%s";

    private final SortedMap<String, T> memTable = new TreeMap<>();
    private final CompationManager compationManager;
    private final String storagePath;

    private final FileSystemAccess<T> fileSystemAccess = new FileSystemAccess<>();

    public LsmTreeStorage(String path) {
        this.storagePath = path + ROOT_DIRECTORY_NAME;
        this.compationManager = new CompationManager(this.storagePath + SS_TABLE_DIRECTORY_RELATIVE_PATH, SS_TABLE_FILE_BASE_NAME);
    }

    @Override
    public void save(String key, T value) {
        runIfMemtableFull(() -> {
            flush();
            compationManager.start();
        });
        memTable.put(key, value);
    }

    //TODO: memtable에 없을 경우 블룸 필터(sstable에 해당 키값이 실제로 존재하긴 하는지)에 대한 로직이 고려돼야 함.
    @Override
    public Optional<T> find(String key) {
        return Optional.ofNullable(memTable.get(key))
                .or(() -> searchInSSTable(key));
    }

    private Optional<T> searchInSSTable(String key) {
        try {
            for (int level = 1; level <= CompationLevel.maxLevel().value(); level++) {
                var results = searchKeyInLevelSSTables(level, key);

                if (results.isPresent()) {
                    return results;
                }
            }
        } catch (IOException e) {
            throw new StorageReadException(e.getMessage());
        }
        return Optional.empty();
    }

    private Optional<T> searchKeyInLevelSSTables(int level, String key) throws IOException {
        var levelPath = Path.of(this.storagePath + SS_TABLE_DIRECTORY_RELATIVE_PATH.formatted(level));
        List<File> sstables = getSSTables(levelPath);

        if (sstables.isEmpty()) {
            return Optional.empty();
        }

        for (var sstable : sstables) {
            Optional<T> result = fileSystemAccess.get(sstable, key);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

    private List<File> getSSTables(Path path) throws IOException {
        if (!fileSystemAccess.existsPath(path)) {
            return List.of();
        }
        try (Stream<Path> paths = Files.list(path)) {
            return paths
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .toList();
        }
    }

    private void flush() {
        var ssTableFileName = SS_TABLE_FILE_BASE_NAME.formatted(generateIdentifier());
        var newFile = new File(this.storagePath + SS_TABLE_DIRECTORY_RELATIVE_PATH.formatted(1) + ssTableFileName);

        fileSystemAccess.existsDirectory(newFile);
        fileSystemAccess.flush(newFile, memTable);
        memTable.clear();
    }

    private String generateIdentifier() {
        return new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
    }

    private void runIfMemtableFull(Runnable runnable) {
        if (memTable.size() >= THRESHOLD) {
            runnable.run();
        }
    }
}

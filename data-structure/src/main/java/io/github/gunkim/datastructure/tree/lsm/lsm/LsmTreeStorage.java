package io.github.gunkim.datastructure.tree.lsm.lsm;

import io.github.gunkim.datastructure.tree.lsm.Storage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

/**
 * <p>LSM-Tree 개념 학습을 위해 구현하는 객체이기 때문에 동시성을 위한 동기화 로직은 고려하지 않는다.</p>
 */
public class LsmTreeStorage<T> implements Storage<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(LsmTreeStorage.class);

    private static final int MEMTABLE_THRESHOLD = 5;

    private static final String ROOT_DIRECTORY_NAME = "lsm-tree";
    private static final String SS_TABLE_DIRECTORY_RELATIVE_PATH = "/sstable/data/level-%d";
    private static final String SS_TABLE_FILE_BASE_NAME = "/sstable-%s";

    private static final ExecutorService executors = Executors.newSingleThreadExecutor();

    private final String storagePath;

    private final MemTable<T> memTable = new MemTable<>(MEMTABLE_THRESHOLD);
    private final Compation compation;
    private final FileSystemAccess<T> fileSystemAccess = new FileSystemAccess<>();

    public LsmTreeStorage(String path) {
        this.storagePath = path + ROOT_DIRECTORY_NAME;
        this.compation = new Compation(this.storagePath + SS_TABLE_DIRECTORY_RELATIVE_PATH, SS_TABLE_FILE_BASE_NAME);
    }

    @Override
    public void save(String key, T value) {
        runIfMemtableFull(() -> {
            LOGGER.info("Memtable 가득참");
            flush();
            compation();
        });
        memTable.add(key, value);
    }

    //TODO: memtable에 없을 경우 블룸 필터(sstable에 해당 키값이 실제로 존재하긴 하는지)에 대한 로직이 고려돼야 함.
    @Override
    public Optional<T> find(String key) {
        return Optional.ofNullable(memTable.get(key))
                .or(() -> searchInSSTable(key));
    }

    private Optional<T> searchInSSTable(String key) {
        LOGGER.info("Memtable에 해당 key(%S)가 존재하지 않아 SS-Table 탐색".formatted(key));
        for (int level = 1; level <= CompationLevel.maxLevel().value(); level++) {
            var results = searchKeyInLevelSSTables(CompationLevel.valueOf(level), key);

            if (results.isPresent()) {
                return results;
            }
        }
        return Optional.empty();
    }

    private Optional<T> searchKeyInLevelSSTables(CompationLevel level, String key) {
        var levelPath = Path.of(createDirectoryPath(level));

        for (var ssTable : ssTables(levelPath)) {
            Optional<T> result = fileSystemAccess.get(ssTable, key);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

    private List<File> ssTables(Path path) {
        try (Stream<Path> paths = fileSystemAccess.getDirectoryInFilePaths(path)) {
            return paths
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .filter(File::isFile)
                    .toList();
        }
    }

    private void flush() {
        LOGGER.info("Memtable 디스크 플러쉬 시작");
        var ssTableFileName = SS_TABLE_FILE_BASE_NAME.formatted(generateIdentifier());
        var newSSTableSavePath = createLevel1SSTableFilePath(ssTableFileName);

        fileSystemAccess.flush(newSSTableSavePath, memTable);
        memTable.clear();
        LOGGER.info("Memtable 디스크 플러쉬 완료");
    }

    private String generateIdentifier() {
        return new SimpleDateFormat("yyMMddHHmmssSSS").format(new Date());
    }

    private void runIfMemtableFull(Runnable runnable) {
        if (memTable.isFull()) {
            runnable.run();
        }
    }

    private void compation() {
        executors.execute(compation::start);
    }

    private String createLevel1SSTableFilePath(String fileName) {
        return createDirectoryPath(CompationLevel.LEVEL_1) + fileName;
    }

    private String createDirectoryPath(CompationLevel level) {
        return this.storagePath + SS_TABLE_DIRECTORY_RELATIVE_PATH.formatted(level.value());
    }
}

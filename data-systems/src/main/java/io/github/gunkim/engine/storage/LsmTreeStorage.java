package io.github.gunkim.engine.storage;

import io.github.gunkim.engine.storage.exception.StorageReadException;
import io.github.gunkim.engine.storage.exception.StorageWriteException;
import io.github.gunkim.engine.storage.serializer.JsonSerializer;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Stream;

/**
 * <p>LSM-Tree 개념 학습을 위해 구현하는 객체이기 때문에 동시성을 위한 동기화 로직은 고려하지 않는다.</p>
 */
public class LsmTreeStorage<T> implements Storage<T> {

    private static final int MAX_LEVEL = 6;
    private static final int THRESHOLD = 5;

    private static final String SS_TABLE_DIRECTORY_NAME = "/sstable";
    private static final String SS_TABLE_FILE_NAME = SS_TABLE_DIRECTORY_NAME + "/data/level-%d/sstable-%s";

    private final SortedMap<String, T> memTable = new TreeMap<>();
    private final JsonSerializer jsonSerializer = new JsonSerializer();
    private final String path;

    public LsmTreeStorage(String path) {
        this.path = "%s/%s".formatted(path, "lsm-tree");
    }

    @Override
    public void save(String key, T value) {
        runIfMemtableFull(() -> {
            flush();
            //TODO 인스턴스 변수로 제공하는게 좋을 듯.
            new CompationManager().start();
        });
        memTable.put(key, value);
    }

    //TODO: memtable에 없을 경우 블룸 필터(sstable에 해당 키값이 실제로 존재하긴 하는지)에 대한 로직이 고려돼야 함.
    @Override
    public Optional<T> find(String key) {
        return Optional.ofNullable(memTable.get(key))
                .or(() -> searchInSSTable(key));
    }

    /**
     * SS-Table 내에 해당 키가 존재하는지 검색한다.
     *
     * @param key
     * @return
     */
    private Optional<T> searchInSSTable(String key) {
        try {
            for (int level = 1; level <= MAX_LEVEL; level++) {
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

    /**
     * <p>해당 레벨의 SS-Table 내에 해당 키가 존재하는지 검색한다.</p>
     * <p>SS-Table들에서 Key가 중복될 가능성이 있으므로, 가장 최신의(시간 지역성) SS-Table을 우선적으로 검색한다.</p>
     *
     * @param level 해당 key를 검색하려는 ss-table 레벨
     * @param key   찾고자 하는 value의 key값
     * @return key의 해당하는 value
     */
    private Optional<T> searchKeyInLevelSSTables(int level, String key) throws IOException {
        //TODO: 해당 경로 값을 상수로 빼야 함.
        var levelPath = Path.of(this.path + "/sstable/data/level-%d".formatted(level));

        List<File> sstables = getSSTables(levelPath);

        if (sstables.isEmpty()) {
            return Optional.empty();
        }

        for (var sstable : sstables) {
            Optional<T> result = searchInFile(sstable, key);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }

    /**
     * <p>SS-Table 내에 해당 키가 존재하는지 검색한다.</p>
     * <p>단일 SS-Table 내의 Key값 Memtable(중복 없음)이 그대로 Flush되기 때문에 Key가 중복될 수 없다.</p>
     *
     * @param sstable 대상 SS-Table
     * @param key     찾고자하는 Value의 Key
     * @return 찾고자하는 Value
     * @throws IOException
     */
    private Optional<T> searchInFile(File sstable, String key) throws IOException {
        try (var fileReader = Files.newBufferedReader(sstable.toPath())) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                var seperator = line.indexOf(",");

                String savedKey = line.substring(0, seperator);
                if (savedKey.equals(key)) {
                    return Optional.of(jsonSerializer.deserialize(line.substring(seperator + 1)));
                }
            }
        }
        return Optional.empty();
    }

    /**
     * 해당 경로 내에 존재하는 모든 SS-Table을 가져온다.
     *
     * @param path
     * @return
     * @throws IOException
     */
    private List<File> getSSTables(Path path) throws IOException {
        if (!Files.exists(path)) {
            return Collections.emptyList();
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
        var file = new File(path + SS_TABLE_FILE_NAME.formatted(1, generateIdentifier()));
        existsDirectory(file);

        try (var fileWriter = new FileWriter(file, false)) {
            for (var entry : memTable.entrySet()) {
                String key = entry.getKey();
                String value = jsonSerializer.serialize(entry.getValue());

                var content = "%s,%s\n".formatted(key, value);
                fileWriter.append(content);
            }
            fileWriter.flush();
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

    private void runIfMemtableFull(Runnable runnable) {
        if (memTable.size() >= THRESHOLD) {
            runnable.run();
        }
    }
}

package io.github.gunkim.engine.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

public class CompationManager extends Thread {

    //TODO: 환경변수 값으로 받을 수 있어야 함.
    private final String path = "./lsm-tree/sstable/data/level-%d";

    @Override
    public void start() {
        for (Level level : Level.values()) {
            if (isCompactionRequired(level)) {
                compation(level);
            }
        }
    }

    private void compation(Level level) {
        var ssTables = ssTables(level);

        SortedMap<String, String> newSSTableCompatiningMap = new TreeMap<>();
        ssTables.forEach(ssTable -> compation(ssTable, newSSTableCompatiningMap));

        var newSSTablePath = String.format(path + "/sstable-%s", level.nextLevel(), generateIdentifier());

        existsDirectory(new File(newSSTablePath));
        try (var fileWriter = new FileWriter(newSSTablePath)) {
            for (Entry<String, String> stringStringEntry : newSSTableCompatiningMap.entrySet()) {
                fileWriter.write("%s,%s\n".formatted(stringStringEntry.getKey(), stringStringEntry.getValue()));
            }
            fileWriter.flush();
        } catch (IOException e) {
            //TODO 더 적절한 예외를 던지는게 좋을 것 같음.
            throw new RuntimeException(e);
        }

        ssTables.forEach(File::delete);
    }

    private String generateIdentifier() {
        var identifierFormat = new SimpleDateFormat("yyMMddHHmmssSSS");
        return identifierFormat.format(new Date());
    }

    private void compation(File ssTable, SortedMap<String, String> newSSTableCompatiningMap) {
        try (var fileReader = Files.newBufferedReader(ssTable.toPath())) {
            String line;
            while ((line = fileReader.readLine()) != null) {
                var seperator = line.indexOf(",");

                String key = line.substring(0, seperator);
                String value = line.substring(seperator + 1);

                newSSTableCompatiningMap.put(key, value);
            }
        } catch (IOException e) {
            //TODO 더 적절한 예외를 던지는게 좋을 것 같음.
            throw new RuntimeException(e);
        }
    }


    private boolean isCompactionRequired(Level level) {
        var ssTableCount = ssTables(level).size();

        return level.isCompactionRequired(ssTableCount);
    }

    private List<File> ssTables(Level level) {
        var path = level.ssTablePath(this.path);

        try {
            var files = Files.list(Path.of(path));
            return files
                    .sorted()
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .toList();
        } catch (IOException e) {
            return List.of();
        }
    }

    private void existsDirectory(File file) {
        var directory = file.getParentFile();
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    enum Level {
        LEVEL_1(1, 2),
        LEVEL_2(2, 4),
        LEVEL_3(3, 8),
        LEVEL_4(4, 16),
        LEVEL_5(5, 32),
        LEVEL_6(6, 64);

        private final int level;
        //SS-Table의 갯수가 threshold를 넘어가면 컴팩션을 수행함.
        private final int threshold;

        Level(int level, int threshold) {
            this.level = level;
            this.threshold = threshold;
        }

        public int level() {
            return level;
        }

        public int nextLevel() {
            if (level == 6) {
                //TODO 더 적절한 예외를 던지는게 좋을 것 같음.
                throw new RuntimeException("최대 레벨입니다.");
            }
            return level + 1;
        }

        public int threshold() {
            return threshold;
        }

        public String ssTablePath(String basePath) {
            return String.format(basePath, level);
        }

        public boolean isCompactionRequired(long ssTableCount) {
            return ssTableCount >= threshold;
        }
    }
}

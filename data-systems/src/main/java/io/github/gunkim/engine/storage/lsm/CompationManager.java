package io.github.gunkim.engine.storage.lsm;

import io.github.gunkim.engine.storage.exception.CompactionFailedException;

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
    private final String basePath;
    private final String ssTableFileBaseName;

    public CompationManager(String basePath, String ssTableFileBaseName) {
        this.basePath = basePath;
        this.ssTableFileBaseName = ssTableFileBaseName;
    }

    @Override
    public void start() {
        for (CompationLevel level : CompationLevel.values()) {
            if (isCompactionRequired(level)) {
                compation(level);
            }
        }
    }

    private void compation(CompationLevel level) {
        var ssTables = ssTables(level);

        SortedMap<String, String> newSSTableCompatiningMap = new TreeMap<>();
        ssTables.forEach(ssTable -> compation(ssTable, newSSTableCompatiningMap));

        var newSSTablePath = String.format(basePath + ssTableFileBaseName, level.nextLevel(), generateIdentifier());

        existsDirectory(new File(newSSTablePath));
        try (var fileWriter = new FileWriter(newSSTablePath)) {
            for (Entry<String, String> stringStringEntry : newSSTableCompatiningMap.entrySet()) {
                fileWriter.write("%s,%s\n".formatted(stringStringEntry.getKey(), stringStringEntry.getValue()));
            }
            fileWriter.flush();
        } catch (IOException e) {
            throw new CompactionFailedException("level-%d 컴팩션에 실패했습니다.".formatted(level.value()), e);
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
            throw new CompactionFailedException("%s SS-Table을 읽어오는 과정에서 문제가 발생했습니다.".formatted(ssTable.getName()), e);
        }
    }


    private boolean isCompactionRequired(CompationLevel level) {
        var ssTableCount = ssTables(level).size();

        return level.isCompactionRequired(ssTableCount);
    }

    private List<File> ssTables(CompationLevel level) {
        var path = level.ssTablePath(this.basePath);

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
}

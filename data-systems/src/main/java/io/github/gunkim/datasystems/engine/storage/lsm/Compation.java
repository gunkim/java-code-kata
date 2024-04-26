package io.github.gunkim.datasystems.engine.storage.lsm;

import io.github.gunkim.datasystems.engine.storage.exception.CompactionFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

public class Compation {
    private static final Logger LOGGER = LoggerFactory.getLogger(Compation.class);

    private final String basePath;
    private final String ssTableFileBaseName;

    public Compation(String basePath, String ssTableFileBaseName) {
        this.basePath = basePath;
        this.ssTableFileBaseName = ssTableFileBaseName;
    }

    public void start() {
        for (CompationLevel level : CompationLevel.values()) {
            if (!isCompactionRequired(level)) {
                return;
            }
            LOGGER.info("Level-%d의 컴팩션 시작".formatted(level.value()));
            compation(level);
            LOGGER.info("Level-%d의 컴팩션 완료".formatted(level.value()));
        }
    }

    private void compation(CompationLevel level) {
        var ssTables = ssTables(level);

        SortedMap<String, String> newSSTableCompatiningMap = new TreeMap<>();
        ssTables.forEach(ssTable -> compation(ssTable, newSSTableCompatiningMap));

        var newSSTablePath = String.format(
                basePath + ssTableFileBaseName,
                level.nextLevel(), generateIdentifier()
        );

        existsDirectory(new File(newSSTablePath));
        try (var fileWriter = new FileWriter(newSSTablePath)) {
            for (Entry<String, String> stringStringEntry : newSSTableCompatiningMap.entrySet()) {
                fileWriter.write("%s,%s\n".formatted(stringStringEntry.getKey(), stringStringEntry.getValue()));
            }
            fileWriter.flush();
        } catch (IOException e) {
            throw new CompactionFailedException("level-%d 컴팩션에 실패했습니다.".formatted(level.value()), e);
        }

        //TODO: 병합이 완료된 SS-Table을 바로 삭제할 경우 이를 참조하는 다른 스레드에서 해당 파일에 접근할 경우 NoSuchFileException 예외를 발생시킬 수 있다.
        //TODO: 우선 삭제가 아니라 마킹을 해둔 후 별도의 스레드를 스케줄링하여 삭제하는게 더 안전할 수 있다.
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

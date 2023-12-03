package io.github.gunkim.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 단순한 Key-Value 스토리지 구현체
 * 파일 쓰기 자체는 효율적이며, 데이터를 파일의 끝에 추가함.
 * 그러나, 읽기 작업은 전체 파일을 순차적으로 스캔하기 때문에 레코드가 많을 경우 성능이 저하될 수 있음.
 * 탐색 비용은 최소 O(N) 이며, 각 키에 대해 별도의 탐색이 필요할 경우 총 탐색 비용은 O(kN)이 될 수 있음 (k는 탐색 횟수).
 */
public class SimpleStorage extends Storage {
    private static final String SAVE_FILE_NAME = "database";
    private static final String SAVE_ROW_FORMAT = "%s,%s\n";
    private static final Gson gson = new Gson();

    public SimpleStorage(String path) {
        super(path);
    }

    @Override
    public void save(String key, Map<String, Object> value) {
        try (var writer = new FileWriter(persistPath(), true)) {
            var jsonValue = gson.toJson(value);
            var content = SAVE_ROW_FORMAT.formatted(key, jsonValue);

            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Map<String, Object>> find(String key) {
        try (Stream<String> lines = Files.lines(Paths.get(persistPath()))) {
            return lines
                    .filter(line -> line.startsWith("%s,".formatted(key)))
                    .reduce((previous, current) -> current)
                    .map(line -> line.substring(line.indexOf(',') + 1))
                    .map(json -> gson.fromJson(json, new TypeToken<Map<String, Object>>() {
                    }.getType()));
        } catch (IOException e) {
            throw new RuntimeException("Error reading file", e);
        }
    }

    private String persistPath() {
        return "%s/%s".formatted(storagePath(), SAVE_FILE_NAME);
    }
}

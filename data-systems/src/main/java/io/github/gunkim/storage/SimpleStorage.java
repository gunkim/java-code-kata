package io.github.gunkim.storage;

import io.github.gunkim.storage.exception.StorageReadException;
import io.github.gunkim.storage.exception.StorageWriteException;
import io.github.gunkim.storage.serializer.JsonSerializer;
import io.github.gunkim.storage.serializer.Serializer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * 단순한 Key-Value 스토리지 구현체
 * 파일 쓰기 자체는 효율적이며, 데이터를 파일의 끝에 추가함.
 * 그러나, 읽기 작업은 전체 파일을 순차적으로 스캔하기 때문에 레코드가 많을 경우 성능이 저하될 수 있음. (컴팩션을 통해 레코드 수를 줄이는 방법을 고려해볼 수 있음)
 * 탐색 비용은 최소 O(N) 이며, 각 키에 대해 별도의 탐색이 필요할 경우 총 탐색 비용은 O(kN)이 될 수 있음 (k는 탐색 횟수).
 */
public class SimpleStorage<T> implements Storage<T> {
    private static final String SAVE_FILE_NAME = "database";
    private static final String SAVE_ROW_FORMAT = "%s,%s\n";
    private static final Serializer jsonSerializer = new JsonSerializer();

    private final String persistPath;

    SimpleStorage(String path) {
        this.persistPath = "%s/%s".formatted(path, SAVE_FILE_NAME);
    }

    @Override
    public void save(String key, T value) {
        try (var writer = new FileWriter(persistPath, true)) {
            var jsonValue = jsonSerializer.serialize(value);
            var content = SAVE_ROW_FORMAT.formatted(key, jsonValue);

            writer.append(content);
            writer.flush();
        } catch (IOException e) {
            throw new StorageWriteException(e.getMessage());
        }
    }

    public Optional<T> find(String key) {
        try (Stream<String> lines = Files.lines(Paths.get(persistPath))) {
            return lines
                    .filter(line -> line.startsWith("%s,".formatted(key)))
                    .reduce((previous, current) -> current)
                    .map(line -> line.substring(line.indexOf(',') + 1))
                    .map(jsonSerializer::deserialize);
        } catch (IOException e) {
            throw new StorageReadException(e.getMessage());
        }
    }
}

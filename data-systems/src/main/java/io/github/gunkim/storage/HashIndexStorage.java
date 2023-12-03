package io.github.gunkim.storage;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 해시 인덱스를 사용한 Key-Value 스토리지 구현체.
 * 파일에 데이터를 쓸 때 해당 키의 시작 위치를 해시맵에 저장하여, 읽기 성능을 크게 향상시킴.
 * 이 구현은 각 키의 파일 내 위치를 인덱싱하여 탐색 비용을 O(1)로 줄임.
 * 그러나, 파일에 쓰기 작업 시 기존에 존재하는 키에 대한 데이터를 덮어쓰지 않고 새로운 데이터를 파일 끝에 추가함.
 * 따라서, 파일 크기가 커질 수 있으며, 이는 저장 공간 효율성에 영향을 줄 수 있음. (이 부분은 컴팩션을 고려해야 함.)
 * RandomAccessFile을 사용하여, 저장된 위치로 직접 점프하여 데이터를 읽어오는 방식으로 효율적인 읽기를 구현함.
 * <p>
 * 인메모리에 모든 키가 저장된다는 조건을 전제로 고성능 읽기, 쓰기를 보장할 수 있다.
 * 하지만 메모리는 휘발성이기 때문에 재시작 시 재색인에 대한 부분은 고려되지 않았다.
 */
public class HashIndexStorage extends Storage {
    private static final String SAVE_FILE_NAME = "database";
    private static final String SAVE_ROW_FORMAT = "%s,%s\n";
    private static final Gson gson = new Gson();

    private final Map<String, Long> map = new HashMap<>();

    public HashIndexStorage(String path) {
        super(path);
    }

    @Override
    public void save(String key, Map<String, Object> value) {
        File file = new File(persistPath());

        long startPosition = file.length();
        try (var writer = new FileWriter(file, true)) {
            var jsonValue = gson.toJson(value);
            var content = SAVE_ROW_FORMAT.formatted(key, jsonValue);

            writer.append(content);
            writer.flush();
            map.put(key, startPosition);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Map<String, Object>> find(String key) {
        long offset = map.get(key);
        try (RandomAccessFile file = new RandomAccessFile(persistPath(), "r")) {
            file.seek(offset);

            StringBuilder data = new StringBuilder();
            int b;
            while ((b = file.read()) != -1) {
                char c = (char) b;
                data.append(c);

                if (c == '\n') {
                    break;
                }
            }
            var json = data.substring(data.indexOf(",") + 1);
            var type = new TypeToken<HashMap<String, Object>>() {
            }.getType();
            return Optional.ofNullable(gson.fromJson(json, type));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    private String persistPath() {
        return "%s/%s".formatted(storagePath(), SAVE_FILE_NAME);
    }
}

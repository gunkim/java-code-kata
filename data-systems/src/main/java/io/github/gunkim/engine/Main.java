package io.github.gunkim.engine;

import io.github.gunkim.engine.storage.lsm.LsmTreeStorageFactory;
import io.github.gunkim.engine.storage.Storage;
import io.github.gunkim.engine.storage.StorageFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Main {
    private static final String SAVE_PATH = "./";

    public static void main(String[] args) {
        StorageFactory<Map<String, Object>> storageFactory = new LsmTreeStorageFactory<>();
        Storage<Map<String, Object>> storage = storageFactory.createSimpleStorage(SAVE_PATH);

        IntStream.rangeClosed(1, 3500).forEach(i -> {
            storage.save(String.valueOf(i), Map.of(
                    "name", "London",
                    "attractions", List.of("Big Ben", "London Eye3")
            ));
        });

        StopWatch.run(() -> {
            var data = storage.find("101");
            data.ifPresent(System.out::println);
        });
    }
}

package io.github.gunkim;

import io.github.gunkim.storage.HashIndexStorageFactory;
import io.github.gunkim.storage.Storage;
import io.github.gunkim.storage.StorageFactory;

import java.util.List;
import java.util.Map;

public class Main {
    private static final String SAVE_PATH = "./";

    public static void main(String[] args) throws InterruptedException {
        StorageFactory<Map<String, Object>> storageFactory = new HashIndexStorageFactory<>();
        Storage<Map<String, Object>> storage = storageFactory.createSimpleStorage(SAVE_PATH);

//        storage.save("123456", Map.of(
//                "name", "London",
//                "attractions", List.of("Big Ben", "London Eye3")
//        ));

        Thread.sleep(2_000);

        stopwatch(() -> {
            var data = storage.find("123456");
            data.ifPresent(System.out::println);
        });
    }

    private static void stopwatch(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        long milliseconds = duration / 1_000_000;
        System.out.printf("Execution time: %d milliseconds\n", milliseconds);
    }
}
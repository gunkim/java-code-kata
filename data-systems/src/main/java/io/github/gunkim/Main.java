package io.github.gunkim;

import io.github.gunkim.storage.SimpleStorage;
import io.github.gunkim.storage.Storage;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        var savePath = "./";
        Storage simple = new SimpleStorage(savePath);
        simple.save("123456", Map.of(
                "name", "London",
                "attractions", List.of("Big Ben", "London Eye3")
        ));

        stopwatch(() -> {
            var data = simple.find("123456");
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
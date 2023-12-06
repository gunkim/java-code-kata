package io.github.gunkim;

public class StopWatch {
    public static void run(Runnable runnable) {
        long startTime = System.nanoTime();
        runnable.run();
        long endTime = System.nanoTime();

        long duration = (endTime - startTime);

        long milliseconds = duration / 1_000_000;
        System.out.printf("Execution time: %d milliseconds\n", milliseconds);
    }
}

package io.github.gunkim.ratelimiter.bucket;

import io.github.gunkim.ratelimiter.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ## 처리 제한 알고리즘, 누출 버킷
 * - 요청이 들어오면 Queue에 요청을 적재 (보통 Queue 사이즈는 버킷 사이즈)
 * - Queue가 가득 찼다면 요청은 Drop (대기시키도록 구현도 고려해볼 순 있음
 * - 일정 시간마다 일정량을 처리
 * <p>
 * 고정된 처리율로 안정적인 트래픽 처리엔 좋지만, 트래픽이 몰리게 되면 요청이 버려질 수 있음.
 */
public class LeakyRateLimiterRateLimiter implements AutoCloseable, RateLimiter {
    private static final Logger logger = LoggerFactory.getLogger(LeakyRateLimiterRateLimiter.class);

    private final Queue<Runnable> queue;
    private final int bucketSize;
    private final int batchSize;

    private final ScheduledExecutorService executorService;

    public LeakyRateLimiterRateLimiter(int bucketSize, long scheduleInterval, int batchSize) {
        this.bucketSize = bucketSize;
        this.batchSize = batchSize;

        this.queue = new ConcurrentLinkedQueue<>();

        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.executorService.scheduleAtFixedRate(this::processRequest, scheduleInterval, scheduleInterval, TimeUnit.MILLISECONDS);
    }

    @Override
    public void handleRequest(Runnable request) {
        if (isFull()) {
            logger.trace("bucket is full!");
            return;
        }
        queue.add(request);
    }

    private boolean isFull() {
        return queue.size() == bucketSize - 1;
    }

    private void processRequest() {
        int queueSize = queue.size();

        logger.debug("전체 {}개 중 {}개 처리", queueSize, this.batchSize);
        for (int i = 0; i < batchSize; i++) {
            queue.poll().run();
        }
    }

    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(1, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException ex) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void close() {
        shutdown();
    }
}

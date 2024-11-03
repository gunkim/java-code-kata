package io.github.gunkim.ratelimiter.bucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ## 처리 제한 알고리즘, 토큰 버킷
 * - 요청이 들어오면 토큰이 존재한다면 처리된다.
 * - 토큰이 부족하다면 거부된다.
 * - 일정 시간마다 토큰을 버킷 크기만큼 리필한다.
 * <p>
 * 구현이 쉬우며 많은 기업이 이용하고 있다. 하지만 안정적인 처리를 보장하진 못한다.
 * 예를 들어, 초반에 트래픽이 몰려 모든 토큰이 소비된다면 이후 요청은 토큰이 리필될 때까지 처리되지 못한다.
 */
public class TokenBucketRateLimiter implements AutoCloseable, Bucket {
    private static final Logger logger = LoggerFactory.getLogger(TokenBucketRateLimiter.class);

    private final ScheduledExecutorService executorService;
    private final AtomicInteger tokens;

    public TokenBucketRateLimiter(int bucketSize, long refillRate) {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.tokens = new AtomicInteger(bucketSize);

        this.executorService.scheduleAtFixedRate(() -> refillTokens(bucketSize), refillRate, refillRate, TimeUnit.MILLISECONDS);

        logger.info("Token bucket created with size: {} and refill rate: {}", bucketSize, refillRate);
    }

    @Override
    public void request(Runnable request) {
        int currentTokens;
        do {
            currentTokens = tokens.get();
            if (currentTokens <= 0) {
                logger.debug("Dropped request");
                return;
            }
        } while (!tokens.compareAndSet(currentTokens, currentTokens - 1));

        request.run();
        logger.debug("Request processed. Remaining tokens: {}", tokens.get());
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

    private void refillTokens(int bucketSize) {
        int currentTokens;
        do {
            currentTokens = tokens.get();
            if (currentTokens >= bucketSize) {
                return;
            }
        } while (!tokens.compareAndSet(currentTokens, bucketSize));
        logger.debug("Refilled tokens: {}", tokens.get());
    }
}
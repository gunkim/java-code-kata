package io.github.gunkim.ratelimiter.tokenbucket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class TokenBucket implements AutoCloseable {
    private static final Logger logger = LoggerFactory.getLogger(TokenBucket.class);

    private final ScheduledExecutorService executorService;
    private final AtomicInteger tokens;

    public TokenBucket(int bucketSize, long refillRate) {
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.tokens = new AtomicInteger(bucketSize);

        this.executorService.scheduleAtFixedRate(() -> refillTokens(bucketSize), refillRate, refillRate, TimeUnit.MILLISECONDS);

        logger.info("Token bucket created with size: {} and refill rate: {}", bucketSize, refillRate);
    }

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
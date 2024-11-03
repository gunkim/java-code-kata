package io.github.gunkim.ratelimiter.window;

import io.github.gunkim.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FixedWindowRateLimiter implements RateLimiter {
    private final int maxRequestsPerWindow;
    private final int windowDurationMillis;
    private final ConcurrentHashMap<Long, AtomicInteger> requestCounts = new ConcurrentHashMap<>();

    public FixedWindowRateLimiter(int maxRequestsPerWindow, int windowDurationMillis) {
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.windowDurationMillis = windowDurationMillis;
    }

    @Override
    public void handleRequest(Runnable request) {
        long currentTimeMillis = Instant.now().toEpochMilli();
        long currentWindowKey = currentTimeMillis / windowDurationMillis;

        cleanupOldWindows(currentWindowKey);

        AtomicInteger currentWindowRequestCount = getOrCreateRequestCounterForWindow(currentWindowKey);
        currentWindowRequestCount.incrementAndGet();

        if (currentWindowRequestCount.get() <= maxRequestsPerWindow) {
            request.run();
        } else {
            currentWindowRequestCount.decrementAndGet();
        }
    }

    private AtomicInteger getOrCreateRequestCounterForWindow(long currentWindowKey) {
        return requestCounts.computeIfAbsent(currentWindowKey, k -> new AtomicInteger(0));
    }

    private void cleanupOldWindows(long currentWindowKey) {
        requestCounts.forEach((windowKey, count) -> {
            if (windowKey < currentWindowKey) {
                requestCounts.remove(windowKey);
            }
        });
    }
}
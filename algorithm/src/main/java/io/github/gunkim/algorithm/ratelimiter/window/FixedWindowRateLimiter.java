package io.github.gunkim.algorithm.ratelimiter.window;

import io.github.gunkim.algorithm.ratelimiter.RateLimiter;

import java.time.Instant;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ## 처리 제한 알고리즘, 고정 윈도우 카운터
 * - 고정된 간격의 윈도 내의 요청은 지정된 임계치 만큼 처리한다.
 * - 임계치를 넘어선 요청은 새 윈도가 열릴 때까지 버려진다.
 * <p>
 * 윈도 경계 부근에 순간적으로 많은 트래픽이 집중된다면 할당된 임계치보다 많은 요청이 처리될 수 있는 문제가 있다.
 * 예를 들어,
 * - 윈도 크기가 1분이고 임계치가 100이라고 가정할 때,
 * - 12:00:30에 100개의 요청이 처리되고,
 * - 12:01:00에 윈도가 새로 열리면서 다시 100개의 요청이 처리될 수 있다.
 * - 이러한 경우 12:00:30에서 12:01:00 사이에 200개의 요청이 처리된 것이다.
 */
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
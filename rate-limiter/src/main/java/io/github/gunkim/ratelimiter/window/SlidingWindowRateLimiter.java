package io.github.gunkim.ratelimiter.window;

import io.github.gunkim.ratelimiter.RateLimiter;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ## 처리 제한 알고리즘, 슬라이딩 윈도우 카운터
 * - 슬라이딩 윈도우 기법을 사용하여 고정된 간격의 윈도 내에서 요청을 제한한다.
 * - 현재 윈도와 이전 윈도의 요청을 샘플링하고, 가중치를 부여하여 전체 요청 수를 계산한다.
 * - 임계치를 넘어선 요청은 거부된다.
 * <p>
 * 이 방식을 사용하면 윈도 경계에서의 집중된 트래픽 문제를 완화할 수 있다.
 * 예를 들어,
 * - 윈도 크기가 1분이고 임계치가 100이라고 가정할 때,
 * - 12:00:30에서 12:01:30까지의 1분간의 요청 중 일부는 새로운 윈도로 넘어가서 가중치를 부여받는다.
 * - 이는 고정된 윈도우 카운터에서 발생할 수 있는 200개의 요청이 처리되는 문제를 감소시킨다.
 */
public class SlidingWindowRateLimiter implements RateLimiter {
    private final int windowDurationMillis;
    private final int maxRequestsPerWindow;
    private final TimeProvider timeProvider;

    private final ConcurrentHashMap<Long, AtomicInteger> windowCounterMap = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(int windowDurationMillis, int maxRequestsPerWindow, TimeProvider timeProvider) {
        this.windowDurationMillis = windowDurationMillis;
        this.maxRequestsPerWindow = maxRequestsPerWindow;
        this.timeProvider = timeProvider;
    }

    @Override
    public void handleRequest(Runnable request) {
        long currentMillis = timeProvider.getCurrentTimeMillis();
        long currentWindowKey = currentMillis / windowDurationMillis;
        long previousWindowKey = currentWindowKey - 1;

        double elapsedRatio = (double) (currentMillis % windowDurationMillis) / windowDurationMillis;

        AtomicInteger currentCounter = windowCounterMap.computeIfAbsent(currentWindowKey, k -> new AtomicInteger(0));
        AtomicInteger previousCounter = windowCounterMap.getOrDefault(previousWindowKey, new AtomicInteger(0));

        // 이전 윈도우의 카운트를 가중치를 적용하여 계산
        double weightedPreviousCount = previousCounter.get() * (1 - elapsedRatio);
        long totalRequestCount = (long) (currentCounter.get() + weightedPreviousCount);

        if (totalRequestCount < maxRequestsPerWindow) {
            request.run();
            currentCounter.incrementAndGet();
        }

        // 오래된 윈도우 제거 (현재 윈도우와 이전 윈도우만 유지)
        windowCounterMap.entrySet().removeIf(entry -> entry.getKey() < previousWindowKey);
    }
}
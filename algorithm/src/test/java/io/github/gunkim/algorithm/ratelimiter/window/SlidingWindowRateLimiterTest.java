package io.github.gunkim.algorithm.ratelimiter.window;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("SlidingWindowRateLimiter는")
class SlidingWindowRateLimiterTest {
    private static final int WINDOW_SIZE = 1_000;
    private static final int REQUESTS_LIMIT = 5;

    @Test
    void window_size_이내_요청은_처리된다() {
        SlidingWindowRateLimiter rateLimiter = createRateLimiter();
        executeAndVerifyRequest(rateLimiter, 1);
    }

    @Test
    void 제한_초과_요청은_처리되지_않는다() throws InterruptedException {
        SlidingWindowRateLimiter rateLimiter = createRateLimiter();
        var counter = new AtomicInteger(0);
        var countDownLatch = new CountDownLatch(REQUESTS_LIMIT);

        executeBulkRequests(rateLimiter, counter, countDownLatch, REQUESTS_LIMIT + 3);

        countDownLatch.await(1, TimeUnit.SECONDS);
        assertThat(counter.get()).isEqualTo(REQUESTS_LIMIT);
    }

    @Test
    void 슬라이딩_윈도우_동작을_만족한다() {
        var currentTime = new AtomicLong(0);
        TimeProvider timeProvider = currentTime::get;
        var rateLimiter = new SlidingWindowRateLimiter(WINDOW_SIZE, REQUESTS_LIMIT, timeProvider);

        int totalRequests = sendAllWindowRequests(rateLimiter, currentTime);

        assertThat(totalRequests)
                .isGreaterThan(REQUESTS_LIMIT)
                .isLessThanOrEqualTo(REQUESTS_LIMIT * 2);
    }

    private int sendAllWindowRequests(SlidingWindowRateLimiter rateLimiter, AtomicLong currentTime) {
        int firstWindowRequests = sendRequests(rateLimiter, REQUESTS_LIMIT + 2, currentTime);
        assertThat(firstWindowRequests)
                .as("임계치에 대한 제한이 정상적으로 구현되었는지 확인해보세요.")
                .isEqualTo(REQUESTS_LIMIT);

        currentTime.addAndGet(WINDOW_SIZE / 2);
        int secondWindowRequests = sendRequests(rateLimiter, REQUESTS_LIMIT, currentTime);
        assertThat(secondWindowRequests).isZero();

        currentTime.addAndGet(WINDOW_SIZE / 2);
        int thirdWindowRequests = sendRequests(rateLimiter, REQUESTS_LIMIT, currentTime);
        assertThat(thirdWindowRequests).isEqualTo(1);

        return firstWindowRequests + secondWindowRequests + thirdWindowRequests;
    }

    private int sendRequests(SlidingWindowRateLimiter rateLimiter, int count, AtomicLong currentTime) {
        var successCount = new AtomicInteger();
        for (int i = 0; i < count; i++) {
            rateLimiter.handleRequest(successCount::getAndIncrement);
            currentTime.addAndGet(1); // 각 요청마다 1ms 증가
        }
        return successCount.get();
    }

    private void executeBulkRequests(SlidingWindowRateLimiter rateLimiter, AtomicInteger counter, CountDownLatch latch, int count) {
        for (int i = 0; i < count; i++) {
            rateLimiter.handleRequest(() -> {
                counter.incrementAndGet();
                latch.countDown();
            });
        }
    }

    private SlidingWindowRateLimiter createRateLimiter() {
        var currentTime = new AtomicLong(0);
        return new SlidingWindowRateLimiter(WINDOW_SIZE, REQUESTS_LIMIT, currentTime::get);
    }

    private void executeAndVerifyRequest(SlidingWindowRateLimiter rateLimiter, int times) {
        Runnable runnable = mock(Runnable.class);
        rateLimiter.handleRequest(runnable);
        verify(runnable, times(times)).run();
    }
}
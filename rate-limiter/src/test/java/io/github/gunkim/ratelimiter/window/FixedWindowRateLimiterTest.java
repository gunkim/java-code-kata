package io.github.gunkim.ratelimiter.window;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@DisplayName("FixedWindowRateLimiter는")
class FixedWindowRateLimiterTest {
    private static final int WINDOW_SIZE = 10_000;
    private static final int REQUESTS_LIMIT = 2;

    @Test
    void window_size_이내_요청은_처리된다() {
        Runnable runnable = mock(Runnable.class);
        FixedWindowRateLimiter rateLimiter = createRateLimiter(10);
        rateLimiter.handleRequest(runnable);
        verify(runnable, times(1)).run();
    }

    @Test
    void window_size가_2이고_요청이_3회라면_2회만_처리된다() throws InterruptedException {
        int requestCount = 3;
        AtomicInteger counter = new AtomicInteger(0);
        CountDownLatch countDownLatch = new CountDownLatch(REQUESTS_LIMIT);
        Runnable request = createRunnable(counter, countDownLatch);

        FixedWindowRateLimiter rateLimiter = createRateLimiter(REQUESTS_LIMIT);
        sendRequests(rateLimiter, request, requestCount);

        countDownLatch.await(1, TimeUnit.SECONDS);
        assertThat(counter.get()).isEqualTo(REQUESTS_LIMIT);
    }

    private FixedWindowRateLimiter createRateLimiter(int limit) {
        return new FixedWindowRateLimiter(limit, WINDOW_SIZE);
    }

    private Runnable createRunnable(AtomicInteger counter, CountDownLatch countDownLatch) {
        return () -> {
            counter.incrementAndGet();
            countDownLatch.countDown();
        };
    }

    private void sendRequests(FixedWindowRateLimiter rateLimiter, Runnable request, int requestCount) {
        for (int i = 0; i < requestCount; i++) {
            rateLimiter.handleRequest(request);
        }
    }
}
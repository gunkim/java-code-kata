package io.github.gunkim.algorithm.ratelimiter.bucket;

import io.github.gunkim.algorithm.ratelimiter.bucket.TokenRateLimiterRateLimiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@DisplayName("TokenBucket은")
class TokenRateLimiterRateLimiterTest {
    private Runnable request;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(Runnable.class);
    }

    @Test
    void 토큰이_없는_경우_요청이_무시된다() {
        try (var tokenBucket = new TokenRateLimiterRateLimiter(0, 20_000)) {
            tokenBucket.handleRequest(request);
            Mockito.verify(request, never()).run();
        }
    }

    @Test
    void 토큰이_최대치일_때_모든_요청이_처리된다() {
        try (var tokenBucket = new TokenRateLimiterRateLimiter(10, 20_000)) {
            for (int i = 0; i < 10; i++) {
                tokenBucket.handleRequest(request);
            }
            Mockito.verify(request, times(10)).run();
        }
    }

    @Test
    void 토큰이_모두_사용된_후_나머지_요청이_무시된다() {
        try (var tokenBucket = new TokenRateLimiterRateLimiter(3, 20_000)) {
            for (int i = 0; i < 6; i++) {
                tokenBucket.handleRequest(request);
            }
            Mockito.verify(request, times(3)).run();
        }
    }

    @Test
    void 다중_스레드_환경에서_정상_작동한다() {
        try (var tokenBucket = new TokenRateLimiterRateLimiter(100, 5_000)) {
            ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
            for (int i = 0; i < 150; i++) {
                taskExecutor.execute(() -> tokenBucket.handleRequest(request));
            }
            taskExecutor.shutdown();
            taskExecutor.awaitTermination(1_000, TimeUnit.MILLISECONDS);
            Mockito.verify(request, times(100)).run();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
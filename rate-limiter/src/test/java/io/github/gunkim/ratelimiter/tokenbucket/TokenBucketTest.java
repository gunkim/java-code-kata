package io.github.gunkim.ratelimiter.tokenbucket;

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
class TokenBucketTest {
    private Runnable request;

    @BeforeEach
    void setUp() {
        request = Mockito.mock(Runnable.class);
    }

    @Test
    void 토큰이_없는_경우_요청이_무시된다() {
        var tokenBucket = new TokenBucket(0, 20_000);
        tokenBucket.request(request);
        Mockito.verify(request, never()).run();
    }

    @Test
    void 토큰이_최대치일_때_모든_요청이_처리된다() {
        var tokenBucket = new TokenBucket(10, 20_000);
        for (int i = 0; i < 10; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(10)).run();
    }

    @Test
    void 토큰이_모두_사용된_후_나머지_요청이_무시된다() {
        var tokenBucket = new TokenBucket(3, 20_000);
        for (int i = 0; i < 6; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(3)).run();
    }

    @Test
    void 단일_스레드_환경에서_정상_작동한다() {
        var tokenBucket = new TokenBucket(5, 1000);
        for (int i = 0; i < 7; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(5)).run();
    }

    @Test
    void 다중_스레드_환경에서_정상_작동한다() throws InterruptedException {
        var tokenBucket = new TokenBucket(100, 5_000);
        ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 150; i++) {
            taskExecutor.execute(() -> tokenBucket.request(request));
        }
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        Mockito.verify(request, times(100)).run();
    }
}
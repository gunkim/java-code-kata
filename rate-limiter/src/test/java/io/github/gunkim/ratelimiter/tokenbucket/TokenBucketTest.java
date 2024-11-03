package io.github.gunkim.ratelimiter.tokenbucket;

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
    @Test
    void 토큰_수가_0이면_요청_메서드가_요청을_삭제한다() {
        var tokenBucket = new TokenBucket(0, 20_000);
        Runnable request = Mockito.mock(Runnable.class);
        tokenBucket.request(request);
        Mockito.verify(request, never()).run();
    }

    @Test
    void 토큰_수가_최대치일_때_요청_메소드는_요청을_처리한다() {
        var tokenBucket = new TokenBucket(10, 20_000);
        Runnable request = Mockito.mock(Runnable.class);
        for (int i = 0; i < 10; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(10)).run();
    }

    @Test
    void 토큰_수가_3이고_요청이_6개_들어오면_3개만_처리한다() {
        var tokenBucket = new TokenBucket(3, 20_000);
        Runnable request = Mockito.mock(Runnable.class);
        for (int i = 0; i < 6; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(3)).run();
    }

    @Test
    void 단일_스레드_환경에서_토큰_버킷이_올바르게_작동한다() {
        var tokenBucket = new TokenBucket(5, 1000);
        Runnable request = Mockito.mock(Runnable.class);
        for (int i = 0; i < 7; i++) {
            tokenBucket.request(request);
        }
        Mockito.verify(request, times(5)).run();
    }

    @Test
    void 다중_스레드_환경에서_토큰_버킷이_올바르게_작동한다() throws InterruptedException {
        var tokenBucket = new TokenBucket(100, 5_000);
        Runnable request = Mockito.mock(Runnable.class);
        ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 150; i++) {
            taskExecutor.execute(() -> tokenBucket.request(request));
        }
        taskExecutor.shutdown();
        taskExecutor.awaitTermination(1000, TimeUnit.MILLISECONDS);
        Mockito.verify(request, times(100)).run();
    }
}

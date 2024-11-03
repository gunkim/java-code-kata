package io.github.gunkim.ratelimiter;


import io.github.gunkim.ratelimiter.bucket.TokenRateLimiterRateLimiter;

public class Application {
    public static void main(String[] args) {
        //4초에 2개 요청에 대한 처리율 제한 설정
        final int bucketSize = 20;
        final long refillRate = 60_000L;
        var bucket = new TokenRateLimiterRateLimiter(bucketSize, refillRate);

        while (true) {
            processRequest(bucket);
            sleep(1_000);
        }
    }

    private static void processRequest(TokenRateLimiterRateLimiter bucket) {
        bucket.handleRequest(() -> System.out.println("Request"));
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

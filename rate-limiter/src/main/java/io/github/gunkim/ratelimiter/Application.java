package io.github.gunkim.ratelimiter;


import io.github.gunkim.ratelimiter.bucket.TokenBucket;

public class Application {
    public static void main(String[] args) {
        //4초에 2개 요청에 대한 처리율 제한 설정
        final int bucketSize = 20;
        final long refillRate = 60_000L;
        var bucket = new TokenBucket(bucketSize, refillRate);

        while (true) {
            processRequest(bucket);
            sleep(1_000);
        }
    }

    private static void processRequest(TokenBucket bucket) {
        bucket.request(() -> System.out.println("Request"));
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
}

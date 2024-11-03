package io.github.gunkim.ratelimiter.bucket;

public interface Bucket {
    void request(Runnable request);
}

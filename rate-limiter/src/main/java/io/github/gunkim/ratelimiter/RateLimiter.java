package io.github.gunkim.ratelimiter;

public interface RateLimiter {
    void request(Runnable request);
}

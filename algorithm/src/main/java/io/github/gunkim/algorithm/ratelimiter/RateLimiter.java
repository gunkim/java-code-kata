package io.github.gunkim.algorithm.ratelimiter;

public interface RateLimiter {
    void handleRequest(Runnable request);
}

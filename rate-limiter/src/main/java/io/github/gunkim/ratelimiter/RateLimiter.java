package io.github.gunkim.ratelimiter;

public interface RateLimiter {
    void handleRequest(Runnable request);
}

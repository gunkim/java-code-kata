package io.github.gunkim.algorithm.ratelimiter.window;

@FunctionalInterface
public interface TimeProvider {
    long getCurrentTimeMillis();
}

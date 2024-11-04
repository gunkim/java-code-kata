package io.github.gunkim.ratelimiter.window;

@FunctionalInterface
public interface TimeProvider {
    long getCurrentTimeMillis();
}

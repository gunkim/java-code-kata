package io.github.gunkim.datasystems.engine.storage;

import java.util.Optional;

public interface Storage<T> {
    void save(String key, T value);

    Optional<T> find(String key);
}

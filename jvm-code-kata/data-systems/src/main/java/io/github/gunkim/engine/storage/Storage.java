package io.github.gunkim.engine.storage;

import java.util.Optional;

public interface Storage<T> {
    void save(String key, T value);

    Optional<T> find(String key);
}

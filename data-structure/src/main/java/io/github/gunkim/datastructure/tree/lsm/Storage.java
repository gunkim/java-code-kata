package io.github.gunkim.datastructure.tree.lsm;

import java.util.Optional;

public interface Storage<T> {
    void save(String key, T value);

    Optional<T> find(String key);
}

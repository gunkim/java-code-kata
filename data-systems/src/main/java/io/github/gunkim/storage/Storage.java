package io.github.gunkim.storage;

import java.util.Optional;

public abstract class Storage<T> {
    private final String storagePath;

    Storage(String path) {
        this.storagePath = path;
    }

    public abstract void save(String key, T value);

    public abstract Optional<T> find(String key);

    protected String storagePath() {
        return storagePath;
    }
}

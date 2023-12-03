package io.github.gunkim.storage;

import java.util.Map;
import java.util.Optional;

public abstract class Storage {
    private final String storagePath;

    Storage(String path) {
        this.storagePath = path;
    }

    public abstract void save(String key, Map<String, Object> value);

    public abstract Optional<Map<String, Object>> find(String key);

    protected String storagePath() {
        return storagePath;
    }
}

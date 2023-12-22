package io.github.gunkim.replication.server;

import java.util.HashMap;
import java.util.Map;

public class DataStore {
    private final Map<String, String> data = new HashMap<>();
    private final boolean writable;

    public DataStore(boolean writable) {
        this.writable = writable;
    }

    public void put(String key, String value) {
        if (!writable) {
            throw new IllegalStateException("This data store is not writable.");
        }
        data.put(key, value);
    }

    public String get(String key) {
        return data.get(key);
    }

    public Map<String, String> export() {
        return new HashMap<>(data);
    }
}

package io.github.gunkim.engine.storage.lsm;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MemTable<T> {
    private final SortedMap<String, T> content = new TreeMap<>();
    private final int threshold;

    public MemTable(int threshold) {
        this.threshold = threshold;
    }

    public boolean isFull() {
        return content.size() >= threshold;
    }

    public void add(String key, T value) {
        content.put(key, value);
    }

    public T get(String key) {
        return content.get(key);
    }

    public void clear() {
        content.clear();
    }

    public Set<Map.Entry<String, T>> entrySet() {
        return content.entrySet();
    }
}

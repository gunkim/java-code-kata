package io.github.gunkim.engine.storage;

public interface StorageFactory<T> {
    Storage<T> createSimpleStorage(String path);
}

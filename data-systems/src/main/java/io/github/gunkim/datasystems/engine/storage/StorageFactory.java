package io.github.gunkim.datasystems.engine.storage;

public interface StorageFactory<T> {
    Storage<T> createSimpleStorage(String path);
}

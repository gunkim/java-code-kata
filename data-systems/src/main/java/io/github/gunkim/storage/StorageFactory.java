package io.github.gunkim.storage;

public interface StorageFactory<T> {
    Storage<T> createSimpleStorage(String path);
}

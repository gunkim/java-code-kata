package io.github.gunkim.engine.storage.simple;

import io.github.gunkim.engine.storage.Storage;
import io.github.gunkim.engine.storage.StorageFactory;

public class SimpleStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new SimpleStorage<>(path);
    }
}

package io.github.gunkim.engine.storage.hash;

import io.github.gunkim.engine.storage.Storage;
import io.github.gunkim.engine.storage.StorageFactory;

public class HashIndexStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new HashIndexStorage<>(path);
    }
}

package io.github.gunkim.datasystems.engine.storage.hash;

import io.github.gunkim.datasystems.engine.storage.Storage;
import io.github.gunkim.datasystems.engine.storage.StorageFactory;

public class HashIndexStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new HashIndexStorage<>(path);
    }
}

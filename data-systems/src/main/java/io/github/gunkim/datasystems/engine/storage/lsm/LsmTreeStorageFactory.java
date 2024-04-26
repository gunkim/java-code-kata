package io.github.gunkim.datasystems.engine.storage.lsm;

import io.github.gunkim.datasystems.engine.storage.Storage;
import io.github.gunkim.datasystems.engine.storage.StorageFactory;

public class LsmTreeStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new LsmTreeStorage<>(path);
    }
}

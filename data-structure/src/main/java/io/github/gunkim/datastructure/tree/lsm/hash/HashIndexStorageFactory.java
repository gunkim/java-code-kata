package io.github.gunkim.datastructure.tree.lsm.hash;

import io.github.gunkim.datastructure.tree.lsm.Storage;
import io.github.gunkim.datastructure.tree.lsm.StorageFactory;

public class HashIndexStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new HashIndexStorage<>(path);
    }
}

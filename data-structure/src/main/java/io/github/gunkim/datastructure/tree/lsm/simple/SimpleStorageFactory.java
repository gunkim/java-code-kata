package io.github.gunkim.datastructure.tree.lsm.simple;

import io.github.gunkim.datastructure.tree.lsm.Storage;
import io.github.gunkim.datastructure.tree.lsm.StorageFactory;

public class SimpleStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new SimpleStorage<>(path);
    }
}

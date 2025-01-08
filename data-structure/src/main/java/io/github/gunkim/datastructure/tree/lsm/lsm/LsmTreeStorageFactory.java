package io.github.gunkim.datastructure.tree.lsm.lsm;

import io.github.gunkim.datastructure.tree.lsm.Storage;
import io.github.gunkim.datastructure.tree.lsm.StorageFactory;

public class LsmTreeStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new LsmTreeStorage<>(path);
    }
}

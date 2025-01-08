package io.github.gunkim.datastructure.tree.lsm;

public interface StorageFactory<T> {
    Storage<T> createSimpleStorage(String path);
}

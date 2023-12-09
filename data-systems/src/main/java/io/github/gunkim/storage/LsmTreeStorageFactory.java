package io.github.gunkim.storage;

public class LsmTreeStorageFactory<T> implements StorageFactory<T> {
    @Override
    public Storage<T> createSimpleStorage(String path) {
        return new LsmTreeStorage<>(path);
    }
}

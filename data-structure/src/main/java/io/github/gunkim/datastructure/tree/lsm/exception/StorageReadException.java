package io.github.gunkim.datastructure.tree.lsm.exception;

public class StorageReadException extends RuntimeException {
    public StorageReadException(String message) {
        super(message);
    }
}

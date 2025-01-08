package io.github.gunkim.datastructure.tree.lsm.exception;

public class StorageWriteException extends RuntimeException {
    public StorageWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

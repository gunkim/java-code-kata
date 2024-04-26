package io.github.gunkim.datasystems.engine.storage.exception;

public class StorageWriteException extends RuntimeException {
    public StorageWriteException(String message, Throwable throwable) {
        super(message, throwable);
    }
}

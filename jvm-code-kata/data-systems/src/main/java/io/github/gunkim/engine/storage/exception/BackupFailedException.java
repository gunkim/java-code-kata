package io.github.gunkim.engine.storage.exception;

public class BackupFailedException extends RuntimeException {
    public BackupFailedException(String message) {
        super(message);
    }
}

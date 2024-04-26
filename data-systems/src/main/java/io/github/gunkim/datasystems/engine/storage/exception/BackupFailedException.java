package io.github.gunkim.datasystems.engine.storage.exception;

public class BackupFailedException extends RuntimeException {
    public BackupFailedException(String message) {
        super(message);
    }
}

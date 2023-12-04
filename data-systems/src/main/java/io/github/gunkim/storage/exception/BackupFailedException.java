package io.github.gunkim.storage.exception;

public class BackupFailedException extends RuntimeException {
    public BackupFailedException(String message) {
        super(message);
    }
}

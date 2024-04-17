package io.github.gunkim.engine.storage.exception;

public class CompactionFailedException extends RuntimeException {
    public CompactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

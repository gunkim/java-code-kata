package io.github.gunkim.datastructure.tree.lsm.exception;

public class CompactionFailedException extends RuntimeException {
    public CompactionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

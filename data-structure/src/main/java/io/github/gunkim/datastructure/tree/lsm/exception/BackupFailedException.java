package io.github.gunkim.datastructure.tree.lsm.exception;

public class BackupFailedException extends RuntimeException {
    public BackupFailedException(String message) {
        super(message);
    }
}

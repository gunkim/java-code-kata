package io.github.gunkim.replication.server.exception;

public class ConnectionRetryFailedException extends IllegalStateException {
    public ConnectionRetryFailedException(String s) {
        super(s);
    }
}

package io.github.gunkim.datasystems.replication.server.exception;

public class ConnectionRetryFailedException extends IllegalStateException {
    public ConnectionRetryFailedException(String s) {
        super(s);
    }
}

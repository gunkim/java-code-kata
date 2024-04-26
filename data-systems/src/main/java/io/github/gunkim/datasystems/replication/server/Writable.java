package io.github.gunkim.datasystems.replication.server;

public interface Writable {
    void write(String key, String value);
}

package io.github.gunkim.replication.server;

public interface Writable {
    void write(String key, String value);
}

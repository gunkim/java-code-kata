package io.github.gunkim.replication.server;

import java.util.logging.Logger;

public class MasterServer {
    private static final Logger LOGGER = Logger.getLogger(MasterServer.class.getName());
    private static final int DEFAULT_PORT = 8080;

    private final DataStore dataStore = new DataStore(true);
    private final int port;

    public MasterServer(int port) {
        this.port = port;
    }

    public MasterServer() {
        this(DEFAULT_PORT);
    }

    public void put(String key, String value) {
        dataStore.put(key, value);
    }

    public String get(String key) {
        return dataStore.get(key);
    }

    public int port() {
        return port;
    }

    public Metadata metadata() {
        return new Metadata("localhost", port);
    }

    public void start() {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    public record Metadata(String host, int port) {
    }
}

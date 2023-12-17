package io.github.gunkim.replication.server;

import java.util.logging.Logger;

public class SlaveServer {
    private static final Logger LOGGER = Logger.getLogger(SlaveServer.class.getName());
    private static final int DEFAULT_PORT = 8080;

    private final DataStore data = new DataStore(false);
    //마스터 노드가 교체되지 않음을 가정
    private final MasterServer.Metadata masterMetadata;
    private final int port;

    public SlaveServer(MasterServer.Metadata masterMetadata, int port) {
        this.masterMetadata = masterMetadata;
        this.port = port;
    }

    public SlaveServer(MasterServer.Metadata masterMetadata) {
        this(masterMetadata, DEFAULT_PORT);
    }

    public String get(String key) {
        return data.get(key);
    }

    public void start() {
        throw new UnsupportedOperationException("Not implemented yet");
    }
}

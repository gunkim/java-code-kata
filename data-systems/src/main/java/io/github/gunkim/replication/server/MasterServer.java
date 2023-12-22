package io.github.gunkim.replication.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
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
        backgroundRun(() -> {
            LOGGER.info("Master server started on port %d".formatted(port));

            while (true) {
                LOGGER.info("Waiting for replication request...");
                try (var serverSocket = new ServerSocket(port);
                     var socket = serverSocket.accept();
                     var dataInputStream = new DataInputStream(socket.getInputStream())) {
                    var request = dataInputStream.readUTF();
                    if (request.startsWith("REPLICATION_REQUEST")) {
                        var data = dataStore.export();
                        var response = "REPLICATION_RESPONSE:%s".formatted(data);
                        try (var dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
                            dataOutputStream.writeUTF(response);
                        }
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public record Metadata(String host, int port) {
    }

    private void backgroundRun(Runnable runnable) {
        new Thread(runnable).start();
    }
}

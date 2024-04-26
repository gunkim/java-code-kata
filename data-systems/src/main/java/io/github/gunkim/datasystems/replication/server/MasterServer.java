package io.github.gunkim.datasystems.replication.server;

import io.github.gunkim.datasystems.engine.storage.serializer.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer implements Readable, Writable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServer.class);
    private static final int DEFAULT_PORT = 8080;

    private final DataStore dataStore = new DataStore(true);
    private final int port;

    public MasterServer(int port) {
        this.port = port;
    }

    public MasterServer() {
        this(DEFAULT_PORT);
    }

    public int port() {
        return port;
    }

    public void start() {
        backgroundRun(this::acceptConnections);
    }

    @Override
    public String read(String key) {
        return dataStore.get(key);
    }

    @Override
    public void write(String key, String value) {
        dataStore.put(key, value);
    }

    /**
     * 단일 요청에 대한 처리만 고려되었음.
     * TODO: 다중 요청 처리를 위한 스레드 풀 사용이 고려되어야 할 듯 함.
     */
    private void acceptConnections() {
        LOGGER.info("Master server started on port {}", port);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                acceptClient(serverSocket);
            }
        } catch (IOException e) {
            LOGGER.error("Error starting server on port {}: {}", port, e.getMessage());
        }
    }

    private void acceptClient(ServerSocket serverSocket) {
        try (var socket = serverSocket.accept()) {
            processClientRequest(socket);
        } catch (IOException e) {
            LOGGER.error("Error accepting client connection: {}", e.getMessage());
        }
    }

    private void processClientRequest(Socket socket) {
        try (var dataOutputStream = new DataOutputStream(socket.getOutputStream())) {
            sendReplicationData(dataOutputStream);
        } catch (IOException e) {
            LOGGER.error("Error processing client request: {}", e.getMessage());
        }
    }

    private void sendReplicationData(DataOutputStream dataOutputStream) throws IOException {
        var data = dataStore.export();
        var json = new JsonSerializer().serialize(data);

        dataOutputStream.writeUTF(json);
    }

    private void backgroundRun(Runnable runnable) {
        new Thread(runnable).start();
    }
}

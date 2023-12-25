package io.github.gunkim.replication.server;

import io.github.gunkim.engine.storage.serializer.JsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MasterServer implements Readable, Writable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MasterServer.class);
    private static final int DEFAULT_PORT = 8080;
    private static final String REPLICATION_REQUEST_PREFIX = "REPLICATION_REQUEST";

    private final DataStore dataStore = new DataStore(true);
    private final int port;
    private volatile boolean running = true;

    public MasterServer(int port) {
        this.port = port;
    }

    public MasterServer() {
        this(DEFAULT_PORT);
    }

    public int port() {
        return port;
    }

    public Metadata metadata() {
        return new Metadata("localhost", port);
    }

    public void start() {
        backgroundRun(this::acceptConnections);
    }

    public void stop() {
        running = false;
    }

    @Override
    public String read(String key) {
        return dataStore.get(key);
    }

    @Override
    public void write(String key, String value) {
        dataStore.put(key, value);
    }

    private void acceptConnections() {
        LOGGER.info("Master server started on port {}", port);
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (running) {
                acceptClient(serverSocket);
            }
        } catch (IOException e) {
            LOGGER.error("Error starting server on port {}: {}", port, e.getMessage());
        }
        LOGGER.info("Master server stopped.");
    }

    private void acceptClient(ServerSocket serverSocket) {
        try (Socket socket = serverSocket.accept()) {
            processClientRequest(socket);
        } catch (IOException e) {
            LOGGER.error("Error accepting client connection: {}", e.getMessage());
        }
    }

    /**
     * TODO: DataOutputStream or DataInputStream을 Request, Response 객체로 각각 캡슐화하는게 더 좋을 것 같음.
     * TODO: 그리고 요청 유형에 따른 핸들러가 있어야 책임 분리가 확실히 가능할 듯?
     *
     * @return
     */
    private void processClientRequest(Socket socket) {
        try (DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream())) {

            handleClientData(dataInputStream, dataOutputStream);
        } catch (IOException e) {
            LOGGER.error("Error processing client request: {}", e.getMessage());
        }
    }

    private void handleClientData(DataInputStream dataInputStream, DataOutputStream dataOutputStream) throws IOException {
        String request = dataInputStream.readUTF();
        if (request.startsWith(REPLICATION_REQUEST_PREFIX)) {
            sendReplicationData(dataOutputStream);
        }
    }

    private void sendReplicationData(DataOutputStream dataOutputStream) throws IOException {
        var data = dataStore.export();
        var json = new JsonSerializer().serialize(data);
        var response = "REPLICATION_RESPONSE:%s".formatted(json);
        dataOutputStream.writeUTF(response);
    }

    private void backgroundRun(Runnable runnable) {
        new Thread(runnable).start();
    }

    public record Metadata(String host, int port) {
    }
}

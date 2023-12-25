package io.github.gunkim.replication.server;

import io.github.gunkim.engine.storage.serializer.JsonSerializer;
import io.github.gunkim.replication.server.exception.ConnectionRetryFailedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Map;

public class SlaveServer implements Readable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SlaveServer.class);
    private static final int MAX_RETRY_COUNT = 5;
    private static final int RETRY_DELAY_MS = 1_000;
    private static final int REPLICATION_DELAY_MS = 5_000;

    private final DataStore data = new DataStore(false);
    private final MasterMetadata masterMetadata;

    public SlaveServer(MasterMetadata masterMetadata) {
        this.masterMetadata = masterMetadata;
    }

    public String get(String key) {
        return data.get(key);
    }

    public void start() {
        runInBackground(this::attemptConnection);
    }

    @Override
    public String read(String key) {
        return data.get(key);
    }

    private void attemptConnection() {
        int retryCount = 0;

        while (true) {
            handleMaxRetryReached(retryCount);

            if (connectAndSync()) {
                retryCount = 0;
            } else {
                retryCount++;
            }

            sleep(RETRY_DELAY_MS);
        }
    }

    private void handleMaxRetryReached(int retryCount) {
        if (retryCount >= MAX_RETRY_COUNT) {
            LOGGER.error("Failed to connect to Master server at {}:{} after {} attempts",
                    masterMetadata.host(), masterMetadata.port(), MAX_RETRY_COUNT);
            throw new ConnectionRetryFailedException("Max retry count reached for master server connection.");
        }
    }

    private boolean connectAndSync() {
        try (Socket client = new Socket(masterMetadata.host(), masterMetadata.port());
             DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
             DataInputStream dataInputStream = new DataInputStream(client.getInputStream())) {
            syncReplication(dataOutputStream, dataInputStream);
            sleep(REPLICATION_DELAY_MS);
            return true;
        } catch (IOException e) {
            LOGGER.error("Error during connection or synchronization with master server: {}", e.getMessage());
            return false;
        }
    }

    /**
     * TODO: 주기적인 마스터와의 싱크보다는 마스터의 변경이 생겼을 때 실시간으로 싱크하는게 더 효율적일 것 같음.
     * TODO: mysql에서는 binlog를 사용하여 변경된 데이터를 실시간으로 싱크하는 듯?
     *
     * @param dataOutputStream
     * @param dataInputStream
     * @throws IOException
     */
    private void syncReplication(DataOutputStream dataOutputStream, DataInputStream dataInputStream) throws IOException {
        dataOutputStream.writeUTF("");

        String response = dataInputStream.readUTF();
        Map<String, String> deserializedData = new JsonSerializer().deserialize(response);
        data.replace(deserializedData);

        LOGGER.info("Replication completed successfully.");
    }

    private void sleep(int durationMs) {
        try {
            Thread.sleep(durationMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.error("Thread interrupted during sleep: {}", e.getMessage());
        }
    }

    private void runInBackground(Runnable task) {
        new Thread(task).start();
    }

    public record MasterMetadata(String host, int port) {
        public MasterMetadata {
            if (host == null || host.isBlank()) {
                throw new IllegalArgumentException("Host cannot be null or blank.");
            }
            if (port < 0 || port > 65535) {
                throw new IllegalArgumentException("Port must be between 0 and 65535.");
            }
        }
    }
}

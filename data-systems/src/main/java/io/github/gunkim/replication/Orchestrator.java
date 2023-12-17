package io.github.gunkim.replication;

import io.github.gunkim.replication.server.MasterServer;
import io.github.gunkim.replication.server.SlaveServer;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Orchestrator {
    private static final int MAX_RETRIES = 10;

    public void run(MasterServer masterServer, List<Integer> slaveServerPorts) {
        masterServer.start();

        waitForMasterServerReady(masterServer.metadata());

        slaveServerPorts.parallelStream()
                .forEach(port -> new SlaveServer(masterServer.metadata(), port).start());
    }

    private void waitForMasterServerReady(MasterServer.Metadata masterServerMetadata) {
        int retries = 0;
        while (true) {
            if (retries++ > MAX_RETRIES) {
                throw new RuntimeException("Server failed to start");
            }
            if (healthCheck(masterServerMetadata)) {
                return;
            }
            delay1Second();
        }
    }

    private void delay1Second() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting for the server to start", ie);
        }
    }

    private boolean healthCheck(MasterServer.Metadata metadata) {
        try (var socket = new Socket(metadata.host(), metadata.port())) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

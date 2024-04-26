package io.github.gunkim.datasystems.replication;

import io.github.gunkim.datasystems.replication.server.SlaveServer;
import io.github.gunkim.datasystems.replication.server.Writable;
import io.github.gunkim.datasystems.replication.server.MasterServer;

public class Main {
    public static void main(String[] args) {
        var masterServer = new MasterServer(8080);
        masterServer.start();

        var slaveServer = new SlaveServer(new SlaveServer.MasterMetadata("localhost", 8080));
        slaveServer.start();

        write(masterServer);
    }

    private static void write(Writable writable) {
        int i = 0;
        while (true) {
            sleep1seconds();
            writable.write("key" + i, "value" + i);
            i++;
        }
    }

    private static void sleep1seconds() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

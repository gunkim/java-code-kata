package io.github.gunkim.replication;

import io.github.gunkim.replication.server.MasterServer;
import io.github.gunkim.replication.server.SlaveServer;
import io.github.gunkim.replication.server.Writable;

public class Main {
    public static void main(String[] args) {
        var masterServer = new MasterServer(8080);
        masterServer.start();

        var slaveServer = new SlaveServer(masterServer.metadata());
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

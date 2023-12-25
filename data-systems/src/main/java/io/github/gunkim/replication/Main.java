package io.github.gunkim.replication;

import io.github.gunkim.replication.server.MasterServer;
import io.github.gunkim.replication.server.SlaveServer;

public class Main {
    public static void main(String[] args) {
        var masterServer = new MasterServer(8080);
        masterServer.start();

        var slaveServer = new SlaveServer(masterServer.metadata());
        slaveServer.start();

        masterServer.put("key1", "value1");
    }
}

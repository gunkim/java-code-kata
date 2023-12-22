package io.github.gunkim.replication;

import io.github.gunkim.replication.server.MasterServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        var masterServer = new MasterServer(8080);
        masterServer.start();

        masterServer.put("key1", "value1");

        while (true) {
            try (var client = new Socket("localhost", 8080);
                 var dataOutputStream = new DataOutputStream(client.getOutputStream())) {
                dataOutputStream.writeUTF("REPLICATION_REQUEST");

                try (var dataInputStream = new DataInputStream(client.getInputStream())) {
                    var response = dataInputStream.readUTF();
                    System.out.println(response);
                    return;
                }
            } catch (IOException e) {
                try {
                    Thread.sleep(1_000);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
    }
}

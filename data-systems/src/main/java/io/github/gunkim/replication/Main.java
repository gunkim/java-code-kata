package io.github.gunkim.replication;

import io.github.gunkim.replication.server.MasterServer;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        new Orchestrator().run(new MasterServer(8080),
                // 5개의 슬레이브 서버를 생성
                List.of(8081, 8082, 8083, 8084, 8085)
        );
    }
}
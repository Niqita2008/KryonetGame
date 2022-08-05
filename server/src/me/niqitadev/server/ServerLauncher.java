package me.niqitadev.server;

import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class ServerLauncher {
    public static void main(String[] args) throws IOException{
        Server server = new Server();
        server.start();
        server.bind(54555, 54777);
    }
}
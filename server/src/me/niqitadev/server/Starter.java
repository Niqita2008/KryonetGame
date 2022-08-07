package me.niqitadev.server;

import java.io.IOException;

public class Starter {
    public static ServerLauncher serverLauncher;

    public static void main(String[] args) throws IOException {
        serverLauncher = new ServerLauncher();
    }
}

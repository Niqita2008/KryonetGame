package me.niqitadev.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.packets.JoinError;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.JoinResponse;

import java.io.IOException;

public class ServerLauncher {
    public final Server server;

    public ServerLauncher() throws IOException {
        server = new Server();
        server.start();
        server.bind(7392, 7392);
        Kryo kryo = server.getKryo();
        kryo.register(JoinRequest.class);
        kryo.register(JoinError.class);
        kryo.register(JoinResponse.class);
        server.addListener(new ServerListener());
    }
}
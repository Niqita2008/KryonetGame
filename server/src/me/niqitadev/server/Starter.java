package me.niqitadev.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.packets.*;
import me.niqitadev.server.handlers.ServerPlayerHandler;

import java.io.IOException;

public class Starter {
    public final Server server;

    private Starter() throws IOException {
        server = new Server();
        server.start();
        server.bind(7392, 7392);
        Kryo kryo = server.getKryo();
        kryo.register(JoinRequest.class);
        kryo.register(float[].class);
        kryo.register(PlayerUpdatePacket.class);
        kryo.register(MovePacket.class);
        kryo.register(JoinRequestEnum.class);
        kryo.register(OtherPlayerDisconnected.class);
        kryo.register(JoinResponse.class);
        final ServerPlayerHandler handler = new ServerPlayerHandler(server);
        handler.start();
        server.addListener(new ServerListener(handler));
    }

    public static void main(String[] args) throws IOException {
        new Starter();
    }
}

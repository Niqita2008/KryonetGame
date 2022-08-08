package me.niqitadev.server;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.packets.*;

import java.io.IOException;
import java.util.HashMap;

public class Starter {
    public static Starter instance;
    public final Server server;

    public Starter() throws IOException {
        server = new Server();
        server.start();
        server.bind(7392, 7392);
        Kryo kryo = server.getKryo();
        kryo.register(JoinRequest.class);
        kryo.register(PlayersUpdatePacket.class);
        kryo.register(MovePacket.class);
        kryo.register(JoinError.class);
        kryo.register(HashMap.class);
        kryo.register(JoinResponse.class);
        server.addListener(new ServerListener());
    }

    public static void main(String[] args) throws IOException {
        instance = new Starter();
    }
}

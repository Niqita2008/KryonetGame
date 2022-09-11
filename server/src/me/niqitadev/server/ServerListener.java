package me.niqitadev.server;


import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.MovePacket;
import me.niqitadev.server.handlers.ServerPlayerHandler;

public class ServerListener extends com.esotericsoftware.kryonet.Listener {
    private final ServerPlayerHandler handler;

    public ServerListener(ServerPlayerHandler handler) {
        this.handler = handler;
    }

    @Override
    public void disconnected(Connection connection) {
        handler.removePlayer(connection);
    }

    @Override
    public void received(final Connection connection, final Object object) {
        if (object instanceof JoinRequest req) handler.addPlayer(connection, req);
        else if (object instanceof MovePacket movePacket) {
            final OnlinePlayer player = handler.getPlayer(connection);
            if (player != null) player.apply(movePacket);
            else connection.close();
        }
    }
}

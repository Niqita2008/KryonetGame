package me.niqitadev.server;


import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.JoinRequestEnum;
import me.niqitadev.core.packets.JoinResponse;
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
        if (object instanceof JoinRequest request) {
            JoinResponse response = new JoinResponse();
            if (handler.getPlayer(request.username) != null) {
                response.errorMessage = JoinRequestEnum.USER_WITH_THIS_NICK_ALREADY_CONNECTED_TO_THE_SERVER.message;
                System.err.println(request.username + " tried to connect, but was kicked with error \"" + response.errorMessage + "\".");
                connection.sendTCP(response);
                return;
            }
            connection.sendTCP(response);
            System.out.println(request.username + " joined.");

            handler.onlinePlayers.add(new OnlinePlayer(request.username, connection));
            return;
        }
        if (object instanceof MovePacket movePacket) {
            OnlinePlayer player = handler.getPlayer(connection);
            if (player != null) player.apply(movePacket);
            else connection.close();
        }
    }
}

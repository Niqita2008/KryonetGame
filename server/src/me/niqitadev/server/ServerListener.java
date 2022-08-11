package me.niqitadev.server;


import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.handlers.CustomForEach;
import me.niqitadev.core.packets.JoinEnum;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.JoinResponse;
import me.niqitadev.core.packets.MovePacket;
import me.niqitadev.server.handlers.ServerPlayerHandler;

import java.util.concurrent.atomic.AtomicReference;

public class ServerListener extends com.esotericsoftware.kryonet.Listener {
    private final ServerPlayerHandler handler;

    public ServerListener(ServerPlayerHandler handler) {
        this.handler = handler;
    }

    @Override
    public void received(final Connection connection, final Object object) {
        if (object instanceof JoinRequest request) {
            JoinResponse response = new JoinResponse();
            AtomicReference<String> error = new AtomicReference<>();
            connection.sendTCP(response);
            boolean[] d = {false};
            CustomForEach.forEach(handler.onlinePlayers, (p, breaker) -> {
                if (!p.name.equals(request.username)) return;
                error.set(JoinEnum.USER_WITH_THIS_NICK_ALREADY_CONNECTED_TO_THE_SERVER.message);
                System.err.println(request.username + " tried to connect, but was kicked with \"" + error.get() + "\"");
                d[0] = true;
                breaker.stop();
            });
            response.errorMessage = error.get();
            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                connection.sendTCP(response);
            }).start();
            if (d[0]) return;
            System.out.println(request.username + " joined.");

            new Thread(() -> {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                handler.onlinePlayers.add(new OnlinePlayer(request.username, connection));
            }).start();

            return;
        }
        if (object instanceof MovePacket movePacket) {
            handler.getPlayer(connection).pos.add(movePacket.x, movePacket.y);
            System.out.println("moved");
        }
    }
}

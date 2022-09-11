package me.niqitadev.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.packets.*;
import me.niqitadev.server.OnlinePlayer;

import java.util.HashSet;

public class ServerPlayerHandler implements Runnable {

    public final HashSet<OnlinePlayer> onlinePlayers = new HashSet<>();
    private final Server server;
    private boolean running;
    private final float spawnX, spawnY, spawnZ;

    public ServerPlayerHandler(Server server) {
        this.server = server;
        spawnX = 0;
        spawnY = 5;
        spawnZ = 0;
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public void addPlayer(final Connection connection, final JoinRequest req) {
        JoinResponse resp = new JoinResponse();
        if (getPlayer(req.name) != null) {
            resp.errorMessage = JoinRequestEnum.USER_WITH_THIS_NICK_ALREADY_CONNECTED_TO_THE_SERVER.message;
            System.err.println(req.name + " tried to connect, but was kicked with error \"" + resp.errorMessage + "\".");
            connection.sendTCP(resp);
            return;
        }
        onlinePlayers.add(new OnlinePlayer(req.name, connection));
        connection.sendTCP(resp);

        final PlayerUpdatePacket packet = new PlayerUpdatePacket(req.name, spawnX, spawnY, spawnZ, 0), packet1 = new PlayerUpdatePacket();
        onlinePlayers.forEach(n -> {
            n.connection.sendTCP(packet);
            server.sendToAllExceptTCP(n.connection.getID(), packet1.set(n.name, n.x, n.y, n.z, n.dirX, n.dirY, n.dirZ));
        });
        System.out.println(req.name + " joined.");
    }

    public OnlinePlayer getPlayer(final String name) {
        return onlinePlayers.stream().filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }

    public OnlinePlayer getPlayer(final Connection connection) {
        return onlinePlayers.stream().filter(p -> p.connection == connection).findFirst().orElse(null);
    }

    public void removePlayer(Connection connection) {
        OnlinePlayer player = getPlayer(connection);
        if (player == null) return;
        OtherPlayerDisconnected disconnected = new OtherPlayerDisconnected();
        disconnected.name = player.name;
        server.sendToAllExceptUDP(connection.getID(), disconnected);
        onlinePlayers.remove(player);
    }

    @Override
    public synchronized void run() {

        final long optimalTime = 70000000; // ms / amount of ticks

        for (long now, updateTime, wait; running; ) {
            now = System.nanoTime();

            final PlayerUpdatePacket packet = new PlayerUpdatePacket();
            onlinePlayers.stream().filter(OnlinePlayer::isUpdated).forEach(p -> server.sendToAllUDP(
                    packet.set(p.name, p.x, p.y, p.z, p.dirX, p.dirY, p.dirZ)));

            updateTime = System.nanoTime() - now;
            wait = (optimalTime - updateTime) / 1000000;
            if (wait < 1) continue;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

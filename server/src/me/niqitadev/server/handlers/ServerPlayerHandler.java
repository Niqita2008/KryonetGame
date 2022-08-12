package me.niqitadev.server.handlers;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.packets.OtherPlayerDisconnected;
import me.niqitadev.core.packets.PlayerUpdatePacket;
import me.niqitadev.server.OnlinePlayer;

import java.util.HashSet;

public class ServerPlayerHandler implements Runnable {

    private boolean running;
    private final Server server;
    public final HashSet<OnlinePlayer> onlinePlayers = new HashSet<>();

    public ServerPlayerHandler(Server server) {
        this.server = server;
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public OnlinePlayer getPlayer(final String name) {
        return onlinePlayers.stream().filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }

    public OnlinePlayer getPlayer(final Connection connection) {
        return onlinePlayers.stream().filter(p -> p.connection == connection).findFirst().orElse(null);
    }

    public synchronized void tick() {
        final PlayerUpdatePacket playerUpdatePacket = new PlayerUpdatePacket();
        onlinePlayers.forEach(p -> {
            p.update();
            playerUpdatePacket.name = p.name;
            playerUpdatePacket.x = p.pos.x;
            playerUpdatePacket.y = p.pos.y;
            server.sendToAllUDP(playerUpdatePacket);
        });

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

        long now, updateTime, wait;

        final long optimalTime = 100000000; // ms / amount of ticks

        while (running) {
            now = System.nanoTime();

            tick();

            updateTime = System.nanoTime() - now;
            wait = (optimalTime - updateTime) / 1000000;

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

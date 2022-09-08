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

            final PlayerUpdatePacket playerUpdatePacket = new PlayerUpdatePacket();
            onlinePlayers.stream().filter(OnlinePlayer::isChanged).forEach(p -> server.sendToAllUDP(playerUpdatePacket.set(p.name, p.x, p.y)));

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

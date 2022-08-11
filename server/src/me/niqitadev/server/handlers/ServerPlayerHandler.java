package me.niqitadev.server.handlers;

import com.badlogic.gdx.utils.ObjectSet;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;
import me.niqitadev.core.handlers.CustomForEach;
import me.niqitadev.core.packets.MovePacket;
import me.niqitadev.core.packets.PlayerUpdatePacket;
import me.niqitadev.server.OnlinePlayer;

import java.util.function.Consumer;

public class ServerPlayerHandler implements Runnable {

    private boolean running;
    private final Server server;
    public final ObjectSet<OnlinePlayer> onlinePlayers = new ObjectSet<>();

    public ServerPlayerHandler(Server server) {
        this.server = server;
    }

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public OnlinePlayer getPlayer(final String name) {
        OnlinePlayer[] value = {null};
        CustomForEach.forEach(onlinePlayers, (e, breaker) -> {
            if (!e.name.equals(name)) return;
            value[0] = e;
            breaker.stop();
        });
        return value[0];
    }

    public OnlinePlayer getPlayer(final Connection connection) {
        OnlinePlayer[] value = {null};
        CustomForEach.forEach(onlinePlayers, (e, breaker) -> {
            if (e.connection != connection) return;
            value[0] = e;
            breaker.stop();
        });
        return value[0];
    }

    public void tick() {
        onlinePlayers.forEach(OnlinePlayer::update);
        final PlayerUpdatePacket playerUpdatePacket = new PlayerUpdatePacket();

        onlinePlayers.forEach(p -> {
            playerUpdatePacket.name = p.name;
            playerUpdatePacket.x = p.pos.x;
            playerUpdatePacket.y = p.pos.y;
            server.sendToAllUDP(playerUpdatePacket);
        });

    }

    @Override
    public void run() {
        long pastTime = System.nanoTime();
        double amountOfTicks = 20, ns = 1000000000 / amountOfTicks, delta = 0;

        while (running) {

            try {
                Thread.sleep((long) (60 / amountOfTicks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            long now = System.nanoTime();
            delta += (now - pastTime) / ns;
            pastTime = now;

            for (; delta > 0; delta--) tick();
        }
    }
}

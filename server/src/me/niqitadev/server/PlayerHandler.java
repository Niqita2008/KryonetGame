package me.niqitadev.server;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.OnlinePlayer;
import me.niqitadev.core.packets.PlayersUpdatePacket;

import java.util.Arrays;

public class PlayerHandler implements Runnable {

    private boolean running;

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public final Array<OnlinePlayer> onlinePlayers = new Array<>();

    public OnlinePlayer getPlayer(final String name) {
        return onlinePlayers.isEmpty() ? null : Arrays.stream(onlinePlayers.items)
                .filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }

    public OnlinePlayer getPlayer(final Connection connection) {
        return onlinePlayers.isEmpty() ? null : Arrays.stream(onlinePlayers.items)
                .filter(p -> p.connection == connection).findFirst().orElse(null);
    }

    public void tick() {
        onlinePlayers.forEach(OnlinePlayer::update);
        final PlayersUpdatePacket playersUpdatePacket = new PlayersUpdatePacket();

        onlinePlayers.forEach(p -> playersUpdatePacket.players.put(p.name, p.position));

        Starter.serverLauncher.server.sendToAllUDP(playersUpdatePacket);
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime(), now;
        double amountOfTicks = 60, ns = 1000000000 / amountOfTicks, delta = 0;

        while (running) {
            try {
                Thread.sleep((long) (60F / amountOfTicks));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delta += (now = System.nanoTime() - pastTime) / ns;
            pastTime = now;

            while (delta > 0) {
                tick();
                delta--;
            }
        }
    }
}

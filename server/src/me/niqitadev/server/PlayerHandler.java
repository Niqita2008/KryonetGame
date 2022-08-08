package me.niqitadev.server;

import com.badlogic.gdx.utils.Array;
import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.PlayersUpdatePacket;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class PlayerHandler implements Runnable {

    private boolean running;

    public synchronized void start() {
        running = true;
        new Thread(this).start();
    }

    public final Array<OnlinePlayer> onlinePlayers = new Array<>();

    public OnlinePlayer getPlayer(final @NotNull String name) {
        return onlinePlayers.isEmpty() ? null : Arrays.stream(onlinePlayers.items)
                .filter(p -> p.name.equals(name)).findFirst().orElse(null);
    }

    public OnlinePlayer getPlayer(final @NotNull Connection connection) {
        return onlinePlayers.isEmpty() ? null : Arrays.stream(onlinePlayers.items)
                .filter(p -> p.connection == connection).findFirst().orElse(null);
    }

    public void tick() {
        onlinePlayers.forEach(OnlinePlayer::update);
        final PlayersUpdatePacket playersUpdatePacket = new PlayersUpdatePacket();

        onlinePlayers.forEach(p -> playersUpdatePacket.players.put(p.name, p.position));

        Starter.instance.server.sendToAllUDP(playersUpdatePacket);
    }

    @Override
    public void run() {
        long pastTime = System.nanoTime(), now;

        for (double delta = 0; running; pastTime = now) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            delta += (now = System.nanoTime() - pastTime) / 1.6666667E7f;

            for (; delta > 0; delta--) tick();
        }
    }
}

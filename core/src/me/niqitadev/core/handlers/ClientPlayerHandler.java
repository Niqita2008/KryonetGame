package me.niqitadev.core.handlers;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ObjectSet;
import me.niqitadev.core.tools.ClientPlayer;

public class ClientPlayerHandler {

    private final ObjectSet<ClientPlayer> players = new ObjectSet<>();

    public ClientPlayer getPlayer(final String name) {
        final ClientPlayer[] value = {null};
        CustomForEach.forEach(players, (e, breaker) -> {
            if (!e.name.equals(name)) return;
            value[0] = e;
            breaker.stop();
        });
        return value[0];
    }

    public void updateAndRender(final float delta, SpriteBatch batch) {
        players.forEach(f -> {
            f.update(delta);
            f.render(batch);
        });
    }

    public void addPlayer(final ClientPlayer player) {
        players.add(player);
    }

    public void removePlayer(final ClientPlayer player) {
        players.remove(player);
    }

    public void clear() {
        players.clear();
    }
}
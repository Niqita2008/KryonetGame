package me.niqitadev.core.handlers;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import me.niqitadev.core.client_players.Me;
import me.niqitadev.core.client_players.OtherClientPlayer;

import java.util.HashSet;

public class ClientPlayerHandler {
    private final HashSet<OtherClientPlayer> players = new HashSet<>();
    public Me me;
    private final Camera camera;
    private final Batch batch;

    public ClientPlayerHandler(Camera camera, Batch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    public OtherClientPlayer getPlayer(final String name) {
        return players.stream().filter(k -> k.name.equals(name)).findFirst().orElse(null);
    }

    public void addMe(String name) {
        me = new Me(name, camera);
    }

    public void update(final float delta) {
        batch.begin();
        if (me != null) me.draw(batch, delta);
        players.forEach(h -> h.draw(batch, delta));
        batch.end();
    }

    public void addPlayer(final OtherClientPlayer player) {
        players.add(player);
    }

    public void removePlayer(final String name) {
        final OtherClientPlayer player = getPlayer(name);
        if (player != null) players.remove(player);
    }

    public void clear() {
        players.clear();
    }
}
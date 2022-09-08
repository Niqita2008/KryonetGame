package me.niqitadev.core.handlers;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.niqitadev.core.client_players.Me;
import me.niqitadev.core.client_players.OtherClientPlayer;

import java.util.HashSet;

public class ClientPlayerHandler {
    private final HashSet<OtherClientPlayer> players = new HashSet<>();

    public Me me;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    public ClientPlayerHandler(final OrthographicCamera camera, final SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    public OtherClientPlayer getPlayer(final String name) {
        return players.stream().filter(k -> k.name.equals(name)).findFirst().orElse(null);
    }

    public void addMe(final String name) {
        me = new Me(name, camera);
    }

    public void update(final float delta, Texture grass) {
        me.draw(batch, delta, grass, players);
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
package me.niqitadev.core.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import me.niqitadev.core.client_players.Me;
import me.niqitadev.core.client_players.OtherClientPlayer;

import java.util.HashSet;

public class ClientPlayerHandler {
    private final HashSet<OtherClientPlayer> players = new HashSet<>();
    private final PerspectiveCamera camera;
    private final ModelBatch batch;
    private final Environment environment;
    private final ModelInstance instance;

    public Me me;

    public ClientPlayerHandler(final PerspectiveCamera camera, final ModelBatch batch, Environment environment) {
        this.camera = camera;
        this.batch = batch;
        this.environment = environment;
        ModelBuilder modelBuilder = new ModelBuilder();
        Model rect = modelBuilder.createRect(
                100,
                0,
                -100,
                -100,
                0,
                -100,
                -100,
                0,
                100,
                100,
                0,
                100,
                0,
                1,
                0,
                new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(rect);
    }

    public OtherClientPlayer getPlayer(final String name) {
        return players.stream().filter(k -> k.name.equals(name)).findFirst().orElse(null);
    }

    public void addMe(final String name) {
        me = new Me(name, camera);
    }

    public void update() {
        batch.begin(camera);
        batch.render(instance, environment);
        players.forEach(p -> p.draw(batch));
        me.draw();
        batch.end();
    }


    public void removePlayer(final String name) {
        final OtherClientPlayer player = getPlayer(name);
        if (player != null) players.remove(player);
    }

    public void clear() {
        players.clear();
    }

    public void addPlayer(final String name, final float x, final float y, final float z) {
        new OtherClientPlayer(name, x, y, z);
    }
}
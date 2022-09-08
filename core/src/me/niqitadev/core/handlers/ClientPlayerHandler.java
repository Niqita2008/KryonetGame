package me.niqitadev.core.handlers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import me.niqitadev.core.client_players.Me;
import me.niqitadev.core.client_players.OtherClientPlayer;

import java.util.HashSet;

public class ClientPlayerHandler {
    private final HashSet<OtherClientPlayer> players = new HashSet<>();
    private final PerspectiveCamera camera;
    private final ModelBatch batch;
    private final ModelInstance instance;

    public Me me;

    public ClientPlayerHandler(final PerspectiveCamera camera, final ModelBatch batch) {
        this.camera = camera;
        this.batch = batch;
        ModelBuilder modelBuilder = new ModelBuilder();
        Model rect = modelBuilder.createBox(1, 1, 1,
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
        batch.render(instance);
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
package me.niqitadev.core.players;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import me.niqitadev.core.Starter;

public final class OtherClientPlayer {
    public final String name;
    private final Vector3 pos, servPos, servDir, dir;
    private ModelInstance instance;

    public OtherClientPlayer(final String name, float x, float y, float z) {
        this.name = name;
        Gdx.app.postRunnable(() -> {
            Model box = Starter.modelBuilder.createBox(2, 3, 2,
                    new Material(ColorAttribute.createDiffuse(Color.LIGHT_GRAY)), VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
            instance = new ModelInstance(box);
        });
        servPos = new Vector3(x, y, z);
        pos = new Vector3(x, y, z);
        servDir = new Vector3();
        dir = new Vector3();
    }

    public void draw(final ModelBatch batch) {
        if (instance == null) return;
        instance.transform.setToRotation(dir.interpolate(servDir, .03f, Interpolation.linear), Vector3.X);
        instance.transform.setTranslation(pos.interpolate(servPos, .03f, Interpolation.linear));
        batch.render(instance);
    }

    @Override
    public boolean equals(final Object obj) {
        if (!(obj instanceof OtherClientPlayer otherClientPlayer)) return false;
        return name.equals(otherClientPlayer.name);
    }

    public void setServPos(final float x, final float y, final float z, final float dirX, final float dirY, final float dirZ) {
        servPos.set(x, y, z);
        servDir.set(dirX, 0, dirZ);
    }

}
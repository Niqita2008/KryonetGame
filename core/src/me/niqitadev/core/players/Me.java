package me.niqitadev.core.players;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public final class Me {
    public final String name;
    private final PerspectiveCamera camera;
    private final Vector3 servPos = new Vector3(), pos = new Vector3();

    public Me(final String name, final PerspectiveCamera camera) {
        this.name = name;
        this.camera = camera;
    }

    public void draw() {
        pos.interpolate(servPos, .05f, Interpolation.linear);
        camera.position.set(pos);
    }

    public void setServPos(final float x, final float y, final float z) {
        servPos.set(x, y, z);
    }
}

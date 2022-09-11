package me.niqitadev.core.players;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;

public final class Me {
    public final String name;
    private final PerspectiveCamera cam;
    private final Vector3 servPos = new Vector3(), pos = new Vector3();

    public Me(final String name, final PerspectiveCamera cam) {
        this.name = name;
        this.cam = cam;
    }

    public void draw() {
        pos.interpolate(servPos, .05f, Interpolation.linear);
        cam.position.set(pos);
    }

    public void setServPos(final float x, final float y, final float z) {
        servPos.set(x, y, z);
    }
}

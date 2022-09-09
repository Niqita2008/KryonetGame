package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.MovePacket;

public class InGameInputListener extends AbstractPressListener {
    private final Starter starter;
    private final PerspectiveCamera camera;

    private final Vector3 tmp = new Vector3(), tmp1 = new Vector3();
    public boolean a, w, s, d, up, down;
    public float degreesPerPixel = .5f;

    public InGameInputListener(Starter starter, PerspectiveCamera camera) {
        this.starter = starter;
        this.camera = camera;
    }

    @Override
    public void keyDown(String code) {
        switch (code) {
            case "w" -> w = true;
            case "s" -> s = true;
            case "a" -> a = true;
            case "d" -> d = true;
            case "space" -> up = true;
            case "l-shift" -> down = true;
            case "escape" -> starter.stop();
        }
    }

    @Override
    void keyUp(String code) {
        switch (code) {
            case "w" -> w = false;
            case "s" -> s = false;
            case "a" -> a = false;
            case "d" -> d = false;
            case "space" -> up = false;
            case "l-shift" -> down = false;
        }
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        float deltaX = -Gdx.input.getDeltaX() * degreesPerPixel;
        float deltaY = -Gdx.input.getDeltaY() * degreesPerPixel;
        camera.direction.rotate(camera.up, deltaX);
        tmp.set(camera.direction).crs(camera.up).nor();
        camera.direction.rotate(tmp, deltaY);
        return true;
    }


    public void reset() {
        a = w = s = d = false;
    }

    public MovePacket get() {
        if (w) tmp.add(tmp1.set(camera.direction.x, 0, camera.direction.z).nor());
        if (s) tmp.add(tmp1.set(-camera.direction.x, 0, -camera.direction.z).nor());
        if (d) tmp.add(tmp1.set(camera.direction.x, 0, camera.direction.z).crs(camera.up).nor());
        if (a) tmp.add(tmp1.set(-camera.direction.x, 0, -camera.direction.z).crs(camera.up).nor());
        if (up) tmp.y += .1f;
        if (down) tmp.y -= .1f;
        final float x = tmp.x, y = tmp.y, z = tmp.z;
        tmp.set(0, 0, 0);
        return a || w || s || d || up || down ? new MovePacket(x, y, z) : null;
    }

}

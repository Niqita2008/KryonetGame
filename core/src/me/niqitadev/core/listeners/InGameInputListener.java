package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.MovePacket;

public class InGameInputListener extends AbstractPressListener {
    private final Starter starter;
    private final PerspectiveCamera camera;

    private final Vector3 tmp = new Vector3();
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
            case "shift" -> down = true;
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
            case "shift" -> down = false;
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
        if (w && !s) tmp.set(camera.direction).nor();
        if (s && !w) tmp.set(camera.direction).nor();
        if (a && !d) tmp.set(camera.direction).crs(camera.up).nor();
        if (d && !a) tmp.set(camera.direction).crs(camera.up).nor();
        if (up && !down) tmp.set(camera.up).nor();
        if (down && !up) tmp.set(camera.up).nor();
        return a || w || s || d || up || down ? new MovePacket(a, w, s, d, up, down) : null;
    }

}

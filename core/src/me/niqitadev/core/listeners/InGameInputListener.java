package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.Vector3;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.MovePacket;

public class InGameInputListener extends AbstractPressListener {
    private final Starter starter;
    private final PerspectiveCamera cam;

    private final Vector3 tmp = new Vector3(), tmp1 = new Vector3();
    public boolean a, w, s, d, up, down;
    public float degreesPerPixel = .5f;
    private boolean moved;

    public InGameInputListener(Starter starter, PerspectiveCamera cam) {
        this.starter = starter;
        this.cam = cam;
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
        cam.direction.rotate(cam.up, -Gdx.input.getDeltaX() * degreesPerPixel).rotate(tmp.set(cam.direction).crs(cam.up).nor(), -Gdx.input.getDeltaY() * degreesPerPixel);
        return moved = true;
    }


    public void reset() {
        a = w = s = d = false;
    }

    public MovePacket get() {
        if (w) tmp1.add(tmp.set(cam.direction.x, 0, cam.direction.z).nor());
        if (s) tmp1.add(tmp.set(-cam.direction.x, 0, -cam.direction.z).nor());
        if (d) tmp1.add(tmp.set(cam.direction.x, 0, cam.direction.z).crs(cam.up).nor());
        if (a) tmp1.add(tmp.set(-cam.direction.x, 0, -cam.direction.z).crs(cam.up).nor());
        if (up) tmp1.y += .1f;
        if (down) tmp1.y -= .1f;
        final float x = tmp1.x, y = tmp1.y, z = tmp1.z;
        tmp1.set(0, 0, 0);
        return a || w || s || d || up || down || moved ? new MovePacket(x, y, z, cam.view.val) : null;
    }

}

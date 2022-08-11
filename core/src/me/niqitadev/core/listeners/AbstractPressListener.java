package me.niqitadev.core.listeners;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;

public abstract class AbstractPressListener extends InputAdapter {
    public float x, y;
    boolean a, w, s, d;

    @Override
    public boolean keyDown(int keycode) {
        keyDown(Input.Keys.toString(keycode));
        return false;
    }

    abstract void keyDown(String code);

    @Override
    public boolean keyUp(int keycode) {
        keyUp(Input.Keys.toString(keycode));
        return false;
    }

    abstract void keyUp(String code);

    abstract boolean update(float delta, float speed);
}

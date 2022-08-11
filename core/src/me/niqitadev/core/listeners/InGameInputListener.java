package me.niqitadev.core.listeners;

import me.niqitadev.core.Starter;

public class InGameInputListener extends AbstractPressListener {

    private final Starter starter;

    public InGameInputListener(Starter starter) {
        this.starter = starter;
    }

    @Override
    public void keyDown(String code) {
        switch (code) {
            case "W" -> w = true;
            case "S" -> s = true;
            case "A" -> a = true;
            case "D" -> d = true;
            case "Escape" -> starter.stop();
        }
    }

    @Override
    void keyUp(String code) {
        switch (code) {
            case "W" -> w = false;
            case "S" -> s = false;
            case "A" -> a = false;
            case "D" -> d = false;
        }
    }

    @Override
    public boolean update(float delta, float speed) {
        if (!a && !w && !s && !d || a && s && w && d) return false;
        float deltaSpeed = delta * speed;
        y = (w ? deltaSpeed : 0) - (s ? deltaSpeed : 0);
        x = (d ? deltaSpeed : 0) - (a ? deltaSpeed : 0);
        return true;
    }

}

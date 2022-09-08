package me.niqitadev.core.listeners;

import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.MovePacket;

public class InGameInputListener extends AbstractPressListener {
    public boolean a, w, s, d;
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

    public void reset() {
        a = w = s = d = false;
    }

    public MovePacket get() {
        return a || w || s || d ? new MovePacket(a, w, s, d) : null;
    }

}

package me.niqitadev.core.listeners;

public class InGameInputListener extends PressListener {

    @Override
    public void keyDown(String code) {
        switch (code) {
            case "W" -> w = true;
            case "S" -> s = true;
            case "A" -> a = true;
            case "D" -> d = true;
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

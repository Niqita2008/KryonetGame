package me.niqitadev.core.packets;

public class PlayerUpdatePacket {
    public String name;
    public float x, y;

    public PlayerUpdatePacket set(final String name, final float x, final float y) {
        this.name = name;
        this.x = x;
        this.y = y;
        return this;
    }
}

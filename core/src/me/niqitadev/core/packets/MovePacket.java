package me.niqitadev.core.packets;

public class MovePacket {
    public MovePacket() {
    }

    public MovePacket(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float x, y; // add
}

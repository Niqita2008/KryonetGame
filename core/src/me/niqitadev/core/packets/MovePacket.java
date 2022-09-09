package me.niqitadev.core.packets;

public class MovePacket {

    public final float x, y, z;

    public MovePacket() {
        x = y = 0;
        z = 5;
    }

    public MovePacket(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

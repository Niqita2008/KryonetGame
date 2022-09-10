package me.niqitadev.core.packets;

public class MovePacket {

    public double x, y, z;

    public MovePacket() {}

    public MovePacket(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

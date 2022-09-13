package me.niqitadev.core.packets;

public class MovePacket {

    public float[] values;
    public double x, y, z;
    public MovePacket() {
    }

    public MovePacket(final double x, final double y, final double z, final float[] values) {
        this.values = values;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

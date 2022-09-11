package me.niqitadev.core.packets;

public class MovePacket {

    public double x, y, z;
    public float dirX, dirY, dirZ;

    public MovePacket() {}

    public MovePacket(final double x, final double y, final double z, final float dirX, final float dirY, final float dirZ) {
        this.dirX = dirX;
        this.dirY = dirY;
        this.dirZ = dirZ;
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

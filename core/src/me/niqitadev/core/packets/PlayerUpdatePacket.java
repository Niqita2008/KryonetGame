package me.niqitadev.core.packets;

public class PlayerUpdatePacket {
    public String name;
    public float y, x, z, dirX, dirY, dirZ;

    public PlayerUpdatePacket(final String name, final float x, final float y, final float z, final double rot) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlayerUpdatePacket() {}

    public PlayerUpdatePacket set(final String name, final float x, final float y, final float z, final float dirX, final float dirY, final float dirZ) {
        this.name = name;
        this.dirX = dirX;
        this.dirY = dirY;
        this.dirZ = dirZ;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}

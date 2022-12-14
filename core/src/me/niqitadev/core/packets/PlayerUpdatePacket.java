package me.niqitadev.core.packets;

public class PlayerUpdatePacket {
    public String name;
    public float y, x, z;
    public float[] values;

    public PlayerUpdatePacket(final String name, final float x, final float y, final float z) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public PlayerUpdatePacket() {}

    public PlayerUpdatePacket set(final String name, final float x, final float y, final float z, final float[] values) {
        this.values = values;
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }
}

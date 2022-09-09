package me.niqitadev.core.packets;

public class MovePacket {
    public boolean a, w, s, d, up, down;

    public MovePacket() {
    }

    public MovePacket(boolean a, boolean w, boolean s, boolean d, boolean up, boolean down) {
        this.down = down;
        this.up = up;
        this.a = a;
        this.w = w;
        this.s = s;
        this.d = d;
    }

}

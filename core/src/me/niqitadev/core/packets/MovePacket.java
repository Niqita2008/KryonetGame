package me.niqitadev.core.packets;

public class MovePacket {
    public boolean a, w, s, d;

    public MovePacket() {
    }

    public MovePacket(boolean a, boolean w, boolean s, boolean d) {
        this.a = a;
        this.w = w;
        this.s = s;
        this.d = d;
    }

}

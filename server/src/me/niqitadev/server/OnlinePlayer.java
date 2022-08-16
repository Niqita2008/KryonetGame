package me.niqitadev.server;

import com.esotericsoftware.kryonet.Connection;

public class OnlinePlayer {
    public final String name;
    public float x, y;
    public final Connection connection;

    public OnlinePlayer(final String name, final Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public void update(boolean d, boolean a, boolean w, boolean s) {
        final float speed = (a || d) && (w || s) ? 17 : 30;
        x += (d ? speed : 0) - (a ? speed : 0);
        y += (w ? speed : 0) - (s ? speed : 0);
    }
}

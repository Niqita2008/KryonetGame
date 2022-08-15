package me.niqitadev.server;

import com.esotericsoftware.kryonet.Connection;

public class OnlinePlayer {
    public final String name;
    public float x, y, speed = 30;
    public final Connection connection;

    public OnlinePlayer(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public void update(boolean d, boolean a, boolean w, boolean s) {
        if ((a || d) && (w || s))
        x += (d ? speed : 0) - (a ? speed : 0);
        y += (w ? speed : 0) - (s ? speed : 0);
    }
}

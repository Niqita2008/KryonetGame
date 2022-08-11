package me.niqitadev.server;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

import java.util.Objects;

public class OnlinePlayer {
    public final String name;
    public float speed = 5;
    public boolean w, s, a, d;
    public final Vector2 pos = new Vector2();
    public final Connection connection;

    public OnlinePlayer(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public void update() {
        pos.add((d ? speed : 0) - (a ? speed : 0), (w ? speed : 0) - (s ? speed : 0));
    }
}

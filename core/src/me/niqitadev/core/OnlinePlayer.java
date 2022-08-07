package me.niqitadev.core;

import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.kryonet.Connection;

public class OnlinePlayer {
    public final String name;
    public float speed = 5;
    public boolean w, s, a, d;
    public final Vector2 position = new Vector2();
    public final Connection connection;

    public OnlinePlayer(String name, Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public void update() {
        position.add((d ? speed : 0) - (a ? speed : 0), (w ? speed : 0) - (s ? speed : 0));
    }
}

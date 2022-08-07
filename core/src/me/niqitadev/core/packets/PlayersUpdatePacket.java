package me.niqitadev.core.packets;

import com.badlogic.gdx.math.Vector2;

import java.util.HashMap;

public class PlayersUpdatePacket {
    public final HashMap<String, Vector2> players = new HashMap<>();
}

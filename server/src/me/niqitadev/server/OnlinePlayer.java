package me.niqitadev.server;

import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.MovePacket;

public class OnlinePlayer {
    public final String name;
    public final Connection connection;
    public float x, z, y = 5, dirX, dirY, dirZ;
    private boolean changed;

    public OnlinePlayer(final String name, final Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public boolean isUpdated() {
        boolean b = changed;
        changed = false;
        return b;
    }

    public void apply(MovePacket mp) {
        x += mp.x;
        y += mp.y;
        z += mp.z;
        dirX = mp.dirX;
        dirY = mp.dirY;
        dirZ = mp.dirZ;
        changed = true;
        if (y > 990) y = 990;
        else if (y < -990) y = -990;
        if (x > 990) x = 990;
        else if (x < -990) x = -990;
        if (z > 990) z = 990;
        else if (z < -990) z = -990;
    }
}

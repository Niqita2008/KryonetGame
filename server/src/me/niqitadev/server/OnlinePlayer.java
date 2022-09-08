package me.niqitadev.server;

import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.MovePacket;

public class OnlinePlayer {
    public final String name;
    public final Connection connection;
    public float x, z, y;
    private boolean changed;

    public OnlinePlayer(final String name, final Connection connection) {
        this.name = name;
        this.connection = connection;
    }

    public boolean isChanged() {
        boolean b = changed;
        changed = false;
        return b;
    }

    public void update(MovePacket mp) {
        final float speed = (mp.a || mp.d) && (mp.w || mp.s) ? .5f : 1;
        x += (mp.d ? speed : 0) - (mp.a ? speed : 0);
        z += (mp.s ? speed : 0) - (mp.w ? speed : 0);
        changed = true;
        if (y > 990) y = 990;
        else if (y < -990) y = -990;
        if (x > 990) x = 990;
        else if (x < -990) x = -990;
        if (z > 990) z = 990;
        else if (z < -990) z = -990;
    }
}

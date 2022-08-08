package me.niqitadev.server;


import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.listeners.InGameInputListener;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.JoinResponse;
import me.niqitadev.core.packets.MovePacket;

public class ServerListener extends com.esotericsoftware.kryonet.Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof JoinRequest request) {
            System.out.println(request.username + " joined.");
            connection.sendTCP(new JoinResponse());
        } else if (object instanceof MovePacket) {
            System.out.println("moved");
        }
    }
}

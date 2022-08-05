package me.niqitadev.server;


import com.esotericsoftware.kryonet.Connection;
import me.niqitadev.core.packets.JoinRequest;
import me.niqitadev.core.packets.JoinResponse;

public class ServerListener extends com.esotericsoftware.kryonet.Listener {
    @Override
    public void received(Connection connection, Object object) {
        if (object instanceof JoinRequest) {
            JoinRequest request = (JoinRequest) object;
            System.out.println(request.username);

            connection.sendTCP(new JoinResponse());
        }
    }
}

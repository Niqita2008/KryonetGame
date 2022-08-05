package me.niqitadev.core;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
    @Override
    public void received(Connection connection, Object object) {
        System.out.println(object);
    }
}

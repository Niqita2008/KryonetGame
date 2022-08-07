package me.niqitadev.core;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class ClientListener extends Listener {
    private final Starter starter;

    public ClientListener(Starter starter) {
        this.starter = starter;
    }

    @Override
    public void received(Connection connection, Object object) {
        starter.setToGame();
    }
}

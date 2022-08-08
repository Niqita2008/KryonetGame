package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.niqitadev.core.Starter;

public class ClientListener extends Listener {
    private final Starter starter;

    public ClientListener(Starter starter) {
        this.starter = starter;
    }

    @Override
    public void received(Connection connection, Object object) {
        Gdx.app.postRunnable(starter::setToGame);
    }
}

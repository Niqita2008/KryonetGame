package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.JoinEnum;
import me.niqitadev.core.packets.JoinResponse;

public class ClientListener extends Listener {
    private final Starter starter;
    private final Label errorLabel;

    public ClientListener(final Starter starter, Label errorLabel) {
        this.starter = starter;
        this.errorLabel = errorLabel;
    }

    @Override
    public void received(final Connection connection, final Object object) {
        if (object instanceof JoinResponse response) {
            if (response.errorMessage == null) Gdx.app.postRunnable(starter::setToGame);
            else errorLabel.setText(response.errorMessage);
            return;
        }
    }
}

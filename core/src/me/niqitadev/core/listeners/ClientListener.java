package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.JoinResponse;
import me.niqitadev.core.packets.OtherPlayerDisconnected;
import me.niqitadev.core.packets.PlayerUpdatePacket;
import me.niqitadev.core.screens.GameScreen;
import me.niqitadev.core.client_players.OtherClientPlayer;

public class ClientListener extends Listener {
    private final Starter starter;
    private final Label errorLabel;
    private final GameScreen gameScreen;

    public ClientListener(final Starter starter, Label errorLabel) {
        gameScreen = starter.gameScreen;
        this.starter = starter;
        this.errorLabel = errorLabel;
    }

    @Override
    public void disconnected(Connection connection) {
        starter.stop();
    }

    @Override
    public void received(final Connection connection, final Object object) {
        if (object instanceof JoinResponse response) {
            if (response.errorMessage == null) Gdx.app.postRunnable(starter::setToGame);
            else errorLabel.setText(response.errorMessage);
            return;
        }
        if (object instanceof PlayerUpdatePacket playerUpdate) {
            OtherClientPlayer player = gameScreen.playerHandler.getPlayer(playerUpdate.name);
            if (player != null) {
                player.setServPos(playerUpdate.x, playerUpdate.y);
                return;
            }
            if (starter.name.equals(playerUpdate.name)) {
                gameScreen.playerHandler.me.setServPos(playerUpdate.x, playerUpdate.y);
                return;
            }
            player = new OtherClientPlayer(playerUpdate.name);
            gameScreen.playerHandler.addPlayer(player);
            player.setServPos(playerUpdate.x, playerUpdate.y);
            return;
        }
        if (object instanceof OtherPlayerDisconnected disconnected)
            gameScreen.playerHandler.removePlayer(disconnected.name);
    }
}

package me.niqitadev.core.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import me.niqitadev.core.Starter;
import me.niqitadev.core.players.OtherClientPlayer;
import me.niqitadev.core.packets.JoinResponse;
import me.niqitadev.core.packets.OtherPlayerDisconnected;
import me.niqitadev.core.packets.PlayerUpdatePacket;
import me.niqitadev.core.screens.GameScreen;

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
        if (object instanceof PlayerUpdatePacket packet) {
            final OtherClientPlayer player = gameScreen.playerHandler.getPlayer(packet.name);
            if (player != null) {
                player.setServPos(packet.x, packet.y, packet.z);
                return;
            }
            if (Starter.name.equals(packet.name)) {
                gameScreen.playerHandler.me.setServPos(packet.x, packet.y, packet.z);
                return;
            }
            gameScreen.playerHandler.addPlayer(packet.name, packet.x, packet.y, packet.z);
            return;
        }
        if (object instanceof OtherPlayerDisconnected disconnected)
            gameScreen.playerHandler.removePlayer(disconnected.name);
    }
}

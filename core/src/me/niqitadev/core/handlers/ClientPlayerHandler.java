package me.niqitadev.core.handlers;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import me.niqitadev.core.Starter;
import me.niqitadev.core.tools.Me;
import me.niqitadev.core.tools.OtherClientPlayer;
import me.niqitadev.core.tools.CustomForEach;

public class ClientPlayerHandler {
    private final Stage stage;
    private final Array<Actor> players;
    public Me me;

    public ClientPlayerHandler(Stage stage) {
        this.stage = stage;
        players = stage.getActors();
    }

    public OtherClientPlayer getPlayer(final String name) {
        final OtherClientPlayer[] value = {null};
        CustomForEach.forEach(players, (e, breaker) -> {
            if (!(e instanceof OtherClientPlayer c) || !c.name.equals(name)) return;
            value[0] = c;
            breaker.stop();

        });
        return value[0];
    }

    public void addMe(String name) {
        me = new Me(name, stage.getCamera());
        stage.addActor(me);
    }

    public void addPlayer(final OtherClientPlayer player) {
        stage.addActor(player);
    }

    public void removePlayer(final String name) {
        Actor player = getPlayer(name);
        if (player == null) return;
        players.removeValue(player, true);
    }

    public void clear() {
        stage.clear();
    }
}
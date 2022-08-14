package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryonet.Client;
import me.niqitadev.core.screens.GameScreen;
import me.niqitadev.core.screens.MenuScreen;

public class Starter extends Game {

    public SpriteBatch spriteBatch;
    public OrthographicCamera camera;
    public Client client = new Client();
    private MenuScreen menu;
    public GameScreen gameScreen;
    public ExtendViewport viewport;

    public void setToGame() {
        setScreen(gameScreen);
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
        viewport = new ExtendViewport(800, 600, camera);
        viewport.setScaling(Scaling.none);
        menu = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menu);
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render(); //important!
    }

    public void stop() {
        gameScreen.playerHandler.clear();
        client.stop();
        setScreen(menu);
    }
}

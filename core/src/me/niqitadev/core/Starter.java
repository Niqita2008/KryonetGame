package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.esotericsoftware.kryonet.Client;
import me.niqitadev.core.screens.GameScreen;
import me.niqitadev.core.screens.MenuScreen;

public class Starter extends Game {
    public SpriteBatch spriteBatch;
    public Client client = new Client();
    public OrthographicCamera orthographicCamera;
    public GameScreen gameScreen;
    public String name;
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;
    private MenuScreen menu;

    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 0, 0);
        cam.near = 1;
        cam.far = 100;
        cam.update();
        spriteBatch = new SpriteBatch();
        modelBatch = new ModelBatch();
        menu = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menu);
    }

    public void setToGame() {
        setScreen(gameScreen);
    }

    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    public void stop() {
        gameScreen.playerHandler.clear();
        client.stop();
        Gdx.app.postRunnable(() -> setScreen(menu));
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
    }
}

package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.esotericsoftware.kryonet.Client;
import me.niqitadev.core.screens.GameScreen;
import me.niqitadev.core.screens.MenuScreen;

public class Starter extends Game {

    public static ModelBuilder modelBuilder;
    public SpriteBatch spriteBatch;
    public OrthographicCamera orthographicCamera;
    public Client client = new Client();
    private MenuScreen menu;
    public GameScreen gameScreen;

    public static String name;
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;

    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam = new PerspectiveCamera(100, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(0, 4, 0);
        cam.near = 1;
        cam.far = 400;
        cam.update();
        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();
        modelBuilder = new ModelBuilder();
        menu = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menu);


    }

    public void setToGame() {
        setScreen(gameScreen);
    }

    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        super.render();
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

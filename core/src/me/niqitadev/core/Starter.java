package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.esotericsoftware.kryonet.Client;
import me.niqitadev.core.screens.*;

public class Starter extends Game {

    public SpriteBatch spriteBatch;
    public OrthographicCamera orthographicCamera;
    public Client client = new Client();
    private MenuScreen menu;
    public GameScreen gameScreen;

    public String name;
    public PerspectiveCamera cam;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;

    @Override
    public void create() {
        orthographicCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam = new PerspectiveCamera(90, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0,0,0);
        cam.near = 1f;
        cam.far = 300f;
        ModelBuilder modelBuilder = new ModelBuilder();
        model = modelBuilder.createBox(5f, 5f, 5f,
                new Material(ColorAttribute.createDiffuse(Color.GREEN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
        instance = new ModelInstance(model);
        cam.update();
        spriteBatch = new SpriteBatch();
        menu = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        setScreen(menu);
        modelBatch = new ModelBatch();


    }

    public void setToGame() {
        setScreen(gameScreen);
    }
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instance);
        modelBatch.end();
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
        model.dispose();
    }
}

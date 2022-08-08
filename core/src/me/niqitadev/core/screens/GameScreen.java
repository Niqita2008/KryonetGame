package me.niqitadev.core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.*;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;

    @Override
    public void show() {

    }

    public GameScreen(OrthographicCamera camera, SpriteBatch spriteBatch) {
        this.camera = camera;
        Texture texture = new Texture("badlogic.jpg");
        viewport = new ScalingViewport(Scaling.fillY, 800, 600, camera);
        stage = new Stage(viewport, spriteBatch);
        stage.addActor(new Image(texture));
    }

    @Override
    public void render(float delta) {
        camera.update();
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
    }
}

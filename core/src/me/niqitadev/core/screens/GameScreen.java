package me.niqitadev.core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class GameScreen extends ScreenAdapter {
    private final Stage stage;
    private final OrthographicCamera camera;
    private final StretchViewport viewport;

    public GameScreen(OrthographicCamera camera, SpriteBatch spriteBatch) {
        this.camera = camera;
        viewport = new StretchViewport(4, 3, camera);
        stage = new Stage(viewport, spriteBatch);
    }

    @Override
    public void render(float delta) {
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void dispose() {
    }
}

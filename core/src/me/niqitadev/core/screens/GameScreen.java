package me.niqitadev.core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.niqitadev.core.Starter;
import me.niqitadev.core.handlers.MoveHandler;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final MoveHandler moveHandler;

    @Override
    public void show() {

    }

    public GameScreen(Starter starter) {
        camera = starter.camera;
        Texture texture = new Texture("badlogic.jpg");
        viewport = new ScalingViewport(Scaling.fillY, 800, 600, camera);
        stage = new Stage(viewport, starter.spriteBatch);
        stage.addActor(new Image(texture));
        moveHandler = new MoveHandler(starter.client);
        moveHandler.start();
    }

    @Override
    public void render(float delta) {
        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void dispose() {
        moveHandler.running = false;
        stage.dispose();
            }
}

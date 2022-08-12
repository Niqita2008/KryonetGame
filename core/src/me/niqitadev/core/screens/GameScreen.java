package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.niqitadev.core.Starter;
import me.niqitadev.core.handlers.ClientPlayerHandler;
import me.niqitadev.core.handlers.MoveHandler;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final Viewport viewport;
    private final Stage stage;
    private final MoveHandler moveHandler;
    public final ClientPlayerHandler playerHandler;
    private final SpriteBatch batch;

    @Override
    public void hide() {
        moveHandler.running = false;
    }

    @Override
    public void show() {
        moveHandler.start();
        Gdx.input.setInputProcessor(moveHandler.listener);
    }

    public GameScreen(Starter starter) {
        camera = starter.camera;
        batch = starter.spriteBatch;
        viewport = new ScalingViewport(Scaling.fillY, 800, 600, camera);
        stage = new Stage(viewport, starter.spriteBatch);
        moveHandler = new MoveHandler(starter);
        playerHandler = new ClientPlayerHandler();
    }


    @Override
    public void render(float delta) {
        camera.update();
        stage.act(delta);
        stage.draw();
        batch.begin();
        playerHandler.updateAndRender(delta, batch);
        batch.end();
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

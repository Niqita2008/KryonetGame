package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.niqitadev.core.Starter;
import me.niqitadev.core.handlers.ClientPlayerHandler;
import me.niqitadev.core.handlers.MoveHandler;

public class GameScreen extends ScreenAdapter {
    private final OrthographicCamera camera;
    private final ExtendViewport viewport;
    private final MoveHandler moveHandler;
    private final Starter starter;
    public final ClientPlayerHandler playerHandler;
    private final SpriteBatch batch;

    @Override
    public void hide() {
        moveHandler.stop();
    }

    @Override
    public void show() {
        playerHandler.addMe(starter.name);
        moveHandler.start();
        Gdx.input.setInputProcessor(moveHandler.listener);
    }

    public GameScreen(final Starter starter) {
        camera = starter.camera;
        batch = starter.spriteBatch;
        viewport = new ExtendViewport(800, 600, camera);
        viewport.setScaling(Scaling.none);
        this.starter = starter;
        playerHandler = new ClientPlayerHandler(camera, batch);
        moveHandler = new MoveHandler(starter);
    }


    @Override
    public void render(final float delta) {
        camera.update();
        playerHandler.update(delta);
        batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void resize(final int width, final int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        moveHandler.stop();
    }

}

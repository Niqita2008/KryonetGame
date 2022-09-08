package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import me.niqitadev.core.Starter;
import me.niqitadev.core.handlers.ClientPlayerHandler;
import me.niqitadev.core.handlers.MoveHandler;

public class GameScreen extends ScreenAdapter {
    public final ClientPlayerHandler playerHandler;
    private final PerspectiveCamera camera;
    private final ExtendViewport viewport;
    private final MoveHandler moveHandler;
    private final Starter starter;

    public GameScreen(final Starter starter) {
        camera = starter.cam;
        ModelBatch batch = starter.modelBatch;
        viewport = new ExtendViewport(800, 600, camera);
        viewport.setScaling(Scaling.none);
        playerHandler = new ClientPlayerHandler(camera, batch);
        this.starter = starter;
        moveHandler = new MoveHandler(starter);
    }

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

    @Override
    public void render(final float delta) {
        playerHandler.update();
        camera.update();
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

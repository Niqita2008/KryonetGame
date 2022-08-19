package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
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
    private final Texture grass;

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
        grass = new Texture("grass.png");
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
        ScreenUtils.clear(0.375f, 0, 0, 1);
        camera.update();
        batch.begin();
        int height = grass.getHeight(), width = grass.getWidth();
        for (int x = -1000; x < 1000; x += width)
            for (int y = -1000; y < 1000; y += height) {
                batch.draw(grass, x, y);
            }
        playerHandler.update(delta);
        batch.setProjectionMatrix(camera.combined);
        batch.end();
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

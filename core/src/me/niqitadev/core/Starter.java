package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.niqitadev.core.screens.GameScreen;
import me.niqitadev.core.screens.MenuScreen;

public class Starter extends Game {

    public SpriteBatch spriteBatch;
    public OrthographicCamera camera;

    public void setToGame() {
        setScreen(new GameScreen(camera, spriteBatch));
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        spriteBatch = new SpriteBatch();
        setScreen(new MenuScreen(this));
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render(); //important!
    }
}

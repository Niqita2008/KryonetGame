package me.niqitadev.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import me.niqitadev.game.screens.MenuScreen;

public class Starter extends Game {

    @Override
    public void create() {
        final OrthographicCamera camera = new OrthographicCamera();
        setScreen(new MenuScreen(camera));
    }

    public void render() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
        super.render(); //important!
    }
}

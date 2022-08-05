package me.niqitadev.core;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import me.niqitadev.core.screens.MenuScreen;

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

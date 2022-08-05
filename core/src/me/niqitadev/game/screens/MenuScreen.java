package me.niqitadev.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class MenuScreen extends ScreenAdapter {

    private final ScreenViewport viewport;
    private final OrthographicCamera camera;
    private final SpriteBatch spriteBatch;
    private final Texture texture;
    private final Stage stage;
    private final Table table;

    @Override
    public void dispose() {
        texture.dispose();
        spriteBatch.dispose();
    }

    public MenuScreen(OrthographicCamera camera) {
        viewport = new ScreenViewport(camera);
        this.camera = camera;
        spriteBatch = new SpriteBatch();
        stage = new Stage(viewport, spriteBatch);
        texture = new Texture("badlogic.jpg");
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        table = new Table(skin);
        table.setBounds(0, 0, 800, 600);
        stage.addActor(table);

        TextButton button1 = new TextButton("Leave game", skin);
        TextButton button2 = new TextButton("Connect", skin);

        table.clear();
        table.add(button1).height(50).width(250).row();
        table.add(button2).padTop(50).height(50).width(250).row();

    }

    @Override
    public void render(float delta) {
        camera.update();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void hide() {
        super.hide();
    }
}

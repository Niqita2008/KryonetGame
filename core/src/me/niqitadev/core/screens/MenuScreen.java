package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.esotericsoftware.kryo.Kryo;
import me.niqitadev.core.listeners.ClientListener;
import me.niqitadev.core.Starter;
import me.niqitadev.core.packets.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;

public class MenuScreen extends ScreenAdapter {

    private final ScreenViewport viewport;
    private final OrthographicCamera camera;
    private final Stage stage;
    public final Label errorLabel;
    private final Starter starter;

    @Override
    public void dispose() {
        starter.dispose();
        stage.dispose();
    }

    public MenuScreen(@NotNull Starter starter) {
        this.starter = starter;
        camera = starter.camera;
        viewport = new ScreenViewport(camera);
        stage = new Stage(viewport, starter.spriteBatch);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table(skin);
        table.setBounds(0, 0, 800, 600);
        stage.addActor(table);
        Gdx.input.setInputProcessor(stage);

        final TextField ipTextField = new TextField("localhost", skin),
                portTextField = new TextField("7392", skin),
                usernameTextField = new TextField("User" + (int) (Math.random() * 9000 + 999), skin);

        final TextButton connectButton = new TextButton("Connect", skin),
                exitButton = new TextButton("Exit game", skin);

        final Label[] labels = {new Label("Server IP", skin), new Label("Port", skin), new Label("Username", skin)};

        for (Label label : labels) {
            label.setColor(Color.LIGHT_GRAY);
            label.setFontScale(.9f);
        }

        errorLabel = new Label(null, skin);
        errorLabel.setColor(Color.RED);
        errorLabel.setFontScale(.9f);
        connectButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                starter.client.start();
                Kryo kryo = starter.client.getKryo();
                kryo.register(JoinRequest.class);
                kryo.register(PlayerUpdatePacket.class);
                kryo.register(MovePacket.class);
                kryo.register(JoinEnum.class);
                kryo.register(JoinResponse.class);

                starter.client.addListener(new ClientListener(starter, errorLabel));
                try {
                    int port = Integer.parseInt(portTextField.getText());
                    starter.client.connect(10000, ipTextField.getText(), port, port);
                } catch (IOException e) {
                    errorLabel.setText(e.getLocalizedMessage());
                    return false;
                }

                JoinRequest request = new JoinRequest();
                request.username = usernameTextField.getText();
                starter.client.sendTCP(request);
                return false;
            }
        });

        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return false;
            }
        });

        table.add(labels[0]).height(35).padRight(100).width(200).row();
        table.add(labels[1]).pad(-35, 450, 0, 0).height(35).width(200).row();
        table.add(ipTextField).padRight(70).height(35).width(300).row();
        table.add(portTextField).pad(-35, 300, 0, 0).height(35).width(70).row();
        table.add(labels[2]).padTop(20).height(35).width(210).row();
        table.add(usernameTextField).padTop(-5).height(35).width(210).row();
        table.add(connectButton).padTop(20).height(35).width(210).row();
        table.add(errorLabel).padTop(20).row();
        table.add(exitButton).padTop(20).height(35).width(210).row();

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
    public void show() {
    }

    @Override
    public void hide() {
    }
}

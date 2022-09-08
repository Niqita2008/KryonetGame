package me.niqitadev.core.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.esotericsoftware.kryo.Kryo;
import me.niqitadev.core.Starter;
import me.niqitadev.core.listeners.ClientListener;
import me.niqitadev.core.packets.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MenuScreen extends ScreenAdapter {

    public final Label errorLabel;
    public final TextButton connectButton;
    private final ExtendViewport viewport;
    private final OrthographicCamera camera;
    private final Stage stage;

    public MenuScreen(@NotNull Starter starter) {
        camera = starter.orthographicCamera;
        viewport = new ExtendViewport(800, 600, camera);
        stage = new Stage(viewport, starter.spriteBatch);
        Skin skin = new Skin(Gdx.files.internal("uiskin.json"));
        Table table = new Table(skin);
        table.setBounds(0, 0, 800, 600);
        stage.addActor(table);

        final TextField ipTextField = new TextField("localhost", skin),
                portTextField = new TextField("7392", skin),
                usernameTextField = new TextField("User" + (int) (Math.random() * 9000 + 999), skin);

        final TextButton exitButton = new TextButton("Exit game", skin);

        final Label[] labels = {new Label("Server IP", skin), new Label("Port", skin), new Label("Username", skin)};

        for (Label label : labels) label.setColor(Color.LIGHT_GRAY);

        errorLabel = new Label(null, skin);
        errorLabel.setColor(Color.RED);
        connectButton = new TextButton("Connect", skin);
        connectButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                starter.client.start();
                Kryo kryo = starter.client.getKryo();
                kryo.register(JoinRequest.class);
                kryo.register(PlayerUpdatePacket.class);
                kryo.register(MovePacket.class);
                kryo.register(JoinRequestEnum.class);
                kryo.register(OtherPlayerDisconnected.class);
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
                starter.name = request.username;
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
    public void dispose() {
        stage.dispose();
    }

    @Override
    public void render(float delta) {
        camera.update();
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void hide() {
    }
}

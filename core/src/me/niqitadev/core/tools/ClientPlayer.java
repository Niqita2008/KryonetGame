package me.niqitadev.core.tools;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import me.niqitadev.core.handlers.ResourceHandler;

public final class ClientPlayer {
    public final String name;
    private final Vector2 pos, serverPosition;
    private float pastTime;
    private final GlyphLayout layout;
    private final BitmapFont font;

    public ClientPlayer(String name) {
        this.name = name;
        serverPosition = new Vector2();
        pos = new Vector2();
        layout = new GlyphLayout();
        font = new BitmapFont();
    }

    public void render(SpriteBatch batch) {
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        int regionWidth = frame.getRegionWidth(), regionHeight = frame.getRegionHeight();
        batch.draw(frame, pos.x, pos.y, regionWidth, regionHeight);

        layout.setText(font, name);

        font.draw(batch, name, pos.x + regionWidth / 2f - layout.width / 2, pos.y + regionHeight + 10);
    }

    public void update(final float delta) {
        pastTime += delta;
        pos.interpolate(this.serverPosition, .2f, Interpolation.linear);
    }

    public void add(float x, float y) {
        pos.add(x, y);
    }

}

package me.niqitadev.core.client_players;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import me.niqitadev.core.handlers.ResourceHandler;

public final class OtherClientPlayer {
    public final String name;
    private final Vector2 pos, servPos;
    private final float regionWidth, regionHeight, fontWidth, fontHeight;
    private float pastTime;
    private final BitmapFont font;

    public OtherClientPlayer(final String name) {
        this.name = name;
        servPos = new Vector2();
        pos = new Vector2();
        font = new BitmapFont();
        font.setColor(.2f, .5f, .7f, 1);
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(0, true);
        final GlyphLayout glyphLayout = new GlyphLayout(font, name);
        regionHeight = frame.getRegionHeight() / 2f;
        regionWidth = frame.getRegionWidth() / 2f;
        fontWidth = -glyphLayout.width / 2f;
        fontHeight = glyphLayout.height + regionHeight;
    }

    public void draw(final Batch batch, final float delta) {
        pastTime += delta;
        pos.interpolate(servPos, .15f, Interpolation.circle);
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        batch.draw(frame, pos.x - regionWidth, pos.y - regionHeight);
        font.draw(batch, name, pos.x + fontWidth, pos.y + fontHeight);
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y);
    }

}
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
    private float pastTime;
    private final BitmapFont font;

    public OtherClientPlayer(final String name) {
        this.name = name;
        servPos = new Vector2();
        pos = new Vector2();
        font = new BitmapFont();
        font.setColor(.15f, .4f, .65f, 1);
    }

    public void draw(final Batch batch, final float delta) {
        pastTime += delta;
        pos.interpolate(servPos, .2f, Interpolation.fade);
        final TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        final float regionHeight = frame.getRegionHeight() / 2f, regionWidth = frame.getRegionWidth() / 2f;
        batch.draw(frame, pos.x - regionWidth, pos.y - regionHeight);
        final GlyphLayout glyphLayout = new GlyphLayout(font, name);
        font.draw(batch, name, pos.x + -glyphLayout.width / 2f, pos.y + glyphLayout.height + regionHeight);
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y);
    }

}
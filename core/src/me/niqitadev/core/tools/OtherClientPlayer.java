package me.niqitadev.core.tools;

import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.niqitadev.core.handlers.ResourceHandler;

public final class OtherClientPlayer extends Actor {
    public final String name;
    private final Vector2 pos, serverPosition;
    private final float regionWidth, regionHeight;
    private final GlyphLayout glyphLayout;
    private float pastTime;
    private final BitmapFont font;

    public OtherClientPlayer(final String name) {
        this.name = name;
        serverPosition = new Vector2();
        pos = new Vector2();
        font = new BitmapFont();
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(0, true);
        glyphLayout = new GlyphLayout(font, name);
        regionHeight = frame.getRegionHeight() / 2f;
        regionWidth = frame.getRegionWidth() / 2f;
    }

    @Override
    public void draw(final Batch batch, float parentAlpha) {
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        batch.draw(frame, pos.x - regionWidth, pos.y -regionHeight);
        font.draw(batch, name, pos.x - glyphLayout.width / 2f, pos.y + glyphLayout.height + regionHeight);
    }

    public void act(final float delta) {
        pastTime += delta;
        pos.interpolate(serverPosition, .08f, Interpolation.linear);
    }

    public void setServPos(final float x, final float y) {
        serverPosition.set(x, y);
    }

}
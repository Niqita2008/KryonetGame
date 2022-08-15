package me.niqitadev.core.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import me.niqitadev.core.handlers.ResourceHandler;

public final class Me extends Actor {
    public final String name;
    private final Camera camera;
    private final float regionWidth, regionHeight;
    private final GlyphLayout glyphLayout;
    private float pastTime;
    private final BitmapFont font;
    private final Vector3 servPos = new Vector3();
    public Me(final String name, final Camera camera) {
        this.name = name;
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(Color.GREEN);
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(0);
        regionHeight = frame.getRegionHeight() / 2f;
        regionWidth = frame.getRegionWidth() / 2f;
        glyphLayout = new GlyphLayout(font, name);
    }

    @Override
    public void draw(final Batch batch, final float parentAlpha) {
        pastTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        batch.draw(frame, camera.position.x - regionWidth, camera.position.y - regionHeight);
        font.draw(batch, name, camera.position.x - glyphLayout.width / 2f, camera.position.y + glyphLayout.height + regionHeight);
    }

    @Override
    public void act(float delta) {
        camera.position.interpolate(servPos, .08f, Interpolation.linear);
        super.act(delta);
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y, 0);
    }
}

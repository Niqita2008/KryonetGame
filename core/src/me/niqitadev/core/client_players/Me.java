package me.niqitadev.core.client_players;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector3;
import me.niqitadev.core.handlers.ResourceHandler;

public final class Me {
    public final String name, you = "You";
    private final Camera camera;
    private final float regionWidth, regionHeight;
    private final GlyphLayout nameLayout, youLayout;
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
        youLayout = new GlyphLayout(font, you);
        nameLayout = new GlyphLayout(font, name);
    }

    public void draw(final Batch batch, final float delta) {
        pastTime += delta;
        camera.position.interpolate(servPos, .2f, Interpolation.circle);
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        batch.draw(frame, camera.position.x - regionWidth, camera.position.y - regionHeight);
        font.draw(batch, you, camera.position.x - youLayout.width / 2f, camera.position.y + youLayout.height + regionHeight + nameLayout.height);
        font.draw(batch, name, camera.position.x - nameLayout.width / 2f, camera.position.y + nameLayout.height + regionHeight);
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y, 0);
    }

    public void dispose() {
        font.dispose();
    }
}

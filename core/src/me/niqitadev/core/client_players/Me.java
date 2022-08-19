package me.niqitadev.core.client_players;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import me.niqitadev.core.handlers.ResourceHandler;

public final class Me {
    public final String name;
    private final Camera camera;
    private final float regionWidth, regionHeight, youHeight, nameWidth, nameHeight;
    private float pastTime;
    private final BitmapFont font;
    private final Vector2 servPos = new Vector2(), pos = new Vector2();

    public Me(final String name, final Camera camera) {
        this.name = name;
        this.camera = camera;
        font = new BitmapFont();
        font.setColor(.1f, .8f, .1f, 1);
        final TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(0);
        regionHeight = frame.getRegionHeight() / 2f;
        regionWidth = frame.getRegionWidth() / 2f;
        final GlyphLayout nameLayout = new GlyphLayout(font, name);
        youHeight = 12 + regionHeight + nameLayout.height;
        nameWidth = -nameLayout.width / 2f;
        nameHeight = nameLayout.height + regionHeight;
    }

    public void draw(final Batch batch, final float delta) {
        pastTime += delta;
        pos.interpolate(servPos, .2f, Interpolation.fade);
        float apply = Interpolation.circle.apply(.3f);
        camera.position.add(apply * (pos.x - camera.position.x), apply * (pos.y - camera.position.y), 0);
        TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(pastTime, true);
        batch.draw(frame, pos.x - regionWidth, pos.y - regionHeight);
        font.draw(batch, "You", pos.x - 12, pos.y + youHeight);
        font.draw(batch, name, pos.x + nameWidth, pos.y + nameHeight);
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y);
    }
}

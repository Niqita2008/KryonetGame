package me.niqitadev.core.client_players;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import me.niqitadev.core.handlers.ResourceHandler;

import java.util.HashSet;

public final class Me {
    public final String name;
    private final OrthographicCamera camera;
    private final float regionWidth, regionHeight, youHeight, nameWidth, nameHeight;
    private float pastTime;
    private final BitmapFont font = new BitmapFont();
    private final Vector2 servPos = new Vector2(), pos = new Vector2();

    public Me(final String name, final OrthographicCamera camera) {
        this.name = name;
        this.camera = camera;
        font.setColor(.1f, .8f, .1f, 1);
        final TextureRegion frame = ResourceHandler.playerIdle.getKeyFrame(0);
        regionHeight = frame.getRegionHeight() / 2f;
        regionWidth = frame.getRegionWidth() / 2f;
        final GlyphLayout nameLayout = new GlyphLayout(font, name);
        youHeight = 12 + regionHeight + nameLayout.height;
        nameWidth = -nameLayout.width / 2f;
        nameHeight = nameLayout.height + regionHeight;
    }

    public void draw(final SpriteBatch batch, final float delta, final Texture grass, HashSet<OtherClientPlayer> players) {
        batch.begin();
        pastTime += delta;
        pos.interpolate(servPos, .2f, Interpolation.circle);
        camera.position.set(pos.x, pos.y, 0);
        final int height = grass.getHeight(), width = grass.getWidth();
        for (int x = -1000, y; x < 1000; x += width) for (y = -1000; y < 1000; y += height) batch.draw(grass, x, y);
        batch.draw(ResourceHandler.playerIdle.getKeyFrame(pastTime, true), pos.x - regionWidth, pos.y - regionHeight);
        font.draw(batch, "You", pos.x - 12, pos.y + youHeight);
        font.draw(batch, name, pos.x + nameWidth, pos.y + nameHeight);
        players.forEach(h -> h.draw(batch, delta));
        batch.end();
    }

    public void setServPos(final float x, final float y) {
        servPos.set(x, y);
    }
}

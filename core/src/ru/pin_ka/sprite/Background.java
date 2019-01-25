package ru.pin_ka.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class Background extends Sprite {

    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        pos.set(worldBounds.pos);
        setHeightProportion(worldBounds.getHeight());
    }
}

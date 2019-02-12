package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class NameGame extends Sprite {
    public NameGame(TextureAtlas atlas) {
        super(atlas.findRegion("title"));
        setHeightProportion(0.4f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop()-0.05f);
        setLeft(worldBounds.getLeft()+0.01f);
    }
}

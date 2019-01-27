package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pin_ka.base.Sprite;

public class NameGame extends Sprite {
    public NameGame(TextureAtlas atlas) {
        super(atlas.findRegion("title"));
        setHeightProportion(0.4f);
        pos.set(-0.1f,0.25f);
    }
}

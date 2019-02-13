package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("gameOver"));
        setHeightProportion(0.15f);
        setBottom(0.7f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setTop(worldBounds.getTop()-worldBounds.getHalfHeight()/2);
        setLeft(worldBounds.getLeft()+0.02f);
    }
}



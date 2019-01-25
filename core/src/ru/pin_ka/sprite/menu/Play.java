package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Play extends ScaledTouchUpButton {

    public Play(TextureAtlas atlas) {
        super(atlas.findRegion("play"));
        setHeightProportion(0.2f);
        pos.set(-0.27f,-0.39f);
    }

    @Override
    public void action() {

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int poiter) {
        return super.touchUp(touch, poiter);
    }
}

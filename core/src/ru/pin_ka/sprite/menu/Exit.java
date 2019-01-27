package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

public class Exit extends ScaledTouchUpButton {
    public Exit(TextureAtlas atlas) {
        super(atlas.findRegion("exit"));
        setHeightProportion(0.2f);
        pos.set(0.27f,-0.39f);
    }

    @Override
    public void action() {

    }


}

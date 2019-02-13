package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pin_ka.base.ScaledTouchUpButton;
import ru.pin_ka.math.Rect;

public class ButtonExit extends ScaledTouchUpButton {

    public ButtonExit(TextureAtlas atlas) {
        super(atlas.findRegion("exit"));
        setHeightProportion(0.2f);
    }

   @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom()+0.02f);
        setRight(worldBounds.getRight()-0.02f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }


}

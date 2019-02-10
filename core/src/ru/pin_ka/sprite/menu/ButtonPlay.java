package ru.pin_ka.sprite.menu;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;


import ru.pin_ka.base.ScaledTouchUpButton;
import ru.pin_ka.math.Rect;
import ru.pin_ka.screen.GameScreen;

public class ButtonPlay extends ScaledTouchUpButton {

    private Game game;

    public ButtonPlay(TextureAtlas atlas,Game game) {
        super(atlas.findRegion("play"));
        this.game=game;
        setHeightProportion(0.2f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom()+0.02f);
        setLeft(worldBounds.getLeft()+0.02f);
    }
}

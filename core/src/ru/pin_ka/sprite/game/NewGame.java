package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.ScaledTouchUpButton;
import ru.pin_ka.screen.GameScreen;

public class NewGame extends ScaledTouchUpButton {

    GameScreen gameScreen;

    public NewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("newGame"));
        setHeightProportion(0.10f);
        setLeft(0.06f);
        setBottom(-0.45f);
        this.gameScreen=gameScreen;
    }

    @Override
    public void action() {
        gameScreen.startNewGame();
    }
}

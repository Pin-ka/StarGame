package ru.pin_ka.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalPool extends SpritesPool <SweetGoal> {

    private TextureAtlas atlas;

    public SweetGoalPool(TextureAtlas atlas) {
        this.atlas=atlas;
    }

    @Override
    protected SweetGoal newObject() {
        return new SweetGoal(atlas);
    }

}

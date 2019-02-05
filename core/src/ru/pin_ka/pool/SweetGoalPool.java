package ru.pin_ka.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.math.Rect;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalPool extends SpritesPool <SweetGoal> {

    private TextureAtlas atlas;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public SweetGoalPool(TextureAtlas atlas, Rect worldBounds,ExplosionPool explosionPool) {
        this.explosionPool=explosionPool;
        this.atlas=atlas;
        this.worldBounds=worldBounds;
    }

    @Override
    protected SweetGoal newObject() {
        return new SweetGoal(atlas,worldBounds,explosionPool);
    }

}

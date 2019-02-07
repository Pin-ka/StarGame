package ru.pin_ka.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.math.Rect;
import ru.pin_ka.sprite.game.Ship;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalPool extends SpritesPool <SweetGoal> {

    private TextureAtlas atlas;
    private Rect worldBounds;
    private ExplosionPool explosionPool;
    private Ship ship;
    private Sound explosionSound;


    public SweetGoalPool(TextureAtlas atlas, Rect worldBounds,ExplosionPool explosionPool,Ship ship) {
        this.explosionPool=explosionPool;
        this.atlas=atlas;
        this.worldBounds=worldBounds;
        this.ship=ship;
        this.explosionSound=Gdx.audio.newSound(Gdx.files.internal("sounds/expCake.wav"));
    }

    @Override
    protected SweetGoal newObject() {
        return new SweetGoal(atlas,worldBounds,explosionPool,ship,explosionSound);
    }

    @Override
    public void dispose() {
        super.dispose();
        explosionSound.dispose();
    }
}

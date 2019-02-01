package ru.pin_ka.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.ExplosionCake;

public class ExplosionPool extends SpritesPool<ExplosionCake> {

    private TextureRegion region;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas) {
        this.region = atlas.findRegion("expCake");
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/expCake.wav"));
    }

    @Override
    protected ExplosionCake newObject() {
        return new ExplosionCake(region, 2, 7, 14, explosionSound);
    }

    public void dispose() {
        explosionSound.dispose();
        super.dispose();
    }
}

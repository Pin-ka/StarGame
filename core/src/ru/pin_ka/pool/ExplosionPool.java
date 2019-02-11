package ru.pin_ka.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    TextureAtlas atlas;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas=atlas;
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas);
    }

}

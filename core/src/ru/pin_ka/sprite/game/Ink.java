package ru.pin_ka.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.ExplosionPool;

public class Ink extends BaseShip {

    private Rect worldBounds;
    private Vector2 v=new Vector2();
    private Sound explosionSound;
    private int damage;
    private Ship ship;

    public Ink(TextureAtlas atlas, Rect worldBounds, ExplosionPool explosionPool, Ship ship, Sound explosionSound) {
        super(atlas.findRegion("ink"),explosionPool);
        this.v.set(v);
        this.worldBounds=worldBounds;
        this.explosionPool=explosionPool;
        this.ship=ship;
        this.explosionSound = explosionSound;
        setHeightProportion(0.15f);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        if(isOutside(worldBounds)){
            destroy();
        }
    }

    public void set(
            Vector2 v0,
            Rect worldBounds
    ){
        this.v.set(v0);
        this.worldBounds=worldBounds;
    }

    @Override
    public void destroy() {
        boom(Explosion.Type.INK);
        explosionSound.play();
        super.destroy();
    }
}

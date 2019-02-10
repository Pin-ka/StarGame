package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.ExplosionPool;

public abstract class BaseShip extends Sprite {

        protected Rect worldBounds;
        protected Vector2 v = new Vector2();
        protected float reloadInterval;
        protected float reloadTimer;
        protected int damage;
        protected int hp;
        protected float damageInterval=0.1f;
        protected float damageTimer=damageInterval;
        protected ExplosionPool explosionPool;

        public BaseShip(TextureRegion region, int rows, int cols, int frames,ExplosionPool explosionPool) {
            super(region, rows, cols, frames);
            this.explosionPool=explosionPool;
        }

    public BaseShip(TextureRegion region, ExplosionPool explosionPool) {
        super(region);
        this.explosionPool=explosionPool;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        damageTimer+=delta;
    }

    public void damage(int damage){
            hp-=damage;
            if (hp<=0){
                hp=0;
                destroy();
            }
    }

    public int getDamage() {
        return damage;
    }

    @Override
        public void resize(Rect worldBounds) {
            super.resize(worldBounds);
            this.worldBounds = worldBounds;
        }


    public void boom(Explosion.Type type){
            Explosion explosion=explosionPool.obtain();
            explosion.set(getHeight(),pos,type);
    }
}

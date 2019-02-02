package ru.pin_ka.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;

public class SweetGoal extends BaseShip {

    private Vector2 v0=new Vector2();

    public SweetGoal(Sound shootSound, BulletPool bulletPool) {
        super();
        this.shootSound=shootSound;
        this.v.set(v0);
        this.bulletPool=bulletPool;
        this.bulletV = new Vector2();
    }


    public void set(
        TextureRegion region,
        Vector2 v0,
        TextureRegion bulletRegion,
        float bulletHeidht,
        float bulletVy,
        int bulletDamage,
        float realoadInterval,
        float height,
        int hp,
        Rect worldBounds
    ){
        this.regions[0]=region;
        this.v0.set(v0);
        this.bulletRegion=bulletRegion;
        this.bulletHeight=bulletHeidht;
        this.bulletV.set(0,bulletVy);
        this.damage=bulletDamage;
        this.reloadInterval=realoadInterval;
        setHeightProportion(height);
        this.hp=hp;
        reloadTimer=realoadInterval;//чтобы сразу стрелял
        v.set(v0);
        this.worldBounds=worldBounds;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v,delta);
        if (isOutside(worldBounds)) {
            destroy();
        }

    }
}

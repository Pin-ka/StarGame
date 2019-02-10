package ru.pin_ka.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.ExplosionPool;

public class SweetGoal extends BaseShip {

    private enum State {DESCENT,FLIGHT};
    private Vector2 v0=new Vector2();
    private Rect worldBounds;
    private State state;
    private Vector2 descentV=new Vector2(0,-0.15f);
    private Vector2 vDamage=new Vector2(-0.5f,-0.1f);
    private Ship ship;
    private Sound explosionSound;

    public SweetGoal(TextureAtlas atlas,Rect worldBounds,ExplosionPool explosionPool,Ship ship,Sound explosionSound) {
        super(atlas.findRegion("cakies"),3,4,11,explosionPool);
        this.v.set(v0);
        this.worldBounds=worldBounds;
        this.explosionPool=explosionPool;
        this.ship=ship;
        this.explosionSound = explosionSound;
        setHeightProportion(0.15f);
    }

    public void set(
        int frame,
        Vector2 v0,
        int hp
    ){
        this.frame=frame;
        this.v0.set(v0);
        this.hp=hp;
        v.set(descentV);
        state=State.DESCENT;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v,delta);
        switch (state){
            case DESCENT:
                if(getTop()<=worldBounds.getTop()){
                    v.set(v0);
                    state=State.FLIGHT;
                }
                break;
            case FLIGHT:
                if (damageTimer>=damageInterval){
                    v.set(v0);
                }
                if (getBottom()<=worldBounds.getBottom()||getRight()<=worldBounds.getLeft()) {
                    destroy();
                    ship.damage(1);
                }
                break;
        }
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        v.set(vDamage);
        if(getLeft()<=worldBounds.getLeft()){
           vDamage.set(0.5f,-0.1f);
        }
        if(getRight()>=worldBounds.getRight()){
            vDamage.set(-0.5f,-0.1f);
        }
        damageTimer=0f;
    }

    public boolean isBulletCollision(Rect bullet){
        return   !(bullet.getRight()<getLeft()
                ||bullet.getLeft()>getRight()
                ||bullet.getBottom()>getTop()
                ||bullet.getTop()<pos.y
                );
    }

    @Override
    public void destroy() {
        boom(Explosion.Type.CAKE);
        explosionSound.play();
        super.destroy();
    }
}

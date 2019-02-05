package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.ExplosionPool;

public class SweetGoal extends BaseShip {

    private Vector2 v0=new Vector2();
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public SweetGoal(TextureAtlas atlas,Rect worldBounds,ExplosionPool explosionPool) {
        super(atlas.findRegion("cakies"),3,4,11);
        this.v.set(v0);
        this.worldBounds=worldBounds;
        this.explosionPool=explosionPool;
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
        v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        this.pos.mulAdd(v,delta);
        if (getBottom()<=worldBounds.getBottom()) {
            destroy();
            boom();
        }
    }

    public void boom(){
        ExplosionCake explosionCake=explosionPool.obtain();
        explosionCake.set(getHeight(),pos);
    }
}

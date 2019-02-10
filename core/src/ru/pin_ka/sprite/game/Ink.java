package ru.pin_ka.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.ExplosionPool;

public class Ink extends BaseShip {

    public enum State {NO_COLLISION, COLLISION};

    private Rect worldBounds;
    private Vector2 v=new Vector2();
    private Sound explosionSound;
    private Ship ship;
    private State state;

    public Ink(TextureAtlas atlas, Rect worldBounds, ExplosionPool explosionPool, Ship ship, Sound explosionSound) {
        super(atlas.findRegion("ink"),explosionPool);
        this.v.set(v);
        this.worldBounds=worldBounds;
        this.explosionPool=explosionPool;
        this.ship=ship;
        this.explosionSound = explosionSound;
        this.damage=1;
        setHeightProportion(0.15f);
        state = State.NO_COLLISION;
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

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public void destroy() {
        switch (state){
            case COLLISION:
                boom(Explosion.Type.INK);
                explosionSound.play();
                state=State.NO_COLLISION;
                break;
            case NO_COLLISION:
                break;
        }
        super.destroy();
    }
}

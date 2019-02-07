package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 v=new Vector2();
    private int damage;
    private Object ovner;

    public Bullet() {
        regions=new TextureRegion[1];
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        if(isOutside(worldBounds)){
            destroy();
        }
    }

    public void set(
            Object ovner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ){
        this.ovner=ovner;
        this.regions[0]=region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds=worldBounds;
        this.damage=damage;
    }

    public Object getOvner() {
        return ovner;
    }
}

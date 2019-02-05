package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;

public class SweetGoal extends BaseShip {

    private Vector2 v0=new Vector2();

    public SweetGoal(TextureAtlas atlas) {
        super(atlas.findRegion("cakies"),3,4,11);
        this.v.set(v0);
        setHeightProportion(0.15f);
    }

    public void set(
        int frame,
        Vector2 v0,
        int hp,
        Rect worldBounds
    ){
        this.frame=frame;
        this.v0.set(v0);
        this.hp=hp;
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

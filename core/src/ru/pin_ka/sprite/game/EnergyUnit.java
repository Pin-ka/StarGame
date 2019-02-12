package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class EnergyUnit extends Sprite {

    private Vector2 v=new Vector2();
    private Rect worldBounds;

    public EnergyUnit(TextureAtlas atlas) {
        super(atlas.findRegion("candies"),2,3,6);
        frame=1;
        setHeightProportion(0.08f);
        v.set(0,0);
    }


    public void resize(Rect worldBounds,int i) {
        this.worldBounds=worldBounds;
        float posX = worldBounds.getLeft()+0.05f+worldBounds.getHalfHeight() / 0.37f*getHalfHeight() * (i);
        float posY=worldBounds.getBottom()+this.getHalfHeight();
        pos.set(posX,posY);
    }

}

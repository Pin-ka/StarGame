package ru.pin_ka.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import java.util.Random;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class CandyBg extends Sprite {

    private Vector2 v=new Vector2();
    private Rect worldBounds;
    private Random random=new Random();

    public CandyBg(TextureAtlas atlas) {
        super(atlas.findRegion("candies"),2,3,6);
        frame=random.nextInt(6);
        setHeightProportion(0.05f);
        v.set((random.nextFloat()*(0.005f-(-0.005f))+(-0.005f)),(random.nextFloat()*(-0.1f-(-0.3f))+(-0.3f)));
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v,delta);
        chekAndHandleBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds=worldBounds;
        float posX=random.nextFloat()*(worldBounds.getRight()-worldBounds.getLeft())+worldBounds.getLeft();
        float posY=random.nextFloat()*(worldBounds.getTop()-worldBounds.getBottom())+worldBounds.getBottom();
        pos.set(posX,posY);
    }

    private void chekAndHandleBounds(){
        if (getRight()<worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if (getLeft()>worldBounds.getRight()) setRight(worldBounds.getLeft());
        if (getTop()<worldBounds.getBottom()) setBottom(worldBounds.getTop());
        if (getBottom()>worldBounds.getTop()) setTop(worldBounds.getBottom());
    }
}

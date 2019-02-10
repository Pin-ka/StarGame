package ru.pin_ka.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.InkPool;
import ru.pin_ka.sprite.game.Ink;

public class InkEmitter {

    private int  damage=1;
    private Random random=new Random();
    private Vector2 goalSmallV=new Vector2(0,-0.2f);
    private Rect worldBounds;
    private float generateInterval=8f;
    private float generateTimer;

    public InkEmitter(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    public void generate (float delta,InkPool inkPool){
        generateTimer+=delta;
        if (generateTimer>=generateInterval){
            generateTimer=0f;
            Ink ink= inkPool.obtain();
            ink.set(goalSmallV,worldBounds);
            ink.pos.x = random.nextFloat()*((worldBounds.getRight() - ink.getHalfWidth()) - (worldBounds.getLeft() + ink.getHalfWidth())) + (worldBounds.getLeft() + ink.getHalfWidth());
            ink.setBottom(worldBounds.getTop());
        }
    }

}

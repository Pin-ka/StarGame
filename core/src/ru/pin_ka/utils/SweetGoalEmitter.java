package ru.pin_ka.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.SweetGoalPool;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalEmitter {

    private static final float  GOAL_SMALL_HEIGHT=0.1f;
    private static final float  GOAL_SMALL_BULLET_HEIGHT=0.01f;
    private static final float  GOAL_SMALL_BULLET_Y=-0.3f;
    private static final int  GOAL_SMALL_DAMAGE=1;
    private static final float  GOAL_SMALL_RELOAD_INTERVAL=3f;
    private static final int  GOAL_SMALL_HP=1;
    private TextureRegion[] goalSmallRegion;
    private TextureRegion oneGoal;
    private TextureRegion bulletRegion;

    private Random random=new Random();

    private Vector2 goalSmallV=new Vector2(0,-0.2f);

    private SweetGoalPool sweetGoalPool;

    private float generateInterval=4f;
    private float generateTimer;

    private Rect worldBounds;

    public SweetGoalEmitter(SweetGoalPool sweetGoalPool, TextureAtlas atlas,Rect worldBounds) {

        this.sweetGoalPool = sweetGoalPool;
        TextureRegion textureRegion=atlas.findRegion("cakies");
        this.goalSmallRegion=Regions.split(textureRegion,3,4,11);
        this.bulletRegion=atlas.findRegion("ink");
        this.worldBounds=worldBounds;
        this.oneGoal=goalSmallRegion[3];

    }

    public void generate (float delta){
        generateTimer+=delta;
        if(generateTimer>=generateInterval){
            generateTimer=0f;
            SweetGoal sweetGoal=sweetGoalPool.obtain();
            sweetGoal.set(
                    oneGoal,
                    goalSmallV,
                    bulletRegion,
                    GOAL_SMALL_BULLET_HEIGHT,
                    GOAL_SMALL_BULLET_Y,
                    GOAL_SMALL_DAMAGE,
                    GOAL_SMALL_RELOAD_INTERVAL,
                    GOAL_SMALL_HEIGHT,
                    GOAL_SMALL_HP,
                    worldBounds
            );

            sweetGoal.pos.x = random.nextFloat()*((worldBounds.getRight() - sweetGoal.getHalfWidth() - worldBounds.getLeft() + sweetGoal.getHalfWidth()) + worldBounds.getLeft() + sweetGoal.getHalfWidth());
            sweetGoal.setBottom(worldBounds.getTop());
        }
    }
}

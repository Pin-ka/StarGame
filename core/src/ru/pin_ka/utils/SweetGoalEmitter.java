package ru.pin_ka.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.SweetGoalPool;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalEmitter {

    private static final int  GOAL_HP=1;
    private Random random=new Random();
    private Vector2 goalSmallV=new Vector2(0,-0.1f);
    private Rect worldBounds;
    private ArrayList <Integer> freeFrames=new ArrayList<Integer>();
    private int currentFrame;
    private boolean isChange=true;

    public SweetGoalEmitter(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    public void generate (SweetGoalPool sweetGoalPool){
        if(sweetGoalPool.getActiveObjects().size()==0){
            SweetGoal sweetGoal=sweetGoalPool.obtain();
            sweetGoal.set(
                    choise(),
                    goalSmallV,
                    GOAL_HP);

            sweetGoal.pos.x = random.nextFloat()*((worldBounds.getRight() - sweetGoal.getHalfWidth()) - (worldBounds.getLeft() + sweetGoal.getHalfWidth())) + (worldBounds.getLeft() + sweetGoal.getHalfWidth());
            sweetGoal.setBottom(worldBounds.getTop());
            isChange=true;
        }else {
            isChange=false;
        }
    }

    private int choise(){
        int currentFrameIndex;
        if (freeFrames.size()==0){
            for(int i=0;i<11;i++){
                freeFrames.add(i);
            }
        }
        currentFrameIndex=random.nextInt(freeFrames.size());
        currentFrame=freeFrames.get(currentFrameIndex);
        freeFrames.remove(currentFrameIndex);
        return currentFrame;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public boolean isChange() {
        return isChange;
    }
}

package ru.pin_ka.utils;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.AnswersPool;
import ru.pin_ka.sprite.game.Answers;

public class AnswersBuilding {

    private Rect worldBounds;
    private static final int NUMBERS_OF_ANSWERS=4;
    private ArrayList frames =new ArrayList(NUMBERS_OF_ANSWERS);
    private ArrayList <Answers> answers=new ArrayList<Answers>(NUMBERS_OF_ANSWERS);
    private Random random=new Random();
    private int currentFrame;
    private boolean isTrueAnswer;

    public AnswersBuilding(Rect worldBounds) {
        this.worldBounds=worldBounds;
    }

    public void buildAnswer(int frame, AnswersPool answersPool,boolean isChange){
        this.currentFrame=frame;
        if (isChange) {
            isTrueAnswer=false;
            if (answers.size()!=0){
                for (int i=0;i<NUMBERS_OF_ANSWERS;i++){
                    answers.get(i).destroy();
                }
            }
            answers.clear();
            buildFrames(frame);
            for(int i=0;i<NUMBERS_OF_ANSWERS;i++) {
                Answers answer = answersPool.obtain();
                answer.set((Integer) frames.get(i), worldBounds);
                answers.add(answer);
                if (i%2==0){
                    answer.pos.x = worldBounds.getLeft() + answer.getHalfWidth();
                    answer.pos.y = worldBounds.getBottom() + answer.getHalfHeight()+answer.getHeight()*i;
                }else {
                    answer.pos.x = worldBounds.getRight() - answer.getHalfWidth();
                    answer.pos.y = worldBounds.getBottom() - answer.getHalfHeight()+answer.getHeight()*i;
                }
            }
            frames.clear();
        }
    }

    private void buildFrames (int frame){
        int nextFrame=-1;
        int trueAnswerNumber=random.nextInt(4);
        int counter=0;
        while (frames.size()<NUMBERS_OF_ANSWERS){
            nextFrame=random.nextInt(11);
            if (counter==trueAnswerNumber){
                frames.add(frame);
                counter++;
            }else if (!frames.contains(nextFrame)&&(nextFrame!=frame)){
                frames.add(nextFrame);
                counter++;
            }
        }
    }

    public void touchDown(Vector2 touch, int pointer) {
            for (int i = 0; i < NUMBERS_OF_ANSWERS; i++) {
                answers.get(i).touchDown(touch, pointer);
            }
    }

    public void touchUp(Vector2 touch, int pointer) {
            for (int i = 0; i < NUMBERS_OF_ANSWERS; i++) {
                answers.get(i).touchUp(touch, pointer, currentFrame);
                if(answers.get(i).getPressedFrame()==currentFrame){
                    isTrueAnswer=true;
                }
            }
    }

    public void setBlocked(boolean isBlocked){
        for (int i = 0; i < NUMBERS_OF_ANSWERS; i++) {
            answers.get(i).setBlocked(isBlocked);
        }
    }

    public boolean isTrueAnsver() {
        return isTrueAnswer;
    }

    public void dispose() {
        for (int i = 0; i < NUMBERS_OF_ANSWERS; i++) {
            answers.get(i).dispose();
        }
    }
}

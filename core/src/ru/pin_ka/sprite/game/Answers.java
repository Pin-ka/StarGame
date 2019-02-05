package ru.pin_ka.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;

public class Answers extends Sprite {

   private Rect worldBounds;
    private static final float PRESS_SCALE=0.5f;
    private int pointer;
    private boolean isPressed;
    private Sound trueAnswer;
    private Sound falseAnswer;

    public Answers(TextureAtlas atlas) {
        super(atlas.findRegion("texts"),3,4,11);
        setHeightProportion(0.08f);
        this.trueAnswer = Gdx.audio.newSound(Gdx.files.internal("sounds/trueAnswer.wav"));
        this.falseAnswer = Gdx.audio.newSound(Gdx.files.internal("sounds/falseAnswer.wav"));
    }

    public void set(int frame,Rect worldBounds){
        this.frame=frame;
        this.worldBounds=worldBounds;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(isPressed||!isMe(touch)){
            return false;
        }
        this.pointer=pointer;
        this.scale=PRESS_SCALE;
        this.isPressed=true;
        return super.touchDown(touch, pointer);
    }

    public boolean touchUp(Vector2 touch, int poiter,int currentFrame) {
        if(this.pointer!=pointer||!isPressed){
            return false;
        }
        if (isMe(touch)){
            action(currentFrame);
        }
        this.isPressed=false;
        scale=1f;
        return super.touchUp(touch, poiter);
    }

    public void action(int currentFrame){

        if (currentFrame!=frame){
            falseAnswer.play();
        }else {
            trueAnswer.play();
        }
    }


    public void dispose() {
        trueAnswer.dispose();
        falseAnswer.dispose();
    }
}

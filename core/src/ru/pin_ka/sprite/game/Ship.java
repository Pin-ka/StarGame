package ru.pin_ka.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;

public class Ship extends Sprite {

    static final int INVALID_POINTER=-1;

    private Vector2 v= new Vector2();
    private Vector2 v0=new Vector2(0.2f,0);
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private  Rect worldBounds;
    private int rightPoiner;
    private int leftPointer;
    private float reloadInterval;
    private float reloadTimer;
    private Sound shootSound;

    public Ship(TextureAtlas atlas,BulletPool bulletPool) {
        super(atlas.findRegion("ship"),1,2,2);
        this.bulletRegion=atlas.findRegion("bullet");
        setHeightProportion(0.15f);
        this.bulletPool=bulletPool;
        this.reloadInterval=0.2f;
        this.shootSound=Gdx.audio.newSound(Gdx.files.internal("sounds/shlep.wav"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds=worldBounds;
        setBottom(worldBounds.getBottom()+0.01f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v,delta);
        reloadTimer+=delta;
        if(reloadTimer>=reloadInterval){
            reloadTimer=0f;
            shoot();
        }
        if (getLeft()<worldBounds.getLeft()){
            setLeft(worldBounds.getLeft());
            stop();
        }
        if (getRight()>worldBounds.getRight()){
            setRight(worldBounds.getRight());
            stop();
        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case  Input.Keys.LEFT:
                    isPressedLeft=true;
                    moveLeft();
                    break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                    isPressedRight=true;
                    moveRight();
                    break;
        }

        return false;
    }

    public boolean keyUp(int keycode) {

        switch (keycode){
            case Input.Keys.A:
            case  Input.Keys.LEFT:
                isPressedLeft=false;
                if(isPressedRight){
                    moveRight();
                }else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight=false;
                if(isPressedLeft){
                    moveLeft();
                }else {
                    stop();
                }
                break;
        }

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x<worldBounds.pos.x){
            if(leftPointer!=INVALID_POINTER) return false;
            leftPointer=pointer;
            moveLeft();
        }else {
            if (rightPoiner!=INVALID_POINTER) return false;
            rightPoiner=pointer;
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (pointer==leftPointer){
            leftPointer=INVALID_POINTER;
            if (rightPoiner!=INVALID_POINTER){
                moveRight();
            }else {
                stop();
            }
        }
        if (pointer==rightPoiner){
            rightPoiner=INVALID_POINTER;
            if(leftPointer!=INVALID_POINTER){
                moveLeft();
            }else {
                stop();
            }
        }
        return super.touchUp(touch, pointer);
    }


    private void moveRight(){
        v.set(v0);
    }

    private void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

    private void shoot(){
        shootSound.play();
        Bullet bullet=bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                new Vector2(0,0.5f),
                0.02f,
                worldBounds,
                1);
    }

    public void dispose(){
        shootSound.dispose();
    }

}

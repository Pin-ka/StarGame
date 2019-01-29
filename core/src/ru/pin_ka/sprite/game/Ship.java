package ru.pin_ka.sprite.game;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;

public class Ship extends Sprite {

    private Vector2 v= new Vector2();
    private Vector2 v0=new Vector2(0.2f,0);
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private  Rect worldBounds;

    public Ship(TextureAtlas atlas,BulletPool bulletPool) {
        super(atlas.findRegion("ship"),1,2,2);
        this.bulletRegion=atlas.findRegion("bullet");
        setHeightProportion(0.15f);
        this.bulletPool=bulletPool;
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
        chekBounds();
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
            case Input.Keys.UP:
                shoot();
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

    public boolean touchDown(Vector2 touch, int pointer) {
        if (touch.x<0){
            isPressedLeft=true;
            moveLeft();
        }
        if (touch.x>0){
            isPressedRight=true;
            moveRight();
        }
        return false;
    }


    public boolean touchUp(Vector2 touch, int pointer) {
        if (touch.x<0){
            isPressedLeft=false;
            if(isPressedRight){
                moveRight();
            }else {
                stop();
            }
        }
        if (touch.x>0){
            isPressedRight=false;
            if(isPressedLeft){
                moveLeft();
            }else {
                stop();
            }
        }

        return false;
    }

    private void chekBounds(){
        if (getLeft()<worldBounds.getLeft()) stop();
        if (getRight()>worldBounds.getRight()) stop();
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
        Bullet bullet=bulletPool.obtain();
        bullet.set(this,
                bulletRegion,
                pos,
                new Vector2(0,0.5f),
                0.02f,
                worldBounds,
                1);
    }

}

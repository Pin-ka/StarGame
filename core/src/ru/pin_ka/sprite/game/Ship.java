package ru.pin_ka.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;
import ru.pin_ka.pool.ExplosionPool;

public class Ship extends BaseShip {

    private static final int INVALID_POINTER=-1;

    private Vector2 v0=new Vector2(0.2f,0);
    private boolean isPressedLeft;
    private boolean isPressedRight;
    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;
    private boolean isBlocked=true;
    private ExplosionShip explosionShip;


    public Ship(TextureAtlas atlas, BulletPool bulletPool, ExplosionShip explosionShip) {
        super(atlas.findRegion("ship"),1,2,2);
        this.bulletRegion=atlas.findRegion("bullet");
        this.bulletPool = bulletPool;
        this.reloadInterval = 0.5f;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/shlep.wav"));
        this.explosionShip=explosionShip;
        setHeightProportion(0.15f);
        this.bulletV = new Vector2(0, 0.5f);
        this.bulletHeight = 0.01f;
        this.damage = 1;
        this.hp = 10;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom()+0.01f);
    }


    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(v, delta);
        if (damageTimer>=damageInterval){
            frame=0;
        }
        reloadTimer += delta;
        if (reloadTimer >= reloadInterval && !isBlocked) {
            reloadTimer = 0f;
            shoot();
        }
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
           stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void damage(int damage) {
        super.damage(damage);
        frame=1;
        damageTimer=0f;
    }

    @Override
    public void destroy() {
        super.destroy();
        boom(explosionShip);
    }

    public boolean keyDown(int keycode) {
        switch (keycode){
            case Input.Keys.A:
            case  Input.Keys.LEFT:
                    isPressedLeft=true;
                    if (!isBlocked) moveLeft();
                    break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                    isPressedRight=true;
                if (!isBlocked) moveRight();
                    break;
        }

        return false;
    }

    public boolean keyUp(int keycode) {

        switch (keycode){
            case Input.Keys.A:
            case  Input.Keys.LEFT:
                isPressedLeft=false;
                if(isPressedRight && !isBlocked){
                    moveRight();
                }else {
                    stop();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                isPressedRight=false;
                if(isPressedLeft && !isBlocked){
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
        if(touch.x<worldBounds.pos.x && !isBlocked){
            if(leftPointer!=INVALID_POINTER) return false;
            leftPointer=pointer;
            moveLeft();
        }else if (!isBlocked){
            if (rightPointer!=INVALID_POINTER) return false;
            rightPointer=pointer;
            moveRight();
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (isBlocked){
            damage(1);
        }
        if (pointer==leftPointer){
            leftPointer=INVALID_POINTER;
            if (rightPointer!=INVALID_POINTER && !isBlocked){
                moveRight();
            }else {
                stop();
            }
        }
        if (pointer==rightPointer){
            rightPointer=INVALID_POINTER;
            if(leftPointer!=INVALID_POINTER && !isBlocked){
                moveLeft();
            }else {
                stop();
            }
        }
        return super.touchUp(touch, pointer);
    }

    public void boom(ExplosionShip explosionShip){
        explosionShip.set(getHeight(),pos);
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

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }
}

package ru.pin_ka.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Base2DScreen;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;
import ru.pin_ka.sprite.Background;
import ru.pin_ka.sprite.CandyBg;
import ru.pin_ka.sprite.game.Ship;

public class GameScreen extends Base2DScreen {
    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private CandyBg[] candyBg;
    private Ship ship;

    private BulletPool bulletPool;

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.jpg");
        background=new Background(new TextureRegion(bg));
        atlas=new TextureAtlas("textures/mainAtlas.tpack");
        candyBg=new CandyBg[25];
        for (int i=0;i<candyBg.length;i++){
            candyBg [i]=new CandyBg(atlas);
        }
        bulletPool=new BulletPool();
        ship=new Ship(atlas,bulletPool);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw();
    }

    public void update(float delta){
        for (int i=0;i<candyBg.length;i++){
            candyBg[i].update(delta);
        }
        ship.update(delta);
        bulletPool.updateActiveSprites(delta);
    }

    public void deleteAllDestroyed(){
        bulletPool.freeAllDestruyedActiveSprites();
    }

    public void draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i=0;i<candyBg.length;i++){
            candyBg[i].draw(batch);
        }
        ship.draw(batch);
        bulletPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        for (int i=0;i<candyBg.length;i++){
            candyBg[i].resize(worldBounds);
        }
        ship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        bulletPool.dispose();
        super.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        ship.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        ship.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        ship.touchDown(touch,pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        ship.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }
}

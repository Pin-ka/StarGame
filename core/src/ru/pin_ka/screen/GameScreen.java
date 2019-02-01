package ru.pin_ka.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Base2DScreen;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.BulletPool;
import ru.pin_ka.pool.ExplosionPool;
import ru.pin_ka.pool.SweetGoalPool;
import ru.pin_ka.sprite.Background;
import ru.pin_ka.sprite.CandyBg;
import ru.pin_ka.sprite.game.ExplosionCake;
import ru.pin_ka.sprite.game.Ship;
import ru.pin_ka.utils.SweetGoalEmitter;

public class GameScreen extends Base2DScreen {


        private TextureAtlas atlas;
        private Texture bg;
        private Background background;
        private CandyBg candyBg[];
        private Ship ship;

        private BulletPool bulletPool;
        private ExplosionPool explosionPool;

        private SweetGoalPool sweetGoalPool;

        private SweetGoalEmitter sweetGoalEmitter;


        private Music music;

        @Override
        public void show() {
            super.show();
            music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
            music.setLooping(true);
            music.setVolume(0.8f);
            music.play();
            bg = new Texture("textures/bg.jpg");
            background = new Background(new TextureRegion(bg));
            atlas = new TextureAtlas("textures/mainAtlas.tpack");
            candyBg = new CandyBg[25];
            for (int i = 0; i < candyBg.length; i++) {
                candyBg[i] = new CandyBg(atlas);
            }
            bulletPool = new BulletPool();
            explosionPool = new ExplosionPool(atlas);
            sweetGoalPool=new SweetGoalPool(bulletPool);
            ship = new Ship(atlas, bulletPool);
            sweetGoalEmitter=new SweetGoalEmitter(sweetGoalPool,atlas,worldBounds);

        }

        @Override
        public void render(float delta) {
            super.render(delta);
            update(delta);
            deleteAllDestroyed();
            draw();
        }

        public void update(float delta) {
            for (int i = 0; i < candyBg.length; i++) {
                candyBg[i].update(delta);
            }
            ship.update(delta);
            bulletPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            sweetGoalPool.updateActiveSprites(delta);
            sweetGoalEmitter.generate(delta);

        }

        public void deleteAllDestroyed() {
            bulletPool.freeAllDestroyedActiveSprites();
            explosionPool.freeAllDestroyedActiveSprites();
            sweetGoalPool.freeAllDestroyedActiveSprites();

        }

        public void draw() {
            Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            background.draw(batch);
            for (int i = 0; i < candyBg.length; i++) {
                candyBg[i].draw(batch);
            }
            ship.draw(batch);
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
            sweetGoalPool.drawActiveSprites(batch);
            batch.end();
        }

        @Override
        public void resize(Rect worldBounds) {
            super.resize(worldBounds);
            background.resize(worldBounds);
            for (int i = 0; i < candyBg.length; i++) {
                candyBg[i].resize(worldBounds);
            }
            ship.resize(worldBounds);
        }

        @Override
        public void dispose() {
            bg.dispose();
            atlas.dispose();
            bulletPool.dispose();
            explosionPool.dispose();
            sweetGoalPool.dispose();
            ship.dispose();
            music.dispose();
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
            ExplosionCake explosion = explosionPool.obtain();
            explosion.set(0.15f, touch);
            ship.touchDown(touch, pointer);
            return super.touchDown(touch, pointer);
        }

        @Override
        public boolean touchUp(Vector2 touch, int pointer) {
            ship.touchUp(touch, pointer);
            return super.touchUp(touch, pointer);
        }
    }

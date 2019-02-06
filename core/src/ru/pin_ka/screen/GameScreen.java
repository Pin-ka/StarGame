package ru.pin_ka.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.pin_ka.base.Base2DScreen;
import ru.pin_ka.math.Rect;
import ru.pin_ka.pool.AnswersPool;
import ru.pin_ka.pool.BulletPool;
import ru.pin_ka.pool.ExplosionPool;
import ru.pin_ka.pool.SweetGoalPool;
import ru.pin_ka.sprite.Background;
import ru.pin_ka.sprite.CandyBg;
import ru.pin_ka.sprite.game.Bullet;
import ru.pin_ka.sprite.game.ExplosionCake;
import ru.pin_ka.sprite.game.ExplosionShip;
import ru.pin_ka.sprite.game.GameOver;
import ru.pin_ka.sprite.game.Ship;
import ru.pin_ka.sprite.game.SweetGoal;
import ru.pin_ka.utils.AnswersBuilding;
import ru.pin_ka.utils.SweetGoalEmitter;

public class GameScreen extends Base2DScreen {


        private TextureAtlas atlas;
        private Texture bg;
        private Background background;
        private CandyBg candyBg[];
        private Ship ship;
        private BulletPool bulletPool;
        private ExplosionPool explosionPool;
        private ExplosionShip explosionShip;
        private SweetGoalPool sweetGoalPool;
        private SweetGoalEmitter sweetGoalEmitter;
        private AnswersPool answersPool;
        private AnswersBuilding answers;
        private Music music;
        private GameOver gameOver;

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
            explosionShip=new ExplosionShip(atlas);
            ship = new Ship(atlas, bulletPool,explosionShip);
            sweetGoalPool=new SweetGoalPool(atlas,worldBounds,explosionPool,ship);
            sweetGoalEmitter=new SweetGoalEmitter(worldBounds);
            answersPool=new AnswersPool(atlas);
            answers=new AnswersBuilding(worldBounds);
            gameOver=new GameOver(atlas);
        }

        @Override
        public void render(float delta) {
            super.render(delta);
            update(delta);
            checkCollisions();
            deleteAllDestroyed();
            draw();
        }

        private void update(float delta) {
            for (CandyBg aCandyBg : candyBg) {
                aCandyBg.update(delta);
            }
            if (!ship.isDestroyed()){
                ship.update(delta);
                bulletPool.updateActiveSprites(delta);
                explosionPool.updateActiveSprites(delta);
                sweetGoalPool.updateActiveSprites(delta);
                sweetGoalEmitter.generate(sweetGoalPool);
                answers.buildAnswer(sweetGoalEmitter.getCurrentFrame(),answersPool,sweetGoalEmitter.isChange());
                if(sweetGoalEmitter.isChange()) {
                    ship.setBlocked(true);
                    answers.setBlocked(false);
                }
                answersPool.updateActiveSprites(delta);
            }
                explosionShip.update(delta);


        }

        private void checkCollisions(){
            List <SweetGoal>sweetGoalList=sweetGoalPool.getActiveObjects();
            for (SweetGoal sweetGoal:sweetGoalList){
                if (sweetGoal.isDestroyed()){
                    continue;
                }
                float minDist=sweetGoal.getHalfHeight()+ship.getHalfWidth();
                if (sweetGoal.pos.dst2(ship.pos)<minDist*minDist){
                    sweetGoal.destroy();
                    ship.damage(sweetGoal.getDamage());
                    return;
                }
            }

            List <Bullet>bulletList=bulletPool.getActiveObjects();
            for (SweetGoal sweetGoal:sweetGoalList){
                if (sweetGoal.isDestroyed()){
                    continue;
                }
               for (Bullet bullet:bulletList){
                    if (bullet.getOvner()!=ship || bullet.isDestroyed()){
                        continue;
                    }
                    if (sweetGoal.isBulletCollision(bullet)){
                        sweetGoal.damage(ship.getDamage());
                        bullet.destroy();
                    }
               }
            }
        }

        private void deleteAllDestroyed() {
            bulletPool.freeAllDestroyedActiveSprites();
            explosionPool.freeAllDestroyedActiveSprites();
            sweetGoalPool.freeAllDestroyedActiveSprites();
            answersPool.freeAllDestroyedActiveSprites();
        }

        private void draw() {
            Gdx.gl.glClearColor(0.5f, 0.2f, 0.3f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            background.draw(batch);
            for (CandyBg aCandyBg : candyBg) {
                aCandyBg.draw(batch);
            }

            if (!ship.isDestroyed()){
                answersPool.drawActiveSprites(batch);
                bulletPool.drawActiveSprites(batch);
                explosionPool.drawActiveSprites(batch);
                sweetGoalPool.drawActiveSprites(batch);
                ship.draw(batch);
            }else {
                gameOver.draw(batch);
            }
            explosionShip.draw(batch);
            batch.end();
        }

        @Override
        public void resize(Rect worldBounds) {
            super.resize(worldBounds);
            background.resize(worldBounds);
            for (CandyBg aCandyBg : candyBg) {
                aCandyBg.resize(worldBounds);
            }
            ship.resize(worldBounds);
            gameOver.resize(worldBounds);
        }

        @Override
        public void dispose() {
            bg.dispose();
            atlas.dispose();
            bulletPool.dispose();
            explosionPool.dispose();
            ship.dispose();
            music.dispose();
            answersPool.dispose();
            answers.dispose();
            super.dispose();
        }

        @Override
        public boolean keyDown(int keycode) {
                if (!ship.isDestroyed())ship.keyDown(keycode);
            return super.keyDown(keycode);
        }

        @Override
        public boolean keyUp(int keycode) {
            if (!ship.isDestroyed())ship.keyUp(keycode);
            return super.keyUp(keycode);
        }

        @Override
        public boolean touchDown(Vector2 touch, int pointer) { ;
            if (!ship.isDestroyed())ship.touchDown(touch, pointer);
            answers.touchDown(touch, pointer);
            return super.touchDown(touch, pointer);
        }

        @Override
        public boolean touchUp(Vector2 touch, int pointer) {
            if (!ship.isDestroyed())ship.touchUp(touch, pointer);
            answers.touchUp(touch,pointer);
            if (answers.isTrueAnsver()){
                ship.setBlocked(false);
                answers.setBlocked(true);
            }
            return super.touchUp(touch, pointer);
        }
    }

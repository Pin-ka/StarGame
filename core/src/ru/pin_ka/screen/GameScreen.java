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
import ru.pin_ka.pool.InkPool;
import ru.pin_ka.pool.SweetGoalPool;
import ru.pin_ka.sprite.Background;
import ru.pin_ka.sprite.CandyBg;
import ru.pin_ka.sprite.game.Bullet;
import ru.pin_ka.sprite.game.GameOver;
import ru.pin_ka.sprite.game.Ink;
import ru.pin_ka.sprite.game.NewGame;
import ru.pin_ka.sprite.game.Ship;
import ru.pin_ka.sprite.game.SweetGoal;
import ru.pin_ka.sprite.menu.NameGame;
import ru.pin_ka.utils.AnswersBuilding;
import ru.pin_ka.utils.InkEmitter;
import ru.pin_ka.utils.SweetGoalEmitter;
import static ru.pin_ka.sprite.game.Ink.State.COLLISION;

public class GameScreen extends Base2DScreen {

        private enum State {PLAYING,GAME_OVER};

        private TextureAtlas atlas;
        private Texture bg;
        private Background background;
        private CandyBg candyBg[];
        private Ship ship;
        private BulletPool bulletPool;
        private ExplosionPool explosionPool;
        private SweetGoalPool sweetGoalPool;
        private InkPool inkPool;
        private InkEmitter inkEmitter;
        private SweetGoalEmitter sweetGoalEmitter;
        private AnswersPool answersPool;
        private AnswersBuilding answers;
        private Music music;
        private GameOver gameOver;
        private NewGame newGame;
        private State state;

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
            ship = new Ship(atlas, bulletPool,explosionPool,worldBounds);
            sweetGoalPool=new SweetGoalPool(atlas,worldBounds,explosionPool,ship);
            inkPool=new InkPool(atlas,worldBounds,explosionPool,ship);
            sweetGoalEmitter=new SweetGoalEmitter(worldBounds);
            inkEmitter=new InkEmitter(worldBounds);
            answersPool=new AnswersPool(atlas);
            answers=new AnswersBuilding(worldBounds);
            gameOver=new GameOver(atlas);
            newGame=new NewGame(atlas,this);
            startNewGame();
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
            explosionPool.updateActiveSprites(delta);
            switch (state){
                case PLAYING:
                    ship.update(delta);
                    bulletPool.updateActiveSprites(delta);
                    sweetGoalPool.updateActiveSprites(delta);
                    inkPool.updateActiveSprites(delta);
                    sweetGoalEmitter.generate(sweetGoalPool);
                    inkEmitter.generate(delta,inkPool);
                    answers.buildAnswer(sweetGoalEmitter.getCurrentFrame(),answersPool,sweetGoalEmitter.isChange());
                    if(sweetGoalEmitter.isChange()) {
                        ship.setBlocked(true);
                        answers.setBlocked(false);
                    }
                    answersPool.updateActiveSprites(delta);
                    break;
                case GAME_OVER:

                    break;
            }
        }

        private void checkCollisions(){
            if (state==State.PLAYING) {
                List<SweetGoal> sweetGoalList = sweetGoalPool.getActiveObjects();
                for (SweetGoal sweetGoal : sweetGoalList) {
                    if (sweetGoal.isDestroyed()) {
                        continue;
                    }
                    float minDist = sweetGoal.getHalfHeight() + ship.getHalfWidth();
                    if (sweetGoal.pos.dst2(ship.pos) < minDist * minDist) {
                        sweetGoal.destroy();
                        ship.damage(sweetGoal.getDamage());
                        return;
                    }
                }

                List<Ink> inkList = inkPool.getActiveObjects();
                for (Ink ink : inkList) {
                    if (ink.isDestroyed()) {
                        continue;
                    }
                    float minDist = ink.getHalfHeight() + ship.getHalfWidth();
                    if (ink.pos.dst2(ship.pos) < minDist * minDist) {
                        ink.setState(COLLISION);
                        ink.destroy();
                        ship.damage(ink.getDamage());
                        return;
                    }
                }

                List<Bullet> bulletList = bulletPool.getActiveObjects();
                for (SweetGoal sweetGoal : sweetGoalList) {
                    if (sweetGoal.isDestroyed()) {
                        continue;
                    }
                    for (Bullet bullet : bulletList) {
                        if (bullet.isDestroyed()) {
                            continue;
                        }
                        if (sweetGoal.isBulletCollision(bullet)) {
                            sweetGoal.damage(ship.getDamage());
                            bullet.destroy();
                        }
                    }
                }
            }
        }

        private void deleteAllDestroyed() {
            if (ship.isDestroyed()){
                state=State.GAME_OVER;
            }
            bulletPool.freeAllDestroyedActiveSprites();
            explosionPool.freeAllDestroyedActiveSprites();
            sweetGoalPool.freeAllDestroyedActiveSprites();
            inkPool.freeAllDestroyedActiveSprites();
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
            switch (state){
                case PLAYING:
                    ship.draw(batch);
                    bulletPool.drawActiveSprites(batch);
                    sweetGoalPool.drawActiveSprites(batch);
                    inkPool.drawActiveSprites(batch);
                    answersPool.drawActiveSprites(batch);
                    break;
                case GAME_OVER:
                    gameOver.draw(batch);
                    newGame.draw(batch);
                    break;
            }
            explosionPool.drawActiveSprites(batch);
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
            sweetGoalPool.dispose();
            inkPool.dispose();
            answers.dispose();
            super.dispose();
        }

        @Override
        public boolean keyDown(int keycode) {
                if (state==State.PLAYING){
                    ship.keyDown(keycode);
                }
            return super.keyDown(keycode);
        }

        @Override
        public boolean keyUp(int keycode) {
            if (state==State.PLAYING)ship.keyUp(keycode);
            return super.keyUp(keycode);
        }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
        public boolean touchDown(Vector2 touch, int pointer) { ;
            if (state==State.PLAYING){
                ship.touchDown(touch, pointer);
                answers.touchDown(touch, pointer);
            }else {
                newGame.touchDown(touch,pointer);
            }
            return super.touchDown(touch, pointer);
        }

    @Override
        public boolean touchUp(Vector2 touch, int pointer) {

            if (state==State.PLAYING) {
                answers.touchUp(touch,pointer);
                if (answers.isTrueAnsver()){
                    ship.setBlocked(false);
                    answers.setBlocked(true);
                }
                ship.touchUp(touch, pointer);
            }else {
                newGame.touchUp(touch,pointer);
            }
            return super.touchUp(touch, pointer);
        }

    public void startNewGame(){
            state=State.PLAYING;
            bulletPool.freeAllActiveObjects();
            explosionPool.freeAllActiveObjects();
            inkPool.freeAllActiveObjects();
            sweetGoalPool.freeAllActiveObjects();
            ship.startNewGame();
    }
}

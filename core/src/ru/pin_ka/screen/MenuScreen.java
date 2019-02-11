package ru.pin_ka.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Base2DScreen;
import ru.pin_ka.math.Rect;
import ru.pin_ka.sprite.Background;
import ru.pin_ka.sprite.CandyBg;
import ru.pin_ka.sprite.menu.ButtonExit;
import ru.pin_ka.sprite.menu.NameGame;
import ru.pin_ka.sprite.menu.ButtonPlay;

public class MenuScreen extends Base2DScreen {

    private Game game;

    private TextureAtlas atlas;
    private Texture bg;
    private Background background;
    private CandyBg [] candyBg;
    private ButtonPlay play;
    private ButtonExit exit;
    private NameGame nameGame;

    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/bg.jpg");
        background=new Background(new TextureRegion(bg));
        atlas=new TextureAtlas("textures/menuAtlas.tpack");
        candyBg=new CandyBg[50];
        for (int i=0;i<candyBg.length;i++){
            candyBg [i]=new CandyBg(atlas);
        }
        play=new ButtonPlay(atlas,game);
        exit=new ButtonExit(atlas);
        nameGame=new NameGame(atlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    public void update(float delta){
        for (CandyBg aCandyBg : candyBg) {
            aCandyBg.update(delta);
        }
    }

    private void draw(){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (CandyBg aCandyBg : candyBg) {
            aCandyBg.draw(batch);
        }
        play.draw(batch);
        exit.draw(batch);
        nameGame.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (CandyBg aCandyBg : candyBg) {
            aCandyBg.resize(worldBounds);
        }
        play.resize(worldBounds);
        exit.resize(worldBounds);
        nameGame.resize(worldBounds);
    }

    @Override
    public void dispose() {
        bg.dispose();
        atlas.dispose();
        super.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        exit.touchDown(touch,pointer);
        play.touchDown(touch,pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        exit.touchUp(touch,pointer);
        play.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {

        return super.keyDown(keycode);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }
}

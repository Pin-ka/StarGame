package ru.pin_ka.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {
    SpriteBatch batch;
    Texture background;
    Texture aim;

    Vector2 pos;
    Vector2 tap;
    Vector2 v;//вектор скорости

    int count=0;//счетчик итераций темпа
    int div=50;//темп движения картинки

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("cookie.jpg");
        aim=new Texture("aim.png");
        pos=new Vector2(0,0);
        v=new Vector2(2,2);
        tap=new Vector2(0,0);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, 0, 0);
        batch.draw(aim,pos.x,pos.y);
        batch.end();
        if(count>0){
            pos.add(v);
            count--;
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        aim.dispose();
        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        tap.set(screenX-57,Gdx.graphics.getHeight()-screenY-95);
        //так как картинка - стрелочка, то логичнее, если в цель придёт острие стрелочки.
        count=div;
        v=tap.cpy().sub(pos).nor().scl(tap.cpy().sub(pos).len()/div);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==19){
            tap.set(pos.x,Gdx.graphics.getHeight()-150);//чтобы картинка не выезжала за рамку
        }
        if (keycode==20){
            tap.set(pos.x,0);
        }
        if (keycode==21){
            tap.set(0,pos.y);
        }
        if (keycode==22){
            tap.set(Gdx.graphics.getWidth()-150,pos.y);//чтобы картинка не выезжала за рамку
        }
        count=div;
        v=tap.cpy().sub(pos).nor().scl(tap.cpy().sub(pos).len()/div);
        return super.keyDown(keycode);
    }
}

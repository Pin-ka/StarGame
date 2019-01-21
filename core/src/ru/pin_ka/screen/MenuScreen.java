package ru.pin_ka.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Base2DScreen;

public class MenuScreen extends Base2DScreen {

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
        background = new Texture("cookie.jpg");
        aim=new Texture("aim.png");
        pos=new Vector2(-0.67f,-0.5f);
        v=new Vector2(0.001f,0.002f);
        tap=new Vector2(-0.67f,-0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(background, -0.67f,-0.5f, 1.34f,1f);
        batch.draw(aim,pos.x,pos.y,0.3f,0.3f);
        batch.end();
        if(count>0){
            pos.add(v);
            count--;
        }
    }

    @Override
    public void dispose() {
        background.dispose();
        aim.dispose();
        super.dispose();
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        tap.set(touch.x-0.11f,touch.y-0.19f);
        //так как картинка - стрелочка, то логичнее, если в цель придёт острие стрелочки.
        count=div;
        v=tap.cpy().sub(pos).nor().scl(tap.cpy().sub(pos).len()/div);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode==19){
            tap.set(pos.x,0.5f-0.3f);//чтобы картинка не выезжала за рамку
        }
        if (keycode==20){
            tap.set(pos.x,-0.5f);
        }
        if (keycode==21){
            tap.set(-0.67f,pos.y);
        }
        if (keycode==22){
            tap.set(1.34f-0.67f-0.3f,pos.y);//чтобы картинка не выезжала за рамку
        }
        count=div;
        v=tap.cpy().sub(pos).nor().scl(tap.cpy().sub(pos).len()/div);
        return super.keyDown(keycode);
    }
}

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
    Vector2 sub;//разность векторов
    Vector2 v;//вектор скорости
    Vector2 norm;//вектор направления
    float v1=0;//скорость перемещения картинки
    int count=0;//счетчик итераций темпа
    int div=50;//темп движения картинки
    boolean isKeyDown=false;//переключатель между режимами: клик по мыши/клик по стрелке на клавиатуре

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        background = new Texture("cookie.jpg");
        aim=new Texture("aim.png");
        pos=new Vector2(0,0);
        v=new Vector2(2,2);
        tap=new Vector2(0,0);
        sub=new Vector2(0,0);
        norm=new Vector2(0,0);
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
            if (count==div){
                if (!isKeyDown) {
                    tap.add(new Vector2(-57, -95));
                    //так как картинка - стрелочка, то логичнее, если в цель придёт острие стрелочки.
                }
                sub=tap.sub(pos);
                v1=sub.len()/div;
                norm=sub.nor();
                v=norm.scl(v1);
            }
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
        float tapX=screenX;
        float tapY=Gdx.graphics.getHeight()-screenY;
        tap.set(tapX,tapY);
        count=div;
        isKeyDown=false;
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
        isKeyDown=true;
        return super.keyDown(keycode);
    }
}

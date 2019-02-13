package ru.pin_ka.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.math.Rect;
import ru.pin_ka.utils.Regions;

public class Sprite extends Rect {
    private float angle;
    protected float scale=1f;
    protected TextureRegion [] regions;
    protected int frame;
    private boolean isDestroyed;

    public Sprite (TextureRegion region){
        if (region==null){
            throw new NullPointerException("Create Sprite witch null region");
        }
        regions = new TextureRegion[1];
        regions[0]=region;
    }
    public Sprite (TextureRegion region, int rows, int cols, int frames){
        if (region==null){
            throw new NullPointerException("Create Sprite witch null region");
        }
        this.regions=Regions.split(region,rows,cols,frames);
    }

    public Sprite (){

    }

    public void setHeightProportion (float height){
        setHeight(height);
        float aspect=regions[frame].getRegionWidth()/(float)regions[frame].getRegionHeight();
        setWidth(height*aspect);
    }

    public void draw (SpriteBatch batch) {
        batch.draw(
          regions [frame],
          getLeft(),getBottom(),
          halfWidth,halfHeight,
          getWidth(),getHeight(),
          scale,scale,
          angle
        );
    }

    public void resize (Rect worldBounds){
    }

    public void update (float delta){
    }

    public boolean touchDown (Vector2 touch,int pointer){
        return false;
    }

    public boolean touchUp (Vector2 touch,int pointer){
        return  false;
    }

    public float getAngle() {
        return angle;
    }

    public float getScale() {
        return scale;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy(){
        this.isDestroyed=true;
    }

    public void  flushDestroy(){
        this.isDestroyed=false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}

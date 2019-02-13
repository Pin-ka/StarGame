package ru.pin_ka.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import ru.pin_ka.base.Sprite;
import ru.pin_ka.utils.Regions;

public class Explosion extends Sprite {

    protected enum Type {CAKE,SHIP,INK};

    private float animateInterval = 0.03f;
    private float animateTimer;
    TextureAtlas atlas;
    Type type;
    int nFrames;

    public Explosion(TextureAtlas atlas) {
        this.atlas=atlas;
        regions=new TextureRegion[nFrames];
    }

    public void set(float height, Vector2 pos,Type type) {
        this.pos.set(pos);

        this.type=type;
        switch (type){
            case INK:
                nFrames=10;
                this.regions=Regions.split(atlas.findRegion("spray"),1,10,10);
                break;
            case CAKE:
                nFrames=14;
                this.regions=Regions.split(atlas.findRegion("expCake"),2,7,14);
                break;
            case SHIP:
                nFrames=32;
                this.regions=Regions.split(atlas.findRegion("expShip"),4,8,32);
                break;
        }
        setHeightProportion(height);
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == regions.length) {
                destroy();
            }
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        frame = 0;
    }
}

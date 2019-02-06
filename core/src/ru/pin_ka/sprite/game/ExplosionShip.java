package ru.pin_ka.sprite.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Sprite;

public class ExplosionShip extends Sprite {
    private float animateInterval = 0.01f;
    private float animateTimer;
    private Sound explosionSound;

    public ExplosionShip(TextureAtlas atlas) {
        super(atlas.findRegion("expShip"), 4, 8, 32);
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/expShip.wav"));;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play();
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            animateTimer = 0f;
            if (++frame == 32) {
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

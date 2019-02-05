package ru.pin_ka.sprite.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.pin_ka.base.Sprite;

public class ExplosionCake extends Sprite {

    private float animateInterval = 0.01f;
    private float animateTimer;
    private Sound explosionSound;

    public ExplosionCake(TextureRegion region, int rows, int cols, int frames, Sound explosionSound) {
        super(region, rows, cols, frames);
        this.explosionSound = explosionSound;
    }

    public void set(float height, Vector2 pos) {
        this.pos.set(pos);
        setHeightProportion(height);
        explosionSound.play(0.5f);
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

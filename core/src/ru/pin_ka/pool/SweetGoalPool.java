package ru.pin_ka.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.SweetGoal;

public class SweetGoalPool extends SpritesPool <SweetGoal> {

    private Sound shootSound;
    private BulletPool bulletPool;

    public SweetGoalPool(BulletPool bulletPool) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/kap.wav"));
        this.bulletPool=bulletPool;
    }

    @Override
    protected SweetGoal newObject() {
        return new SweetGoal(shootSound, bulletPool);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}

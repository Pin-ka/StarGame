package ru.pin_ka.pool;

import ru.pin_ka.base.Sprite;
import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.Bullet;

public class BulletPool extends SpritesPool <Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}

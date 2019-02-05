package ru.pin_ka.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.pin_ka.base.SpritesPool;
import ru.pin_ka.sprite.game.Answers;

public class AnswersPool extends SpritesPool <Answers> {

    private TextureAtlas atlas;

    public AnswersPool(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    @Override
    protected Answers newObject() {
        return new Answers(atlas);
    }

    @Override
    public void updateActiveSprites(float delta) {
        super.updateActiveSprites(delta);
    }

    @Override
    public void dispose() {
        //только вот нужно затащить музыку сюда
        super.dispose();
    }
}

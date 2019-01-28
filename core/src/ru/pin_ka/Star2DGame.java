package ru.pin_ka;

import com.badlogic.gdx.Game;

import ru.pin_ka.screen.MenuScreen;

public class Star2DGame extends Game {
    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}

package ru.pin_ka;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture aim;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("cookie.jpg");
		aim=new Texture("aim.png");
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(background, 0, 0);
		batch.draw(aim,0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		background.dispose();
		batch.dispose();
	}
}

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;

public class Labyrinth extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;

    @Override
	public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        batch = new SpriteBatch();

        //Put new items and objects here

	}

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        //Put sprites and effects here

        gameStage.draw();
        batch.end();

    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
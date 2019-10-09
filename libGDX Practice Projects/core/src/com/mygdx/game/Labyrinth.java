package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Labyrinth extends ApplicationAdapter {
	private SpriteBatch batch;
	private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;

    @Override
	public void create () {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1100, 900);
        board = new Board();
        gameStage = new Stage();
        Gdx.input.setInputProcessor(gameStage);
        //Put new items and objects here

	}

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Put sprites and effects here

        batch.begin();
        board.draw(batch);
        gameStage.draw();
        batch.end();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }

}
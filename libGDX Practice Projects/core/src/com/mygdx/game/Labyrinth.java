package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Labyrinth extends Game {
	private SpriteBatch batch;
	private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;
    private Music music;
    /*private Tile[][] TileArray;*/
    public static int Width = 1024;
    public static int Height = 1024;

    @Override
	public void create () {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Width, Height * (Gdx.graphics.getHeight()/Gdx.graphics.getWidth()));
        board = new Board();
        gameStage = new Stage(new FitViewport(Width,Height * (Gdx.graphics.getHeight()/Gdx.graphics.getWidth()), camera));
        Gdx.input.setInputProcessor(gameStage);
        setScreen(new SplashScreen(this));
        /*TileArray = gameBoard.getBoard();*/
        //Put new items and objects here

	}

    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Put sprites and effects here

        /*batch.begin();
        board.draw(batch);
        gameStage.draw();
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        //Put sprites and effects here

        batch.end();*/
        super.render();

    }

    @Override
    public void dispose () {
        batch.dispose();
    }

    @Override
    public void resize(int width, int height){
        camera.viewportWidth = 10f;
        camera.viewportHeight = 10f * height/width;
        camera.update();
    }
}

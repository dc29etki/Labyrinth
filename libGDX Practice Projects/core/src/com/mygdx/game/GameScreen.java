package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;


public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;
    private Music music;
    private TextButton button;
    private TextButton.TextButtonStyle textButtonStyle;
    private BitmapFont font;
    private Skin skin;
    private TextureAtlas buttonAtlas;

    public GameScreen(Game game) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        board = new Board();
        gameStage = new Stage();
        this.game = game; // Store this to call game.setScreen(new MenuScreen(game)) to return to the menu
        textButtonStyle = new TextButton.TextButtonStyle();
        font = new BitmapFont();
        skin = new Skin();
        textButtonStyle.font = font;
        button = new TextButton("Button1", textButtonStyle);
        button.setPosition(100,  500);
        gameStage.addActor(button);
        Gdx.input.setInputProcessor(gameStage);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("CLICKED");
                board.insertTile(0,3);
                //board = new Board();
            }
        });

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        //Put sprites and effects here

        batch.begin();
        Gdx.input.setInputProcessor(gameStage);
        board.draw(batch);
        gameStage.draw();
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        /*for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                Tile tile = gameBoard.getBoard() [i][j];
                tile.draw(batch);
            }

        }*/

        //Put sprites and effects here

        /* gameStage.draw();*/
        batch.end();

        // Do game logic and rendering
    }

    @Override
    public void show() { }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() { }

    @Override
    public void dispose() { }
}
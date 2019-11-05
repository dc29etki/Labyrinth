package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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
    private TextButton button, button1;
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
        button = new TextButton("Insert Tile 1   ->", textButtonStyle);
        button.setPosition(15,  550);
        gameStage.addActor(button);
        Gdx.input.setInputProcessor(gameStage);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("insertTile(3,0)");
                board.insertTile(3, 0);
            }
        });
        button1 = new TextButton("Insert Tile 2 \\/", textButtonStyle);
        button1.setPosition(630,  940);
        gameStage.addActor(button1);
        Gdx.input.setInputProcessor(gameStage);
        button1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                System.out.println("insertTile(0,5)");
                board.insertTile(0, 5);
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        //Put sprites and effects here

        Gdx.input.setInputProcessor(gameStage);
        board.draw(batch);
        System.out.println(board.getBoard()[3][3].toString());
        //
        //Draw empty sprite to update all other drawings
        Sprite green = new Sprite();
        green.setTexture(new Texture(Gdx.files.internal("Blank_Icon.png")));
        green.setPosition(-10, -10);
        green.draw(batch);

        gameStage.draw();
        Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
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
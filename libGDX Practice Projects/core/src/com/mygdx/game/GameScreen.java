package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Random;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;


public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;
    private Music music;
    private Tile[] tilePaths;
    private GameRunner runEnv;
    private MoveButtons buttons;

    public GameScreen(Game game) {
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        board = new Board();
        gameStage = new Stage();
        this.game = game; // Store this to call game.setScreen(new MenuScreen(game)) to return to the menu
        runEnv = new GameRunner(board, batch);
        runEnv.getPlayers()[0].swapSprite();
        runEnv.dealCards(0);

        Gdx.input.setInputProcessor(gameStage);

        buttons = new MoveButtons(-2, board, runEnv);
        buttons.disableMove();
        for(Button button : buttons.getInsButtons()) {
            gameStage.addActor(button);
        }
        for(Button[] row : buttons.getButtons()) {
            for (Button button : row) {
                gameStage.addActor(button);
            }
        }


        ImageButton rotateClk = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/BackArrow.png")))));
        rotateClk.setPosition(9*(Width/10 + 1) - 32, Height - 8*(Height/10 + 1)-32);
        rotateClk.setSize(64,64);
        gameStage.addActor(rotateClk);
        Gdx.input.setInputProcessor(gameStage);
        rotateClk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.getExtraTile().rotate(1);
            }
        });
        ImageButton rotateCClk = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/BackArrowF.png")))));
        rotateCClk.setPosition(10*(Width/10 + 1) - 32, Height - 8*(Height/10 + 1)-32);
        rotateCClk.setSize(64,64);
        gameStage.addActor(rotateCClk);
        Gdx.input.setInputProcessor(gameStage);
        rotateCClk.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                board.getExtraTile().rotate(-1);
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
        board.draw(batch);

        runEnv.draw(batch);

        //System.out.println(board.getBoard()[3][3].toString());
        //
        //Draw empty sprite to update all other drawings
        Sprite green = new Sprite();
        green.setTexture(new Texture(Gdx.files.internal("Blank_Icon.png")));
        green.setPosition(-10, -10);
        green.draw(batch);

        gameStage.draw();

        //Music Disabled For Testing Ease
        //Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));
        /*for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                Tile tile = gameBoard.getBoard() [i][j];
                tile.draw(batch);
            }

        }*/

        batch.end();
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
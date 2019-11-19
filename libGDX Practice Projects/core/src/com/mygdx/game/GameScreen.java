package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.awt.*;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;


public class GameScreen implements Screen {
    private Game game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private Stage gameStage;
    private Board board;
    private Music music;
    private int isTestCalled = 0;
    Robot robot = new Robot();

    public GameScreen(Game game) throws AWTException{
        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        board = new Board();
        gameStage = new Stage();
        this.game = game; // Store this to call game.setScreen(new MenuScreen(game)) to return to the menu
        /*
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
        */
        ImageButton ins01 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins03 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins05 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins10 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins30 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins50 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins61 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins63 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins65 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins16 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton ins36 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton ins56 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton[] insButtons = new ImageButton[]{ins01,ins03,ins05,ins10,ins30,ins50,ins61,ins63,ins65,ins16,ins36,ins56};
        final int[][] insLoc = new int[][]{{0,1},{0,3},{0,5},{1,0},{3,0},{5,0},{6,1},{6,3},{6,5},{1,6},{3,6},{5,6}};
        for(int i = 0; i < insButtons.length; i++){
            insButtons[i].setSize(64,64);
            if(insLoc[i][0] == 0){
                insButtons[i].setPosition((insLoc[i][1]+1) * (Width / 10 + 1) + 16, Height - Height/5 + 112);
            }else if(insLoc[i][1] == 0){
                insButtons[i].setPosition(32, Height - (insLoc[i][0]+2) * (Height / 10 + 1) + 16);
            }else if(insLoc[i][0] == 6){
                insButtons[i].setPosition((insLoc[i][1]+1) * (Width / 10 + 1), Height - 9 * (Height / 10 + 1) + 32);
            }else if(insLoc[i][1] == 6) {
                insButtons[i].setPosition(8 * (Width / 10 + 1), Height - (insLoc[i][0]+2) * (Height / 10 + 1) + 16);
            }
            gameStage.addActor(insButtons[i]);
            Gdx.input.setInputProcessor(gameStage);
            final int thisPos = i;
            insButtons[i].addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    board.insertTile(insLoc[thisPos][0], insLoc[thisPos][1]);
                    runTilePathing(board);
                }
            });
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

        //System.out.println(board.getBoard()[3][3].toString());
        //
        //Draw empty sprite to update all other drawings
        Sprite green = new Sprite();
        green.setTexture(new Texture(Gdx.files.internal("Blank_Icon.png")));
        green.setPosition(-10, -10);
        green.draw(batch);

        gameStage.draw();
        if( isTestCalled < 99 )
        {
            System.out.println(System.currentTimeMillis() + " TEST");
            isTestCalled++;
            click(250, 75);
        }
        else if(isTestCalled < 199)
        {
            isTestCalled++;
                    click(465,75);

        }
        else if(isTestCalled < 299)
        {
            isTestCalled++;
            click(660,75);

        }
        else if(isTestCalled < 399)
        {
            isTestCalled++;
            click(890,270);

        }
        batch.end();

        // Do game logic and rendering
    }
    public void click(int x, int y){
        //if(click == true){
        //t1.start();

        Thread t1 = new Thread();

        robot.mouseMove(x, y);
        //Thread.sleep(5000);
        //robot.delay(5000);
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_MASK);
        t1.start();


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

    public void runTilePathing(Board board){
        Tile[][] boardTileArrays = board.getBoard();
        for(int i = 0; i < boardTileArrays.length; i++){
            GameRunner.clearTilePaths(boardTileArrays[i]);
        }
        GameRunner.showTilePaths(board.getBoard()[3][3],board);
    }
}
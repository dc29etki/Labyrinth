package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

public class Labyrinth extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private Texture img1;
	private Texture cntrImg;
	private Texture treasureTest;
	private OrthographicCamera camera;
	private Rectangle treasureRect;
	private Tile testTile;
    private Skin skin;
    private Stage gameStage;
    private BitmapFont font;
    private TextButton buttonText;
    private TextButton.TextButtonStyle textButtonStyle;
    private ImageButton tileButton;
    private ImageButton.ImageButtonStyle imageButtonStyle;
    private Board GameBoard;
    private Tile[][] TileArray;
    private Music music;

	@Override
	public void create () {
        batch = new SpriteBatch();
        img = new Texture("Card_Base.png");
        img1 = new Texture(Gdx.files.internal("Icon_Helmet.png"));
        cntrImg = new Texture(Gdx.files.internal("Piece_Corner_Blank.png"));
        treasureTest = new Texture(Gdx.files.internal("Icon_Skull.png"));
        /*Music music = Gdx.audio.newMusic(Gdx.files.internal("Startup_Sound.wav"));*/
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 800);
        batch = new SpriteBatch();
        GameBoard = new Board(15, 15);
        TileArray = GameBoard.getBoard();
        Music music = Gdx.audio.newMusic(Gdx.files.internal("GameBackground.wav"));
        music.play();
        music.setLooping(true);





        gameStage = new Stage();
        Gdx.input.setInputProcessor(gameStage);
        skin = new Skin();
        skin.add("Icon", img);
        skin.add("Tile", new Texture(Gdx.files.internal("Piece_Straight_Blank.png")));
        font = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("Icon");
        textButtonStyle.down = skin.getDrawable("Icon");
        textButtonStyle.checked = skin.getDrawable("Icon");
        buttonText = new TextButton("Test Button", textButtonStyle);
        buttonText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("TEST BUTTON PRESSED. PERFORMING TEST.");
                System.out.println("TEST SUCCESSFUL");
            }
        });
        gameStage.addActor(buttonText);


        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.getDrawable("Tile");
        imageButtonStyle.down = skin.getDrawable("Tile");
        tileButton = new ImageButton(imageButtonStyle);
        gameStage.addActor(tileButton);
        tileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tileButton.moveBy(5, 5);
            }
        });


        treasureRect = new Rectangle();
        treasureRect.x = 15;
        treasureRect.y = 25;
        treasureRect.width = 47;
        treasureRect.height = 47;

        testTile = new Tile(0, 20, 1, 1, 0);


		/*batch.begin();

		batch.draw(treasureTest, treasureRect.x, treasureRect.y);

		batch.draw(testTile.getTilePng(), testTile.getTilePosition().x, testTile.getTilePosition().y);
		batch.draw(testTile.getTreasurePng(), testTile.getTreasurePosition().x, testTile.getTreasurePosition().y);

		batch.end();

		gameStage.draw();*/

	}



    @Override
    public void render () {
        Gdx.gl.glClearColor(0, 1, 5, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        /*Sound sound = Gdx.audio.newSound(Gdx.files.internal("Startup_Sound.wav"));
        sound.play();*/
        /*batch.draw(img1, 100, 100);*/
        for (int i=0; i<15; i++){
            for (int j=0; j<15; j++){
                Tile tile = GameBoard.getBoard() [i][j];
                Texture tex = tile.getTilePng();
                batch.draw(tex, 100*i, 100*j);
            }

        }

        /*batch.draw(img, 0, 0);
        batch.draw(cntrImg, 400, 400);*/

        if(Gdx.input.isTouched()) {
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            treasureRect.x = touchPos.x;
            treasureRect.y = 800- touchPos.y;
            
        }else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed((Input.Keys.A))) {
            treasureRect.x -= 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed((Input.Keys.D))){
            treasureRect.x += 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed((Input.Keys.W))){
            treasureRect.y += 200 * Gdx.graphics.getDeltaTime();
        }else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed((Input.Keys.S))){
            treasureRect.y -= 200 * Gdx.graphics.getDeltaTime();
        }

        batch.draw(treasureTest, treasureRect.x, treasureRect.y);

        batch.draw(testTile.getTilePng(), testTile.getTilePosition().x, testTile.getTilePosition().y);
        batch.draw(testTile.getTreasurePng(), testTile.getTreasurePosition().x, testTile.getTreasurePosition().y);


        batch.end();

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
        music.dispose();
    }


    public Object[] getAllArrays(){
        Object[] array = new Object[6];
        array[0]=img;
        array[1]=cntrImg;
        array[2]=treasureTest;
        array[3]=treasureRect;
        array[4]=testTile;
        array[5]=GameBoard;
        return array;
    }
}
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

public class Labyrinth extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
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

	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("Card_Base.png");
		cntrImg = new Texture(Gdx.files.internal("Piece_Corner_Blank.png"));
		treasureTest = new Texture(Gdx.files.internal("Icon_Skull.png"));
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);
		batch = new SpriteBatch();

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
                tileButton.moveBy(5,5);
            }
        });


		treasureRect = new Rectangle();
		treasureRect.x = 15;
		treasureRect.y = 25;
		treasureRect.width = 47;
		treasureRect.height = 47;

		testTile = new Tile(0,20,1,1,0);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 5, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 0, 0);
		batch.draw(cntrImg, 400, 400);

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

		gameStage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}

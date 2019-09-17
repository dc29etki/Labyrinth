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
	private OrthographicCamera camera;
	private Tile testTile;
	private Card testCard;
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
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 800);

		gameStage = new Stage();
		Gdx.input.setInputProcessor(gameStage);

        testTile = new Tile(2,7,1,1,0);
        testCard = new Card(7, 450,450);

		skin = new Skin();
        skin.add("Icon", new Texture(Gdx.files.internal("Icon_Gem.png")));
        skin.add("Tile", new Texture(Gdx.files.internal("Icon_Spider.png")));
        font = new BitmapFont();
        textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font;
        textButtonStyle.up = skin.getDrawable("Icon");
        textButtonStyle.down = skin.getDrawable("Icon");
        textButtonStyle.checked = skin.getDrawable("Icon");
        buttonText = new TextButton("Rotate Tile", textButtonStyle);
        buttonText.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
				testTile.rotate(1);
            }
        });
        gameStage.addActor(buttonText);

		Table buttonHolder = new Table();
        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.up = skin.getDrawable("Tile");
        imageButtonStyle.down = skin.getDrawable("Tile");
        tileButton = new ImageButton(imageButtonStyle);
        gameStage.addActor(buttonHolder);
        buttonHolder.add(tileButton).size(64,64);
        tileButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                tileButton.moveBy(5,5);
            }
        });

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 1, 10, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			testTile.setNewPosition(800-(int)touchPos.x,(int)touchPos.y);
		}else if(Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed((Input.Keys.A))) {
			testTile.setNewPosition((int)(testTile.getTilePosition().x - 200 * Gdx.graphics.getDeltaTime()),(int)testTile.getTilePosition().y);
		}else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed((Input.Keys.D))){
			testTile.setNewPosition((int)(testTile.getTilePosition().x + 200 * Gdx.graphics.getDeltaTime()),(int)testTile.getTilePosition().y);
		}else if(Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed((Input.Keys.W))){
			testTile.setNewPosition((int)testTile.getTilePosition().x, (int)(testTile.getTilePosition().y + 200 * Gdx.graphics.getDeltaTime()));
		}else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed((Input.Keys.S))){
			testTile.setNewPosition((int)testTile.getTilePosition().x, (int)(testTile.getTilePosition().y - 200 * Gdx.graphics.getDeltaTime()));
		}

		Sprite testTileSprite = testTile.getTileSprite();
		testTileSprite.setX(0);
		testTileSprite.setY(0);
		testTileSprite.translate(testTile.getTilePosition().x, testTile.getTilePosition().y);
		testTileSprite.draw(batch);

		Sprite testTileTreasureSprite = testTile.getTreasureSprite();
		testTileTreasureSprite.setX(0);
		testTileTreasureSprite.setY(0);
		testTileTreasureSprite.translate(testTile.getTreasurePosition().x, testTile.getTreasurePosition().y);
		testTileTreasureSprite.draw(batch);

		testCard.draw(batch);

		batch.end();

		gameStage.draw();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

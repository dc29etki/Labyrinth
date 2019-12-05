package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;


public class VictoryScreen implements Screen {

    private Stage victorymenuStage;
    private Skin victorymenuSkinPlay;
    private ImageButton victorymenuimagebuttonPlay;
    private Table victorymenuTablePlay;
    private Texture victorymenuTexture;
    private Image victorymenuImage;
    private float WIDTH,HEIGHT;
    private Game g;
    private Music music;
    private int victor;
    private Label victorText;

    public VictoryScreen(Game g, int victor) {
        this.g = g;
        this.victor = victor;
    }

    @Override
    public void show() {
        WIDTH = Width;
        HEIGHT = (Height/4)*3;
        victorymenuTexture = Treasures.getPlayerTextures(0)[victor+8];
        victorymenuTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        victorymenuImage = new Image(victorymenuTexture);
        victorymenuImage.setSize(Height/8,Height/4);
        victorymenuImage.setPosition(WIDTH/2-Height/16,HEIGHT-Height/4);
        BitmapFont font = new BitmapFont();
        font.getData().setScale(3);
        victorText = new Label(".", new Label.LabelStyle(font,new Color(1,1,1,1)));
        switch(victor){
            case 1:
                victorText.setText("Blue Won!\n\nPlay Again?");
                victorText.setColor(0,0,1,1);
                break;
            case 2:
                victorText.setText("Yellow Won!\n\nPlay Again?");
                victorText.setColor(1,1,0,1);
                break;
            case 3:
                victorText.setText("Green Won!\n\nPlay Again?");
                victorText.setColor(0,1,0,1);
                break;
            case 0:
            default:
                victorText.setText("Red (You) Won!\n\nPlay Again?");
                victorText.setColor(1,0,0,1);
                break;
        }
        victorText.setAlignment(Align.center);
        victorText.setBounds(0,HEIGHT-630,WIDTH, Height/2);
        victorymenuStage = new Stage(new FitViewport(WIDTH,HEIGHT, new Cam(WIDTH,HEIGHT)));
        victorymenuTablePlay = new Table();
        victorymenuSkinPlay = new Skin(Gdx.files.internal("skins/play.json"), new TextureAtlas(Gdx.files.internal("skins/mainMenuPack.atlas")));
        victorymenuimagebuttonPlay = new ImageButton(victorymenuSkinPlay);
        victorymenuTablePlay.bottom().add(victorymenuimagebuttonPlay).size( Width/8F, Height/8F).padBottom(Height/50F);
        victorymenuStage.addActor(victorymenuImage);
        victorymenuStage.addActor(victorymenuTablePlay);
        victorymenuStage.addActor(victorText);
        Gdx.input.setInputProcessor(victorymenuStage);
        victorymenuTablePlay.addAction(Actions.sequence(Actions.moveBy(0.0F, -250F), Actions.delay(2.0F), Actions.moveBy(0.0F, 250F, 1.0F, Interpolation.swing)));
        victorymenuImage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(1.0F)));
        victorText.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.delay(1.0F), Actions.fadeIn(1.0F)));

        music = Treasures.getMusic()[2];
        music.play();
        music.setVolume(0.5f);
        music.setLooping(true);

        victorymenuimagebuttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                music.pause();
                music.dispose();
                g.setScreen(new LoadScreen(g));
            }
        });
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        victorymenuStage.act();
        victorymenuStage.draw();
    }


    @Override
    public void resize(int width, int height) {
        victorymenuStage.getViewport().update(width,height,true);

        victorymenuTablePlay.invalidateHierarchy();
        victorymenuTablePlay.setSize(WIDTH, HEIGHT);
        Width = width;
        Height = height;
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();    }

    @Override
    public void dispose() {

        victorymenuStage.dispose();
        victorymenuTexture.dispose();
        victorymenuSkinPlay.dispose();

    }
}
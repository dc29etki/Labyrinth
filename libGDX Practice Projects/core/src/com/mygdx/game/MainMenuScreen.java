package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.awt.*;


public class MainMenuScreen extends Thread implements Screen{

    private Stage mainmenuStage;
    private Skin mainmenuSkinPlay;
    private ImageButton mainmenuimagebuttonPlay;
    private Table mainmenuTablePlay;
    private Texture mainmenuTexture;
    private Image mainmenuImage;
    private float WIDTH,HEIGHT;
    private Game g;
    Robot robot = new Robot();
    private boolean isTestCalled = false;

    public MainMenuScreen(Game g) throws AWTException {
        this.g = g;
    }

    @Override
    public void show() {
        System.out.println(System.currentTimeMillis() + " Start");
        WIDTH = 1280;
        HEIGHT = 720;
        mainmenuTexture = new Texture("mainMenu/mainMenu.png");
        mainmenuTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        mainmenuImage = new Image(mainmenuTexture);
        mainmenuImage.setSize(1280,720);
        mainmenuStage = new Stage(new FitViewport(WIDTH,HEIGHT, new Cam(WIDTH,HEIGHT)));
        mainmenuTablePlay = new Table();
        mainmenuSkinPlay = new Skin(Gdx.files.internal("skins/play.json"), new TextureAtlas(Gdx.files.internal("skins/mainMenuPack.atlas")));
        mainmenuimagebuttonPlay = new ImageButton(mainmenuSkinPlay);
        mainmenuTablePlay.bottom().add(mainmenuimagebuttonPlay).size( 152F, 164F).padBottom(20F);
        mainmenuStage.addActor(mainmenuImage);
        mainmenuStage.addActor(mainmenuTablePlay);
        Gdx.input.setInputProcessor(mainmenuStage);
        mainmenuTablePlay.addAction(Actions.sequence(Actions.moveBy(0.0F, -250F), Actions.delay(1.0F), Actions.moveBy(0.0F, 250F, 1.0F, Interpolation.swing)));
        mainmenuImage.addAction(Actions.sequence(Actions.alpha(0.0F), Actions.fadeIn(1.0F)));



        mainmenuimagebuttonPlay.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                g.setScreen(new GameScreen(g));
            }
        });
        System.out.println(System.currentTimeMillis() + " Before click()");



    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainmenuStage.act();
        mainmenuStage.draw();
        click(1000, 1000);
        if( !isTestCalled )
        {
            System.out.println(System.currentTimeMillis() + " TEST");
            isTestCalled = true;
            click(1000, 1000);
        }

    }

    public void click(int x, int y){
        //if(click == true){
        //t1.start();
        System.out.println(System.currentTimeMillis() + " CLICK");
        Thread t1 = new Thread();

        robot.mouseMove(1000, 800);
        //Thread.sleep(5000);
        //robot.delay(5000);
        robot.mousePress(java.awt.event.InputEvent.BUTTON1_MASK);
        robot.mouseRelease(java.awt.event.InputEvent.BUTTON1_MASK);
        t1.start();


    }

    @Override
    public void resize(int width, int height) {
        mainmenuStage.getViewport().update(width,height,true);

        mainmenuTablePlay.invalidateHierarchy();
        mainmenuTablePlay.setSize(WIDTH, HEIGHT);
    }

    @Override
    public void pause() {
    }



    @Override
    public void hide() {
        dispose();    }

    @Override
    public void dispose() {

        mainmenuStage.dispose();
        mainmenuTexture.dispose();
        mainmenuSkinPlay.dispose();

    }
}
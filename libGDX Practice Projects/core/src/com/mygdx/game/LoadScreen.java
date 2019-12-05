package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class LoadScreen implements Screen{
    private Texture splashtexture;
    private Image splashimage;
    private Texture splashText;
    private Image splashLoading;
    private Stage splashstage;
    private float WIDTH,HEIGHT;
    private Cam cam;
    private Game g;
    public LoadScreen(Game g){
        this.g = g;
    }
    @Override
    public void show(){
        WIDTH = 1280;
        HEIGHT = 800;

        splashtexture = new Texture(Gdx.files.internal("SplashScreen.png"));
        splashtexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashimage = new Image(splashtexture);
        splashimage.setSize(1280,720);

        splashText = new Texture(Gdx.files.internal("Loading.png"));
        splashText.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        splashLoading = new Image(splashText);
        splashLoading.setSize(256,64);

        splashstage = new Stage(new FitViewport(WIDTH,HEIGHT, new Cam(WIDTH,HEIGHT)));
        splashstage.addActor(splashimage);
        splashstage.addActor(splashLoading);

        splashimage.addAction(Actions.sequence(Actions.delay(0.25F), Actions.run(new Runnable() {
            @Override
            public void run() {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen(g));
            }
        })));

    }
    @Override
    public void render (float delta){
        Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        splashstage.act();
        splashstage.draw();

    }
    @Override
    public void resize (int width, int height){
        splashstage.getViewport().update(width,height,true);
    }
    @Override
    public void pause(){

    }
    @Override
    public void resume(){

    }
    @Override
    public void hide(){
        dispose();
    }
    @Override
    public void dispose(){
        splashtexture.dispose();
        splashstage.dispose();
    }
}

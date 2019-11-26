//package com.mygdx.game;
package com.mygdx.game;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuE implements Screen{
    private Stage menuEStage;
    private Skin menuESkinResume;
    private ImageButton menuEResume;
    private Table menuETableResume;
    private Texture menuETexture;
    private Image menuEImage;
    private float WIDTH,HEIGHT;
    private Game g;
    public MenuE(Game g){
        this.g = g;
    }
    @Override
    public void show() {
        System.out.println(System.currentTimeMillis() + " Start");
        WIDTH = 1280;
        HEIGHT = 720;
        menuETexture = new Texture("mainMenu/mainMenu.png");
        menuETexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        menuEImage = new Image(menuETexture);
        menuEImage.setSize(1280,720);
        menuEStage = new Stage(new FitViewport(WIDTH,HEIGHT, new Cam(WIDTH,HEIGHT)));
        menuETableResume = new Table();
        menuESkinResume = new Skin(Gdx.files.internal("skins/play.json"), new TextureAtlas(Gdx.files.internal("skins/mainMenuPack.atlas")));
        menuEResume = new ImageButton(menuESkinResume);
        menuETableResume.bottom().add(menuEResume).size( 152F, 164F).padBottom(20F);
        menuEStage.addActor(menuEImage);
        menuEStage.addActor(menuETableResume);
        Gdx.input.setInputProcessor(menuEStage);
    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}

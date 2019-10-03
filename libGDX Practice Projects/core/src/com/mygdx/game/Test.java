package com.mygdx.game;
import com.badlogic.gdx.Game;




public class Test extends Game{
    public Test(){

    }
    @Override
    public void create() {
        setScreen(new SplashScreen());
    }



    public void render() {
        super.render();
    }
}


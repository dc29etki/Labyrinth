package com.mygdx.game;
import com.badlogic.gdx.graphics.OrthographicCamera;
public class Cam extends OrthographicCamera{
    public Cam(float width, float height){
        super((float)width, (float)height);
        this.position.x = (float)(width/2);
        this.position.y = (float)(width/2);
    }
}

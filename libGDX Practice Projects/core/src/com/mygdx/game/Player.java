package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player {
    int[] boardPosition;
    Texture smallTexture;
    Texture bigTexture;
    Sprite playerSprite;
    int color;
    Card[] hand;
    int[] drawPos;
    boolean myTurn;

    public Player(int playerNum){
        switch(playerNum){
            case -3:
                smallTexture = new Texture(Gdx.files.internal("Icon_BlueCircle.png"));
                setBoardPosition(0,6);
                break;
            case -4:
                smallTexture = new Texture(Gdx.files.internal("Icon_YellowCircle.png"));
                setBoardPosition(6,0);
                break;
            case -5:
                smallTexture = new Texture(Gdx.files.internal("Icon_GreenCircle.png"));
                setBoardPosition(6,6);
                break;
            case -2:
            default:
                smallTexture = new Texture(Gdx.files.internal("Icon_RedCircle.png"));
                setBoardPosition(0,0);
                break;
        }
    }

    void draw(SpriteBatch batch){
        playerSprite.draw(batch);
    }

    void setPosition(int x, int y){
        //WIP
    }

    void setPosition(Tile tile){
        //WIP
    }

    void setBoardPosition(int x, int y){

        //WIP

    }



}

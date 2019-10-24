package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.util.Stack;
import static com.mygdx.game.Labyrinth.*;

public class Player {
    int[] boardPosition;
    Texture smallTexture;
    Texture bigTexture;
    Sprite playerSprite;
    boolean isBigSprite;
    int color;
    Stack hand;
    int[] drawPos;
    boolean myTurn;

    public Player(int playerNum){
        switch(playerNum){
            case -3:
                smallTexture = new Texture(Gdx.files.internal("Icon_BlueCircle.png"));
                bigTexture = new Texture(Gdx.files.internal("Icon_BluePlayer.png"));
                setBoardPosition(0,6);
                break;
            case -4:
                smallTexture = new Texture(Gdx.files.internal("Icon_YellowCircle.png"));
                bigTexture = new Texture(Gdx.files.internal("Icon_YellowPlayer.png"));
                setBoardPosition(6,0);
                break;
            case -5:
                smallTexture = new Texture(Gdx.files.internal("Icon_GreenCircle.png"));
                bigTexture = new Texture(Gdx.files.internal("Icon_GreenPlayer.png"));
                setBoardPosition(6,6);
                break;
            case -2:
            default:
                smallTexture = new Texture(Gdx.files.internal("Icon_RedCircle.png"));
                bigTexture = new Texture(Gdx.files.internal("Icon_RedPlayer.png"));
                setBoardPosition(0,0);
                break;
        }
        isBigSprite = false;
        playerSprite = new Sprite(smallTexture);
        playerSprite.setSize(128,128);
    }

    void draw(SpriteBatch batch){
        playerSprite.draw(batch);
    }

    void deal(Card card){
        hand.add(card);
    }

    void setPosition(int x, int y){
        playerSprite.setPosition(x,y);
    }

    void setPosition(Tile tile){
        int[] tilePos = tile.getTilePosition();
        playerSprite.setPosition(tilePos[0] + Width/30, tilePos[1] + Height/30);
    }

    void setBoardPosition(int x, int y){
        boardPosition[0] = x;
        boardPosition[1] = y;
    }

    void swapSprite(){
        if(isBigSprite){
            playerSprite.setTexture(smallTexture);
            isBigSprite = false;
        }else{
            playerSprite.setTexture(bigTexture);
            isBigSprite = true;
        }
    }

    int getPlayer(){
        return color;
    }

    void foundCard(){
        hand.pop();
    }

    void showCard(SpriteBatch batch){
        ((Card)hand.peek()).draw(batch);
    }

    int[] getBoardPosition(){
        return boardPosition;
    }

    int[] getPosition(){
        return drawPos;
    }

    boolean isMyTurn(){
        return myTurn;
    }

    void setMyTurn(boolean turn){
        myTurn = turn;
    }
}

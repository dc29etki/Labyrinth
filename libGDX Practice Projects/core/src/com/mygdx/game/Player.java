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
                smallTexture = Treasures.getPlayerTextures(0)[5];
                bigTexture = Treasures.getPlayerTextures(0)[9];
                setBoardPosition(0,6);
                break;
            case -4:
                smallTexture = Treasures.getPlayerTextures(0)[6];
                bigTexture = Treasures.getPlayerTextures(0)[10];
                setBoardPosition(6,0);
                break;
            case -5:
                smallTexture = Treasures.getPlayerTextures(0)[7];
                bigTexture = Treasures.getPlayerTextures(0)[11];
                setBoardPosition(6,6);
                break;
            case -2:
            default:
                smallTexture = Treasures.getPlayerTextures(0)[4];
                bigTexture = Treasures.getPlayerTextures(0)[8];
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

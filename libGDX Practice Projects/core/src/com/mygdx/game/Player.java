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
    boolean isBigSprite = false;
    int color;
    Stack<Card> hand;
    int[] drawPos;
    boolean myTurn;

    public Player(int playerNum, Board board){
        Tile[][] boardCont = board.getBoard();
        color = playerNum;
        hand = new Stack<Card>();
        switch(playerNum){
            case -3://Blue
                smallTexture = Treasures.getPlayerTextures(0)[5];
                bigTexture = Treasures.getPlayerTextures(0)[9];
                playerSprite = new Sprite(smallTexture);
                setBoardPosition(6,0, board);
                break;
            case -4://Yellow
                smallTexture = Treasures.getPlayerTextures(0)[6];
                bigTexture = Treasures.getPlayerTextures(0)[10];
                playerSprite = new Sprite(smallTexture);
                setBoardPosition(0,6, board);
                break;
            case -5://Green
                smallTexture = Treasures.getPlayerTextures(0)[7];
                bigTexture = Treasures.getPlayerTextures(0)[11];
                playerSprite = new Sprite(smallTexture);
                setBoardPosition(6,6, board);
                break;
            case -2://Red
            default:
                smallTexture = Treasures.getPlayerTextures(0)[4];
                bigTexture = Treasures.getPlayerTextures(0)[8];
                playerSprite = new Sprite(smallTexture);
                setBoardPosition(0,0, board);
                break;
        }
        isBigSprite = false;
        playerSprite.setSize(Width/30,Height/30);
    }

    void draw(SpriteBatch batch){
        playerSprite.draw(batch);
    }

    void deal(Card card){
        hand.add(card);
    }

    Stack<Card> getHand(){
        return hand;
    }

    void setPosition(int x, int y){
        playerSprite.setPosition(x,y);
    }

    void setPosition(Tile tile){
        int[] tilePos = tile.getSpritePosition();
        playerSprite.setPosition(tilePos[0] + Width/30, tilePos[1] + Height/30);
        boardPosition = tile.getTilePosition();
    }

    void setBoardPosition(int x, int y, Board board){
        boardPosition = new int[2];
        boardPosition[0] = x;
        boardPosition[1] = y;
        setPosition(board.getBoard()[y][x]);
    }

    void swapSprite(){
        if(isBigSprite){
            playerSprite.setTexture(smallTexture);
            isBigSprite = false;
            playerSprite.setSize(Width/30,Height/30);
        }else{
            playerSprite.setTexture(bigTexture);
            isBigSprite = true;
            playerSprite.setSize(Width/30,Height/15);
        }
    }

    int getPlayer(){
        return color;
    }

    void foundCard() {
        if (!hand.empty()) {
            hand.pop();
        }
        if (!hand.empty()) {
            hand.peek().flipCard();
        }
    }

    public int lookingFor(){
        if (!hand.empty()) {
            Card card = showCard();
            return card.getTreasureId();
        }
        return color;
    }

    Card showCard(){
        return hand.peek();
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

    @Override
    public String toString(){
        return ("Playernum: " + color);
    }
}

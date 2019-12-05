package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;

public class Card {
    private int treasureId;
    private Texture cardFace = Treasures.getCardTextures(0)[0];
    private Texture cardTreasure = Treasures.getCardTextures(0)[0];
    private Texture cardBack = Treasures.getCardTextures(0)[1];
    private boolean flipped = false;
    private Texture treasurePng;
    private Texture blank = Treasures.getBlankTexture();
    private Sprite thisCard;
    private Sprite thisTreasure;
    private int[] cardPos;
    private int[] treasurePos;

    public Card(int treasure, int treasuresType){

        treasureId = treasure;
        if(treasureId != -1) {
            treasurePng = Treasures.getTreasure(treasureId, treasuresType);
        }else{
            treasurePng = Treasures.getBlankTexture();
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(Width/16,Height/16);
        cardPos = new int[]{0,0};
        thisCard.setPosition(cardPos[0], cardPos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(Width/32,Height/32);
        findTreasurelocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    public Card(int treasure, int treasuresType, int x, int y){

        treasureId = treasure;
        if(treasureId != -1) {
            treasurePng = Treasures.getTreasure(treasureId, treasuresType);
        }else{
            treasurePng = Treasures.getBlankTexture();
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(Width/16,Height/16);
        cardPos = new int[]{x,y};
        thisCard.setPosition(cardPos[0], cardPos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(Width/32,Height/32);
        findTreasurelocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    private void findTreasurelocation(){
        treasurePos = new int[2];
        treasurePos[0] = cardPos[0] + Width/16/2 - Width/64;
        treasurePos[1] = cardPos[1] + Height/16/2 - Height/64;
    }

    void draw(SpriteBatch batch){
        thisCard.draw(batch);
        thisTreasure.draw(batch);
    }

    void setPosition(int x, int y){
        cardPos = new int[]{x,y};
        thisCard.setPosition(cardPos[0], cardPos[1]);
        findTreasurelocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    int getTreasureId(){
        return treasureId;
    }

    void flipCard(){
        if(flipped){
            cardFace = cardTreasure;
            thisCard.setTexture(cardFace);
            thisTreasure.setTexture(treasurePng);
            flipped = false;
        }else{
            cardFace = cardBack;
            thisCard.setTexture(cardFace);
            thisTreasure.setTexture(blank);
            flipped = true;

        }
    }

}

package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Card {
    private int treasureId;
    private Texture cardFace = new Texture(Gdx.files.internal("Card_Face.png"));
    private Texture cardBack = new Texture(Gdx.files.internal("Card_Back.png"));
    private Texture treasurePng;
    private Sprite thisCard;
    private Sprite thisTreasure;
    private int[] cardPos;
    private int[] treasurePos;

    public Card(int treasure, int treasuresType){

        treasureId = treasure;
        if(treasureId != -1) {
            String[] namesDict = Treasures.getTreasureDict(treasuresType);
            treasurePng = new Texture(Gdx.files.internal("Icon_"+namesDict[treasureId]+".png"));
        }else{
            treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(64,64);
        cardPos = new int[]{0,0};
        thisCard.setPosition(cardPos[0], cardPos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);
        findTreasurelocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    public Card(int treasure, int treasuresType, int x, int y){

        treasureId = treasure;
        if(treasureId != -1) {
            String[] namesDict = Treasures.getTreasureDict(treasuresType);
            treasurePng = new Texture(Gdx.files.internal("Icon_"+namesDict[treasureId]+".png"));
        }else{
            treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(64,64);
        cardPos = new int[]{x,y};
        thisCard.setPosition(cardPos[0], cardPos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);
        findTreasurelocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    private void findTreasurelocation(){
        treasurePos = new int[2];
        treasurePos[0] = cardPos[0] + 64/2 - 16;
        treasurePos[1] = cardPos[1] + 64/2 - 16;
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

    void flipCard(){
        //Function WIP for Gen X Deliverable
    }

}

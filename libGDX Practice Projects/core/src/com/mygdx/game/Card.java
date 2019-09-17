package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Card {

    private Texture cardFace;
    private Texture cardBack;
    private Texture treasurePng;
    private int treasureId;
    private Rectangle cardLoc;
    private Rectangle treasureLoc;
    private Sprite thisCard;
    private Sprite thisTreasure;

    public Card(int treasureNum){

        treasureId = treasureNum;
        makeTreasureDict();

        cardLoc = new Rectangle();
        cardLoc.x = 0;
        cardLoc.y = 0;
        cardLoc.width = 64;
        cardLoc.height = 64;
        findTreasureLocation();

        cardFace = new Texture(Gdx.files.internal("Card_Face.png"));

        if(treasureNum > 0){
            String[] namesDict = makeTreasureDict();
            treasurePng = new Texture(Gdx.files.internal("Icon_"+namesDict[treasureNum]+".png"));
        }else{
            treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(64,64);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);

    }

    public Card(int treasureNum, int x, int y){

        treasureId = treasureNum;
        makeTreasureDict();

        cardLoc = new Rectangle();
        cardLoc.x = x;
        cardLoc.y = y;
        cardLoc.width = 64;
        cardLoc.height = 64;
        findTreasureLocation();

        cardFace = new Texture(Gdx.files.internal("Card_Face.png"));

        if(treasureNum > 0){
            String[] namesDict = makeTreasureDict();
            treasurePng = new Texture(Gdx.files.internal("Icon_"+namesDict[treasureNum]+".png"));
        }else{
            treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
        }

        thisCard = new Sprite(cardFace);
        thisCard.setSize(64,64);
        thisCard.setPosition(cardLoc.x,cardLoc.y);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);
        thisTreasure.setPosition(treasureLoc.x,treasureLoc.y);

    }

    public void draw(SpriteBatch bat){
        thisCard.draw(bat);
        thisTreasure.draw(bat);
    }

    private String[] makeTreasureDict(){

        return new String[]{
                "",
                "Bat",
                "Beetle",
                "Candlestick",
                "Coins",
                "Crown",
                "Dinosaur",
                "Gem",
                "Genie",
                "Ghost",
                "Helmet",
                "Hobgoblin",
                "Key",
                "Lizard",
                "Map",
                "Moth",
                "Owl",
                "Princess",
                "Rat",
                "Ring",
                "Skull",
                "Spider",
                "Sword",
                "Tome",
                "TreasureChest"
        };

    }

    private void findTreasureLocation(){
        treasureLoc = new Rectangle();
        treasureLoc.x = cardLoc.x + cardLoc.width/2 - 16;
        treasureLoc.y = cardLoc.y + cardLoc.width/2 - 16;
        treasureLoc.width = 32;
        treasureLoc.height = 32;
    }

    public Rectangle getCardLoc(){
        return cardLoc;
    }

    public void setNewPosition(int x, int y){
        thisCard.setPosition(x,y);
        cardLoc.x = x;
        cardLoc.y = y;
        findTreasureLocation();
    }

    public Rectangle getTreasurePosition(){
        return treasureLoc;
    }

    public void flipCard(){
        //WIP
    }

    public Sprite getCardSprite(){
        return thisCard;
    }

    public Sprite getTreasureSprite(){
        return thisTreasure;
    }

    public Texture getTreasurePng(){
        return treasurePng;
    }

    public int getTreasureId() {
        return treasureId;
    }

}

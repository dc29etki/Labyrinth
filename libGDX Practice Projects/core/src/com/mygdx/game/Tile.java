package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class Tile {

    private Texture tilePng;
    private Texture treasurePng;
    private Rectangle locationRect;
    private Rectangle treasureRect;
    private boolean connectTop;
    private boolean connectRight;
    private boolean connectBottom;
    private boolean connectLeft;
    private int[] arrayPos;
    private int treasureId;
    private int facingDir;
    private Sprite thisTile;
    private Sprite thisTreasure;

    public Tile(int tileType, int treasureNum, int xPos, int yPos, int dir){
    //tileType: 0 = Corner, 1 = Straight, 2 = Intersection; treasureNum = treasure (-1 = none); xPos = column number (in board); yPos = row number (in board); dir = direction tile default top is facing (0 = up, 1 = right, 2 = down, 3 = left)

        if(tileType == 0){
            connectTop = false;
            connectRight = true;
            connectBottom = true;
            connectLeft = false;
            tilePng = new Texture(Gdx.files.internal("Tile_23.png"));
        }else if(tileType == 1){
            connectTop = false;
            connectRight = true;
            connectBottom = false;
            connectLeft = true;
            tilePng = new Texture(Gdx.files.internal("Tile_13.png"));
        }else{
            connectTop = true;
            connectRight = true;
            connectBottom = false;
            connectLeft = true;
            tilePng = new Texture(Gdx.files.internal("Tile_234.png"));
        }


        treasureId = treasureNum;
        arrayPos = new int[] {xPos,yPos};
        facingDir = dir;
        for(int i = 0; i < facingDir; i++){
            thisTile.rotate90(true);
        }

        findPlaceLocation();

        findTreasureLocation();

        if(treasureNum > -1){
            String[] namesDict = makeTreasureDict();
            treasurePng = new Texture(Gdx.files.internal("Icon_"+namesDict[treasureNum]+".png"));
        }else if(treasureNum < -1){
            if(treasureNum == -2){//Red Circle
                treasurePng = new Texture(Gdx.files.internal("Icon_" + "RedCircle" + ".png"));
            }else if(treasureNum == -3){//Blue Circle
                treasurePng = new Texture(Gdx.files.internal("Icon_" + "BlueCircle" + ".png"));
            }else if(treasureNum == -4){//Yellow Circle
                treasurePng = new Texture(Gdx.files.internal("Icon_" + "YellowCircle" + ".png"));
            }else if(treasureNum == -5) {//Green Circle
                treasurePng = new Texture(Gdx.files.internal("Icon_" + "GreenCircle" + ".png"));
            }else{
                treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
            }
        }else{
            treasurePng = new Texture(Gdx.files.internal("Blank_Icon.png"));
        }

        thisTile = new Sprite(tilePng);
        thisTile.setSize(128,128);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);

    }

    private String[] makeTreasureDict(){

        return new String[]{
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

    private void findPlaceLocation(){
        locationRect = new Rectangle();
        locationRect.x = 10;
        locationRect.y = 10;
        locationRect.width = 85;
        locationRect.height = 85;
        for(int i = 0; i <= arrayPos[0]; i++){
            locationRect.x += 87;
        }
        for(int i = 0; i < arrayPos[1]; i++){
            locationRect.y += 87;
        }
    }
    private void findTreasureLocation(){
        treasureRect = new Rectangle();
        treasureRect.x = locationRect.x + 48;
        treasureRect.y = locationRect.y + 48;
        treasureRect.width = 32;
        treasureRect.height = 32;
    }

    public void rotate(int dir){
        //Dir = 1 if clockwise, -1 if counterclockwise
        boolean topPrev = connectTop;

        if(dir == 1) {
            thisTile.rotate90(true);
            connectTop = connectLeft;
            connectLeft = connectBottom;
            connectBottom = connectRight;
            connectRight = topPrev;
            facingDir = facingDir + dir;
            if(facingDir == 4){
                facingDir = 0;
            }
        }else if(dir == -1) {
            thisTile.rotate90(false);
            connectTop = connectRight;
            connectRight = connectBottom;
            connectBottom = connectLeft;
            connectLeft = topPrev;
            facingDir = facingDir + dir;
            if(facingDir == -1){
                facingDir = 3;
            }
        }
    }

    public int[] getConnectSides(){

        int up = 0;
        int right = 0;
        int down = 0;
        int left = 0;

        if(connectTop == true){
            up = 1;
        }
        if(connectRight == true){
            right = 1;
        }
        if(connectBottom == true){
            down = 1;
        }
        if(connectLeft == true){
            left = 1;
        }

        return new int[]{up,right,down,left};

    }

    public Rectangle getTilePosition(){
        return locationRect;
    }

    public void setNewPosition(int x, int y){
        thisTile.setPosition(x,y);
        locationRect.x = x;
        locationRect.y = y;
        findTreasureLocation();
    }

    public Rectangle getTreasurePosition(){
        return treasureRect;
    }

    public Texture getTilePng(){
        return tilePng;
    }

    public Sprite getTileSprite(){
        return thisTile;
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

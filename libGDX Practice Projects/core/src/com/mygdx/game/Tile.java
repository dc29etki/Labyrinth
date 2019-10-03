package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Tile {
    private int[] connections;
    private Texture tilePng;
    private int treasureId;
    private int[] arrayPos;
    private Texture treasurePng;
    private Sprite thisTile;
    private Sprite thisTreasure;
    private int[] tilePos = new int[2];
    private int[] treasurePos = new int[2];

    public Tile(int tileType, int treasureNum, int xPos, int yPos, int dir, int treasuresType){

        //Connections: Top,Right,Bottom,Left
        if(tileType == 0){//Corner Piece
            connections = new int[]{0,1,1,0};
            tilePng = new Texture(Gdx.files.internal("Tile_23.png"));
        }else if(tileType == 1){//Straight Piece
            connections = new int[]{0,1,0,1};
            tilePng = new Texture(Gdx.files.internal("Tile_13.png"));
        }else {//T-Piece
            connections = new int[]{1,1,0,1};
            tilePng = new Texture(Gdx.files.internal("Tile_234.png"));
        }

        arrayPos = new int[]{xPos,yPos};

        treasureId = treasureNum;
        String[] treasureDict = Treasures.getTreasureDict(treasuresType);

        findPlaceLocation();
        findTreasureLocation();

        if(treasureId > -1){
            treasurePng = new Texture(Gdx.files.internal("Icon_" + treasureDict[treasureId]+".png"));
        }else if(treasureId < -1){
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
        thisTile.setPosition(tilePos[0], tilePos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(32,32);
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);

        //Rotate tile dir times
        for(int i = 0; i < dir; i++){
            rotate(1);
        }

    }

    void rotate(int dir){
        //dir = 1 if clockwise, dir = -1 if counterclockwise. dir = 0 does nothing.
        int zedPrev = connections[0];
        if(dir == 1){
            thisTile.rotate90(true);
            connections[0] = connections[3];
            connections[3] = connections[2];
            connections[2] = connections[1];
            connections[1] = zedPrev;
        }else if(dir == -1){
            thisTile.rotate90(false);
            connections[0] = connections[1];
            connections[1] = connections[2];
            connections[2] = connections[3];
            connections[3] = zedPrev;
        }
    }

    private void findPlaceLocation(){
        int x = 0;
        int y = 800-132;
        for(int i = 0; i <= arrayPos[0]; i++){
            x += 129;
        }
        for(int i = 0; i < arrayPos[1]; i++){
            y -= 129;
        }
        tilePos = new int[]{x,y};
    }

    private void findTreasureLocation(){
        treasurePos[0] = tilePos[0] + 48;
        treasurePos[1] = tilePos[1] + 48;
    }

    void setNewPosition(int x, int y){
        thisTile.setPosition(x,y);
        tilePos = new int[]{x,y,};
        findTreasureLocation();
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    void setBoardPosition(int x, int y){
        arrayPos = new int[]{x,y};
        findPlaceLocation();
        findTreasureLocation();
        thisTile.setPosition(tilePos[0], tilePos[1]);
        thisTreasure.setPosition(treasurePos[0],treasurePos[1]);
    }

    int[] getConnectSides(){
        return connections;
    }

    int getTreasureId(){
        return treasureId;
    }

    void draw(SpriteBatch batch){
        thisTile.draw(batch);
        thisTreasure.draw(batch);
    }

}

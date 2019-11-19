package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;


public class Tile {
    private int[] connections;
    private Texture tilePng;
    private Texture tileMovePng;
    private boolean isShowingMove = false;
    private int treasureId;
    private int[] arrayPos;
    private Texture treasurePng;
    private Sprite thisTile;
    private Sprite thisTreasure;
    private int[] tilePos = new int[2];
    private int[] treasurePos = new int[2];

    public Tile(int tileType, int treasureNum, int xPos, int yPos, int dir, int treasuresType){
        //Connections: Top,Right,Bottom,Left
        Texture[] tilePngs = Treasures.getTileSprites(treasuresType);
        if(tileType == 0){//Corner Piece
            connections = new int[]{0,1,1,0};
            tilePng = tilePngs[0];
            tileMovePng = tilePngs[3];
        }else if(tileType == 1){//Straight Piece
            connections = new int[]{1,0,1,0};
            tilePng = tilePngs[1];
            tileMovePng = tilePngs[4];
        }else {//T-Piece
            connections = new int[]{0,1,1,1};
            tilePng = tilePngs[2];
            tileMovePng = tilePngs[5];
        }

        arrayPos = new int[]{xPos,yPos};

        treasureId = treasureNum;

        findPlaceLocation();
        findTreasureLocation();

        if(treasureId > -1){
            treasurePng = Treasures.getTreasure(treasureId,treasuresType);
        }else if(treasureId < -1){
            if(treasureNum == -2){//Red Circle
                treasurePng = Treasures.getPlayerTextures(treasuresType)[0];
            }else if(treasureNum == -3){//Blue Circle
                treasurePng = Treasures.getPlayerTextures(treasuresType)[1];
            }else if(treasureNum == -4){//Yellow Circle
                treasurePng = Treasures.getPlayerTextures(treasuresType)[2];
            }else if(treasureNum == -5) {//Green Circle
                treasurePng = Treasures.getPlayerTextures(treasuresType)[3];
            }else{
                treasurePng = Treasures.getBlankTexture();
            }
        }else{
            treasurePng = Treasures.getBlankTexture();
        }

        thisTile = new Sprite(tilePng);
        thisTile.setSize(Width/10,Height/10);
        thisTile.setPosition(tilePos[0], tilePos[1]);
        thisTreasure = new Sprite(treasurePng);
        thisTreasure.setSize(Width/30,Height/30);
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
        int y = Height-(Height/5);
        for(int i = 0; i <= arrayPos[0]; i++){
            x += Width/10 + 1;
        }
        for(int i = 0; i < arrayPos[1]; i++){
            y -= Height/10 + 1;
        }

        if(arrayPos[0] == -1){
            x = 9*(Width/10 + 1);
            y = Height - 9*(Height/10 + 1);
        }
        tilePos = new int[]{x,y};
    }

    private void findTreasureLocation(){
        treasurePos[0] = tilePos[0] + Width/30;
        treasurePos[1] = tilePos[1] + Width/30;
    }

    void setNewPosition(int x, int y){
        thisTile.setPosition(x,y);
        tilePos = new int[]{x,y};
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

    int[] getSpritePosition(){
        return tilePos;
    }
    int[] getTilePosition(){
        return arrayPos;
    }

    void showLine() {
        if(!isShowingMove){
            thisTile.setTexture(tileMovePng);
            isShowingMove = true;
        }
    }

    void hideLine(){
        if(isShowingMove){
            thisTile.setTexture(tilePng);
            isShowingMove = false;
        }
    }

    @Override
    public String toString(){
        return "Treasure: " + treasureId + ", Board Position: (" + arrayPos[0] + "," + arrayPos[1] + "), Tile Connected Sides: " + connections[0] + connections[1] + connections[2] + connections[3];
    }
}

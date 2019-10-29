package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Deck deck;
    private Tile[] tiles;
    private Tile[] tiles2;
    private Tile[][] grid;
    private Tile extra;
    private Tile[][] temp1;
    public Board(){
        deck = new Deck(1,0);
        tiles = deck.getTileDeck();
        tiles2 = new Deck(1, 0).getTileDeck();
        grid = new Tile[7][7];
        int count = 0;
        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                grid[i][j] = tiles[count];
                count++;
            }
        }
        extra = tiles[49];
        temp1 = new Tile[7][7];
        temp1 = grid;
    }

    public Tile[][] getBoard(){
        return grid;
    }

    public Tile getExtraTile(){
        return extra;
    }

    public void draw(SpriteBatch batch){
        for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                grid[i][j].setBoardPosition(i, j);
                grid[i][j].draw(batch);
            }
        }
        extra.draw(batch);
    }
    public void insertTile(int x, int y){
        if(x==0){
            Tile[] temp = new Tile[7];
            for(int i=0; i<7; i++){
                temp[i] = grid[i][y];
            }
            grid[x][y] = extra;
            for(int j=1; j<7; j++){
                grid[j][y] = temp[j-1];
            }
            extra = temp[temp.length-1];
        }
        else if(y==0){
            Tile[] temp = new Tile[7];
            for(int i=0; i<7; i++){
                temp[i] = grid[x][i];
            }
            grid[x][y] = extra;
            for(int j=1; j<7; j++){
                grid[x][j] = temp[j-1];
            }
            extra = temp[temp.length-1];
        }
    }
}
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Deck deck;
    private Tile[] tiles;
    private Tile[][] grid;
    private Tile extra;
    public Board(){
        deck = new Deck(1,0);
        tiles = deck.getTileDeck();
        grid = new Tile[7][7];
        int count = 0;
        for(int i=0; i<7; i++){
            for(int j=0; j<7; j++){
                grid[i][j] = tiles[count];
                count++;
            }
        }
        extra = tiles[count];
        extra.setBoardPosition(-1,-1);
    }

    public void draw(SpriteBatch batch){
        for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                grid[i][j].draw(batch);
            }
        }
        extra.draw(batch);
    }
    public void insertTile(int x, int y){
        if(x==0){
            Tile temp = grid[0][y];
            grid[0][y] = extra;
            for(int i=0; i<grid[x].length-1; i++){
                temp = grid[i][y];
                grid[i+1][y] = temp;
            }
            extra = grid[grid[x].length][y];
        }
        else if(y==0){
            Tile temp = grid[x][0];
            grid[x][0] = extra;
            for(int i=0; i<grid[y].length-1; i++){
                temp = grid[x][i];
                grid[x][i+1] = temp;
            }
            extra = grid[x][grid[y].length];
        }
    }
}
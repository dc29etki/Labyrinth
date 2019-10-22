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
    }

    public void draw(SpriteBatch batch){
        for (int i=0; i<7; i++){
            for (int j=0; j<7; j++){
                grid[i][j].draw(batch);
            }
        }

    }
    public void insertTile(int x, int y, SpriteBatch batch){
        if(x==0){
            /*
            Tile[][] temp = grid;
            for(int i=5; i>0; i--){
                grid[y][i+1] = grid[y][i];
            }
            grid[x][y] = extra;
            extra = temp[x][y];
            */
            grid[x][y] = grid[x+1][y];
            draw(batch);
        }
        else if(y==0){

        }
    }
}
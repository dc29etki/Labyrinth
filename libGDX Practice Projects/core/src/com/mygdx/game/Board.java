package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Tile[][] grid;
    private Tile extra;
    public Board(){
        grid = createGrid(7,7);
        extra = new Tile(new Random.nextInt(2),0,0,0,0);
    }
    public Board(int x, int y){
        grid = createGrid(x,y);
        extra = new Tile(new Random.nextInt(2),0,0,0,0);
    }
    public Tile[][] createGrid(int x, int y){
        Tile[][] grid = new Tile[x][y];
        Random rand = new Random();
        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                int tileDir = rand.nextInt(2);
                int xPos = rand.nextInt(x);
                int yPos = rand.nextInt(y);
                grid[j][i] = new Tile(tileDir,0,xPos,yPos,0);
            }
        }
        return grid;
    }
    public Tile[][] getBoard() {
        return grid;
    }
    public Tile getExtraTile(){
        return extra;
    }
    public void draw(SpriteBatch batch){
        for (int i=0; i<grid.length; i++){
            for (int j=0; j<grid.length; j++){
                Tile tile = this.getBoard() [i][j];
                Texture tex = tile.getTilePng();
                batch.draw(tex, 100*i, 100*j);
            }
        }
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
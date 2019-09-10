package com.mygdx.game;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Tile[][] grid;
    public Board(){
        grid = createGrid(7,7);
    }
    public Board(int x, int y){
        grid = createGrid(x,y);
    }
    public Tile[][] createGrid(int x, int y){
        Tile[][] grid = new Tile[x][y];
        Random rand = new Random();
        for(int i=0; i<x; i++){
            for(int j=0; j<y; i++){
                int tileDir = rand.nextInt(2);
                int xPos = rand.nextInt(x);
                int yPos = rand.nextInt(y);
                grid[i][j] = new Tile(tileDir,0,xPos,yPos);
            }
        }
        return grid;
    }
    public Tile[][] getGrid() {
        return grid;
    }
}

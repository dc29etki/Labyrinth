package com.mygdx.game;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Tile[][] grid;
    public Board(){
        grid = createGrid(12,12);
    }
    public Board(int x, int y){
        grid = createGrid(x,y);
    }
    public Tile[][] createGrid(int x, int y){
        Tile[][] grid = new Tile[x][y];
        Random rand = new Random();
        for(int i=0; i<10; i++){
            for(int j=i; j<10; i++){
                int tileDir = rand.nextInt(2);
                int xPos = rand.nextInt(x);
                int yPos = rand.nextInt(y);
                grid[j][i] = new Tile(tileDir,0,xPos,yPos,0);
                System.out.println(i+"  "+j);
            }
        }
        return grid;
    }
    public Tile[][] getBoard() {
        return grid;
    }
}

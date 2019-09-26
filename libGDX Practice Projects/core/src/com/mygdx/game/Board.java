package com.mygdx.game;

import java.util.Arrays;
import java.util.Random;

public class Board {
    private Tile[][] grid;
    private Tile extraTile;
    private Deck boardDeck;
    public Board(){
        boardDeck = new Deck(1);
        grid = createGrid(7,7);
        extraTile = boardDeck.dealTile();
    }
    public Board(int x, int y){
        boardDeck = new Deck(1);
        grid = createGrid(x,y);
        extraTile = boardDeck.dealTile();
    }
    public Tile[][] createGrid(int x, int y){
        Tile[][] grid = new Tile[x][y];
        Random rand = new Random();
        for(int i=0; i<x; i++){
            for(int j=0; j<y; j++){
                grid[j][i] = boardDeck.dealTile();
            }
        }
        return grid;
    }
    public Tile[][] getBoard() {
        return grid;
    }
    public Tile getExtraTile() { return extraTile; }
}

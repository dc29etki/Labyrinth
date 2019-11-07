package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;
import java.util.Stack;

public class GameRunner {

    Player[] players;
    Board board;
    int whichTurn;
    SpriteBatch batch;

    public GameRunner(Board thisGame, SpriteBatch pass){
        board = thisGame;
        batch = pass;
        players = new Player[4];
        players[0] = new Player(-2);
        players[1] = new Player(-3);
        players[2] = new Player(-4);
        players[3] = new Player(-5);
        whichTurn = 0;
        players[whichTurn].setMyTurn(true);
        players[whichTurn].swapSprite();
    }

    public Player[] getPlayers(){
        return players;
    }

    public void nextTurn(){
        players[whichTurn].setMyTurn(false);
        players[whichTurn].swapSprite();
        whichTurn ++;
        if(whichTurn >= players.length){
            whichTurn = 0;
        }
        players[whichTurn].setMyTurn(true);
        players[whichTurn].swapSprite();
    }

    public int[][] getBoardPositions(){
        int[][] positions = new int[players.length][];
        for(int i = 0; i < players.length; i++){
            positions[i] = players[i].getBoardPosition();
        }
        return positions;
    }

    public void movePlayerTo(int play, int x, int y){
        players[play].setBoardPosition(x,y);
    }

    public void network(){
        //WIP
    }

    public void print(){
        for(Player player : players){
            player.draw(batch);
        }
    }

    public Tile[] tilePaths(Tile start){
        List connected = null;
        connected.add(start);
        Stack returns = null;
        Tile nextTile;
        int[] sides = start.getConnectSides();
        for(int s : sides){
            nextTile = findNextTile(start, s);
            if(nextTile != null) {
                returns.add(nextTile);
                returns.addAll( getConnectedTiles(nextTile, s, returns) );
            }
        }
        int tiles = returns.size();
        Tile[] paths = new Tile[tiles];
        for(int i = 0; i < tiles; i++){
            paths[i] = (Tile)returns.pop();
        }
        return paths;
    }

    private Tile findNextTile (Tile start, int side){
        //Return the tile NEXT TO the side of start's listed side, IF that tile's same side is open.
        int x = start.getTilePosition()[0];
        int y = start.getTilePosition()[1];

        switch(side) {
            case 1:
                x += 1;
                break;
            case 2:
                y += 1;
                break;
            case 3:
                x -= 1;
                break;
            case 0:
            default:
                y -= 1;
        }

        if(x < 0 || x > 6 || y < 0 || y > 6) {
            return null;
        }else{
            Tile nextTo = board.getBoard()[x][y];
            int[] opens = nextTo.getConnectSides();
            switch(side) {
                case 1:
                    if(opens[3] == 1){
                        return nextTo;
                    }
                    break;
                case 2:
                    if(opens[0] == 1){
                        return nextTo;
                    }
                    break;
                case 3:
                    if(opens[1] == 1){
                        return nextTo;
                    }
                    break;
                case 0:
                default:
                    if(opens[2] == 1) {
                        return nextTo;
                    }
            }
        }
        return null;
    }

    private Stack getConnectedTiles(Tile start, int fromDir, Stack soFar){
        //Recursively find the connected tiles
        Stack returns = soFar;
        Tile nextTile;
        int[] sides = start.getConnectSides();
        for(int s : sides){
            if(s != fromDir) {
                nextTile = findNextTile(start, s);
                if (nextTile != null && !(soFar.contains(nextTile))) {
                    returns.add(nextTile);
                    returns.addAll(getConnectedTiles(nextTile, s, returns));
                }
            }
        }
        return returns;
    }

    void draw(SpriteBatch batch){
        for(Player player : players){
            player.draw(batch);
        }
    }

}

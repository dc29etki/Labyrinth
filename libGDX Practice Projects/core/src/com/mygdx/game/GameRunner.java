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
    Tile[] movables;

    public GameRunner(Board thisGame, SpriteBatch pass){
        board = thisGame;
        batch = pass;
        players = new Player[4];
        players[0] = new Player(-2,board);//Red
        players[1] = new Player(-3,board);//Blue
        players[2] = new Player(-4,board);//Yellow
        players[3] = new Player(-5,board);//Green
        whichTurn = 0;
        players[whichTurn].setMyTurn(true);
        //players[whichTurn].swapSprite();
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

    public void setMovables(Tile[] move){
        movables = move;
    }

    public void tryMove(int playerNumber, int x, int y, MoveButtons moveButtons){
        for(int i = 0; i < movables.length; i++){
            if(movables[i].getTilePosition()[0] == x && movables[i].getTilePosition()[1] == y){
                movePlayerTo((-2)-playerNumber,x,y);
                moveButtons.disableMove();
                moveButtons.enableIns();
            }
        }
    }

    public void updateFromInsert(int x, int y){
        int horizVert = 0;//0 = Vertical, 1 = Horizontal
        int shift = 0;//Row/Column to check
        int shiftDir = 0;
        if(x == 0){
            shift = y;
            shiftDir = 1;
        }else if(x == 6){
            shift = y;
            shiftDir = -1;
        }else if(y == 0){
            horizVert = 1;
            shift = x;
            shiftDir = 1;
        }else if(y == 6){
            horizVert = 1;
            shift = x;
            shiftDir = -1;
        }

        Tile[][] brd = board.getBoard();
        if(horizVert == 0) {
                for (Player play : players) {
                    if (shift == play.getBoardPosition()[0]) {
                        int[] pos = play.boardPosition;
                        pos[1] += shiftDir;
                        if (pos[1] < 0) {
                            pos[1] = 6;
                        } else if (pos[1] > 6) {
                            pos[1] = 0;
                        }
                        System.out.println("Move To: " + pos[1] + ", " + pos[0]);
                        play.setPosition(brd[pos[1]][pos[0]]);
                    }
                }
        }else if(horizVert == 1) {
                for (Player play : players) {
                    if (shift == play.getBoardPosition()[1]) {
                        int[] pos = play.boardPosition;
                        pos[0] += shiftDir;
                        if (pos[0] < 0) {
                            pos[0] = 6;
                        } else if (pos[0] > 6) {
                            pos[0] = 0;
                        }
                        play.setPosition(brd[pos[1]][pos[0]]);
                    }
                }
        }

    }

    public Tile[] runTilePathing(Board board, int playerNumAs01){
        Tile[][] boardTileArrays = board.getBoard();
        for(int i = 0; i < boardTileArrays.length; i++){
            clearTilePaths(boardTileArrays[i]);
        }
        board.getExtraTile().hideLine();
        int[] start = getPlayers()[playerNumAs01].getBoardPosition();
        return showTilePaths(board.getBoard()[start[1]][start[0]],board);
    }

    public void movePlayerTo(int play, int x, int y){
        players[play].setPosition(board.getBoard()[y][x]);
        checkForTreasureMatch(play);
    }

    public void checkForTreasureMatch(int player){
        //WIP
    }

    public void network(){
        //WIP
    }

    public void print(){
        for(Player player : players){
            player.draw(batch);
        }
    }

    public static Tile[] showTilePaths(Tile start, Board board){
        Tile[] connected = tilePaths(start, board);
        for (int i = 0; i < connected.length; i++) {
            connected[i].showLine();
        }
        return connected;
    }

    public static void clearTilePaths(Tile[] tiles){
        for(int i = 0; i < tiles.length; i++){
            tiles[i].hideLine();
        }
    }

    public static void toggleTilePaths(Tile[] tiles){
        for(int i = 0; i < tiles.length; i++){
            tiles[i].showLine();
        }
    }

    public static Tile[] tilePaths(Tile start, Board board){
        Stack<Tile> returns = new Stack<Tile>();
        returns.add(start);
        Tile nextTile;
        int[] sides = start.getConnectSides();
        for(int i = 0; i < 4; i++){
            if(sides[i] == 1) {
                nextTile = findNextTile(start, i, board);
                if (nextTile != null) {
                    returns.add(nextTile);
                    int oppositeSide = (i+2)%4;
                    returns = getConnectedTiles(nextTile, oppositeSide, returns, board);
                }
            }
        }
        int tiles = returns.size();
        Tile[] paths = new Tile[tiles];
        for(int i = 0; i < tiles; i++){
            paths[i] = (Tile)returns.pop();
        }
        return paths;
    }

    private static Tile findNextTile (Tile start, int side, Board board){
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
            Tile nextTo = board.getBoard()[y][x];
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

    private static Stack getConnectedTiles(Tile start, int fromDir, Stack soFar, Board board){
        //Recursively find the connected tiles
        Stack returns = soFar;
        Tile nextTile;
        int[] sides = start.getConnectSides();
        for(int i = 0; i < 4; i++){
            if(sides[i] == 1 && i != fromDir) {
                nextTile = findNextTile(start, i, board);
                if (nextTile != null && !(returns.contains(nextTile))) {
                    returns.add(nextTile);
                    int oppositeSide = (i+2)%4;
                    returns = getConnectedTiles(nextTile, oppositeSide, returns, board);
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

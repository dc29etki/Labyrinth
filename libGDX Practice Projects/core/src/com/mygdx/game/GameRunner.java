package com.mygdx.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;
import java.util.Random;
import java.util.Stack;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;
import static java.lang.Thread.sleep;

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
    }

    public Player[] getPlayers(){
        return players;
    }

    public void dealCards(int treasureType){
        Deck cardDeck = new Deck(0, treasureType);
        players[0].deal(cardDeck.dealCard());
        players[0].getHand().peek().flipCard();
        players[0].getHand().peek().setPosition(9*(Width/10 + 1), Height - 6*(Height/10 + 1));
        players[0].deal(cardDeck.dealCard());
        players[0].getHand().peek().flipCard();
        players[0].getHand().peek().setPosition(9*(Width/10 + 1), Height - 4*(Height/10 + 1));
        players[0].deal(cardDeck.dealCard());
        players[0].getHand().peek().setPosition(9*(Width/10 + 1), Height - 2*(Height/10 + 1));
        for(int i = 1; i < 4; i++){
            for(int j = 0; j < 3; j++){
                players[i].deal(cardDeck.dealCard());
            }
        }
    }

    public Stack<Card> getPlayerHand(int playerNum){
        return players[-2-playerNum].getHand();
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

    public Tile[] getMovables(){
        return movables;
    }

    public boolean tryMove(int playerNumber, int x, int y, MoveButtons moveButtons){
        for(int i = 0; i < movables.length; i++){
            if(movables[i].getTilePosition()[0] == x && movables[i].getTilePosition()[1] == y){
                movePlayerTo((-2)-playerNumber,x,y);
                moveButtons.disableMove();
                runAi(moveButtons);
                return true;
            }
        }
        return false;
    }

    private void runAi(final MoveButtons moveButtons){
        //NOTE: CREATES NEW THREAD
        nextTurn();
        final Random[] random = {new Random()};

        Thread renderLoop = new Thread(new Runnable() {
            private void runWait(int time) {
                try {
                    sleep(time * 1000);
                }catch(Exception e){
                }
            }
            @Override
            public void run() {
                while (whichTurn != 0) {
                    random[0] = new Random();
                    Board board = moveButtons.board;
                    Tile extra = board.getExtraTile();
                    GameRunner runEnvironment = moveButtons.runEnvironment;
                    //Rotate Extra Tile
                    for (int i = 0; i < random[0].nextInt(4); i++) {
                        extra.rotate(1);
                        runWait(1);
                    }
                    runWait(1);

                    //Insert Extra Tile
                    int x = 0;
                    int y = 0;
                    int z = 0;
                    while (x == 0) {
                        x = random[0].nextInt(13) % 2;
                    }
                    y = random[0].nextInt(2);
                    if (y == 1) {
                        y = 6;
                    }
                    z = random[0].nextInt(2);
                    if (z == 1) {
                        z = y;
                        y = x;
                        x = z;
                    }
                    board.insertTile(x, y);
                    runEnvironment.updateFromInsert(x, y);
                    runEnvironment.setMovables(runEnvironment.runTilePathing(board, whichTurn));

                    runWait(2);
                    //Move To Connected Space
                    Tile[] connectedTiles = runEnvironment.getMovables();
                    Tile moveTo = connectedTiles[random[0].nextInt(connectedTiles.length)];
                    int treasureNum = players[whichTurn].lookingFor();
                    for (Tile tile : connectedTiles) {
                        if (tile.getTreasureId() == treasureNum) {
                            moveTo = tile;
                            break;
                        }
                    }
                    x = moveTo.getTilePosition()[0];
                    y = moveTo.getTilePosition()[1];
                    movePlayerTo(whichTurn, x, y);
                    runWait(2);
                    clearTilePaths(getMovables());
                    nextTurn();
                }
                moveButtons.enableIns();
            }
            });
        renderLoop.start();
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
        checkForTreasureMatch(play,x,y);
    }

    public void checkForTreasureMatch(int player,int x,int y){
        Tile movedToTile = board.getBoard()[y][x];
        if(movedToTile.getTreasureId() == players[player].lookingFor()){
            players[player].foundCard();
            movedToTile.treasureFound();
        }
    }

    public void network(){
        //WIP
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
        Card[] shownHand = getPlayerHand(-2).toArray(new Card[0]);
        for(Card card : shownHand){
            card.draw(batch);
        }
    }

}

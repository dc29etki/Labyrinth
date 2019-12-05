package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import static com.mygdx.game.Labyrinth.Height;
import static com.mygdx.game.Labyrinth.Width;

public class MoveButtons {

    Button[][] buttons;
    Button[] insButtons;
    Button[] rotateButtons;
    GameRunner runEnvironment;
    Board board;
    int playerNum;

    public MoveButtons(int playerNumber, Board brd, GameRunner runEnv){
        runEnvironment = runEnv;
        board = brd;
        playerNum = playerNumber;
        buttons = makeMoveButtons(playerNumber, runEnv);
        insButtons = makeInsButtons(board);
        rotateButtons = makeRotateButtons();
    }

   public Button[][] makeMoveButtons(final int playerNumber, final GameRunner runEnv){

       Button[][] moveButtons = new Button[7][7];

        for(int t = 0; t < 7; t++){
            for(int j = 0; j < 7; j++){
                moveButtons[t][j] = new ImageButton(new SpriteDrawable(new Sprite(Treasures.getBlankTexture())));
                moveButtons[t][j].setSize(Width/10,Height/10);
                int x = 0;
                int y = Height-(Height/5);
                for(int i = 0; i <= t; i++){
                    x += Width/10 + 1;
                }
                for(int i = 0; i < j; i++){
                    y -= Height/10 + 1;
                }

                if(t == -1){
                    x = 9*(Width/10 + 1);
                    y = Height - 9*(Height/10 + 1);
                }
                moveButtons[t][j].setPosition(x,y);
                final int finalT = t;
                final int finalJ = j;
                final MoveButtons finalThis = this;
                moveButtons[t][j].addListener(new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        if(runEnv.tryMove(playerNumber, finalT, finalJ, finalThis)){
                            runEnv.clearTilePaths(runEnv.getMovables());
                        }
                    }
                });
            }
        }

        return moveButtons;
   }

    public Button[][] getButtons(){
        return buttons;
    }

    public void disableMove(){
        for(Button[] row : buttons){
            for(Button button : row){
                button.setDisabled(true);
            }
        }
    }

    public void enableMove(){
        for(Button[] row : buttons){
            for(Button button : row){
                button.setDisabled(false);
            }
        }
        for(Button button : rotateButtons){
            button.setDisabled(false);
        }
    }

    public void disableIns(){
        for(Button button : insButtons) {
            button.setDisabled(true);
        }
        for(Button button : rotateButtons){
            button.setDisabled(true);
        }
    }

    public void enableIns(){
        for(Button button : insButtons) {
            button.setDisabled(false);
        }
    }

    public Button[] makeInsButtons(final Board board){

        ImageButton ins01 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins03 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins05 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowD.png")))));
        ImageButton ins10 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins30 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins50 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowR.png")))));
        ImageButton ins61 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins63 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins65 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowU.png")))));
        ImageButton ins16 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton ins36 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton ins56 = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/ArrowL.png")))));
        ImageButton[] insButtons = new ImageButton[]{ins01,ins03,ins05,ins10,ins30,ins50,ins61,ins63,ins65,ins16,ins36,ins56};
        final int[][] insLoc = new int[][]{{0,1},{0,3},{0,5},{1,0},{3,0},{5,0},{6,1},{6,3},{6,5},{1,6},{3,6},{5,6}};
        for(int i = 0; i < insButtons.length; i++){
            insButtons[i].setSize(64,64);
            if(insLoc[i][0] == 0){
                insButtons[i].setPosition((insLoc[i][1]+1) * (Width / 10 + 1) + 16, Height - Height/5 + 112);
            }else if(insLoc[i][1] == 0){
                insButtons[i].setPosition(32, Height - (insLoc[i][0]+2) * (Height / 10 + 1) + 16);
            }else if(insLoc[i][0] == 6){
                insButtons[i].setPosition((insLoc[i][1]+1) * (Width / 10 + 1) + 16, Height - 9 * (Height / 10 + 1) + 32);
            }else if(insLoc[i][1] == 6) {
                insButtons[i].setPosition(8 * (Width / 10 + 1), Height - (insLoc[i][0]+2) * (Height / 10 + 1) + 16);
            }
            final int thisPos = i;
            insButtons[i].addListener(new ChangeListener() {
                @Override
                public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                    board.insertTile(insLoc[thisPos][0], insLoc[thisPos][1]);
                    runEnvironment.updateFromInsert(insLoc[thisPos][0], insLoc[thisPos][1]);
                    runEnvironment.setMovables(runEnvironment.runTilePathing(board, (-2)-playerNum));
                    runEnvironment.playInsertSound();
                    disableIns();
                    runEnvironment.updateText(1);
                    enableMove();
                }
            });
        }
        return insButtons;
    }

    public Button[] makeRotateButtons() {
        Button[] buttons = new Button[2];
        ImageButton rotateClk = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/BackArrow.png")))));
        rotateClk.setPosition(9*(Width/10 + 1) - 32, Height - 8*(Height/10 + 1)-32);
        rotateClk.setSize(64,64);
        rotateClk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                board.getExtraTile().rotate(1);
                runEnvironment.playRotateSound();
            }
        });
        buttons[0] = rotateClk;
        ImageButton rotateCClk = new ImageButton(new TextureRegionDrawable(new TextureRegion(new Texture(Gdx.files.internal("Game rule images/BackArrowF.png")))));
        rotateCClk.setPosition(10*(Width/10 + 1) - 32, Height - 8*(Height/10 + 1)-32);
        rotateCClk.setSize(64,64);
        rotateCClk.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                board.getExtraTile().rotate(-1);
                runEnvironment.playRotateSound();
            }
        });
        buttons[1] = rotateCClk;
        return buttons;
    }

    public Button[] getRotateButtons() {
        return rotateButtons;
    }

    public Button[] getInsButtons() {
        return insButtons;
    }
}

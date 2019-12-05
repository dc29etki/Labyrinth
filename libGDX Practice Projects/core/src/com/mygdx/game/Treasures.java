package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

public class Treasures {

    public static Texture getTreasure(int id, int set){

        if(true){//(set == 0){
            String[] treasureDict = new String[]{
                    "Bat",
                    "Beetle",
                    "Candlestick",
                    "Coins",
                    "Crown",
                    "Dinosaur",
                    "Gem",
                    "Genie",
                    "Ghost",
                    "Helmet",
                    "Hobgoblin",
                    "Key",
                    "Lizard",
                    "Map",
                    "Moth",
                    "Owl",
                    "Princess",
                    "Rat",
                    "Ring",
                    "Skull",
                    "Spider",
                    "Sword",
                    "Tome",
                    "TreasureChest"
            };
            return new Texture(Gdx.files.internal("Icon_" + treasureDict[id]+".png"));
        }
        return null;
    }

    public static String[] getTreasureDict(int set){

        if(true){//(set == 0){
            String[] treasureDict = new String[]{
                    "Bat",
                    "Beetle",
                    "Candlestick",
                    "Coins",
                    "Crown",
                    "Dinosaur",
                    "Gem",
                    "Genie",
                    "Ghost",
                    "Helmet",
                    "Hobgoblin",
                    "Key",
                    "Lizard",
                    "Map",
                    "Moth",
                    "Owl",
                    "Princess",
                    "Rat",
                    "Ring",
                    "Skull",
                    "Spider",
                    "Sword",
                    "Tome",
                    "TreasureChest"
            };
            return treasureDict;
        }
        return null;
    }

    public static Texture[] getTileSprites(int style){
        //if(style == 0){
        if(true) {
            return new Texture[]{
                    new Texture(Gdx.files.internal("Tile_23.png")),
                    new Texture(Gdx.files.internal("Tile_13.png")),
                    new Texture(Gdx.files.internal("Tile_234.png")),
                    new Texture(Gdx.files.internal("Tile_230.png")),
                    new Texture(Gdx.files.internal("Tile_130.png")),
                    new Texture(Gdx.files.internal("Tile_2340.png"))
            };
        }
        return null;
    }

    public static Sound[] getSoundEffects(){
        Sound[] sounds = new Sound[3];
        sounds[0] = Gdx.audio.newSound(Gdx.files.internal("Music/Collect Treasure/Collect treasure.wav"));
        sounds[1] = Gdx.audio.newSound(Gdx.files.internal("Music/Move Tile/stone_rock_or_wood_moved.mp3"));
        sounds[2] = Gdx.audio.newSound(Gdx.files.internal("Music/Rotate Tile/sfx_push_boulder.mp3"));
        return sounds;
    }

    public static Texture getBlankTexture(){
        return new Texture(Gdx.files.internal("Blank_Icon.png"));
    }

    public static Texture[] getCardTextures(int style) {
        //if(style == 0){
        if (true) {
            return new Texture[]{
                    new Texture(Gdx.files.internal("Card_Face.png")),
                    new Texture(Gdx.files.internal("Card_Back.png"))
            };
        }
        return null;
    }

    public static Texture[] getPlayerTextures(int style){
        //if(style == 0){
        if(true) {
            return new Texture[]{
                    new Texture(Gdx.files.internal("Icon_" + "RedCircle" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "BlueCircle" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "YellowCircle" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "GreenCircle" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "RedMark" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "BlueMark" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "YellowMark" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "GreenMark" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "RedPlayer" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "BluePlayer" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "YellowPlayer" + ".png")),
                    new Texture(Gdx.files.internal("Icon_" + "GreenPlayer" + ".png"))
            };
        }
        return null;
    }

    public static Music[] getMusic(){
        Music[] musics = new Music[2];
        musics [0] = Gdx.audio.newMusic(Gdx.files.internal("Music\\Main Menu\\Main Menu.wav"));
        musics [1] = Gdx.audio.newMusic(Gdx.files.internal("Music\\In game\\Ingame 3.OGG"));

        return musics;
    }

}

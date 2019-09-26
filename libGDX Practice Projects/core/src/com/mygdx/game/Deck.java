package com.mygdx.game;

import java.util.*;

public class Deck {

    private String[] treasureDict;
    private Card[] cardDeck;
    private int cardPoint = -1;
    private int tilePoint = -1;
    private Tile[] tileDeck;
    private int[] treasureMaker;
    private List hardPlaced;
    private Stack treasurePlacer;

    public Deck(int tilesType){

           treasureDict = makeTreasureDict();
           cardDeck = new Card[treasureDict.length];

           for(int i = 0; i < treasureDict.length; i++){
                cardDeck[i] = new Card(i);
           }

           Random intGen = new Random();
           for(int i = 0; i < intGen.nextInt(25); i++){
               Collections.shuffle(Arrays.asList(cardDeck));
           }

           tileDeck = new Tile[50];

           treasureMaker = new int[treasureDict.length];
           for(int i = 0; i < treasureDict.length; i++){
               treasureMaker[i] = i;
           }
           intGen = new Random();
           for(int i = 0; i < intGen.nextInt(25); i++){
               Collections.shuffle(Arrays.asList(treasureMaker));
           }
           treasurePlacer = new Stack();
           for(int treasure : treasureMaker) {
               treasurePlacer.add(treasure);
           }

        if(tilesType == 0){

               tileDeck [0] = new Tile(0, -2,0,0,0);
               tileDeck [6] = new Tile(0, -3,6,0,1);
               tileDeck [42] = new Tile(0, -4,0,6,3);
               tileDeck [48] = new Tile(0, -5,6,6,2);
               hardPlaced = Arrays.asList(0,6,42,48);

        }else if(tilesType == 1){

               tileDeck [0] = new Tile(0, -2,0,0,0);
               tileDeck [6] = new Tile(0, -3,6,0,1);
               tileDeck [42] = new Tile(0, -4,0,6,3);
               tileDeck [48] = new Tile(0, -5,6,6,2);

               tileDeck [2] = new Tile(2, (Integer) treasurePlacer.pop(),2,0,2);
               tileDeck [4] = new Tile(2, (Integer) treasurePlacer.pop(),4,0,2);

               tileDeck [14] = new Tile(2, (Integer) treasurePlacer.pop(),0,2,1);
               tileDeck [16] = new Tile(2, (Integer) treasurePlacer.pop(),2,2,1);
               tileDeck [18] = new Tile(2, (Integer) treasurePlacer.pop(),4,2,2);
               tileDeck [20] = new Tile(2, (Integer) treasurePlacer.pop(),6,2,3);

               tileDeck [28] = new Tile(2, (Integer) treasurePlacer.pop(),0,4,1);
               tileDeck [30] = new Tile(2, (Integer) treasurePlacer.pop(),2,4,0);
               tileDeck [32] = new Tile(2, (Integer) treasurePlacer.pop(),4,4,3);
               tileDeck [34] = new Tile(2, (Integer) treasurePlacer.pop(),6,4,3);

               tileDeck [44] = new Tile(2, (Integer) treasurePlacer.pop(),2,6,0);
               tileDeck [46] = new Tile(2, (Integer) treasurePlacer.pop(),4,6,0);

               hardPlaced = Arrays.asList(0,6,42,48,2,4,14,16,18,20,28,30,32,34,44,46);

        }

        intGen = new Random();
        Tile[] cardArray = new Tile[50-hardPlaced.size()];
        //L-pieces WITH treasures
        cardArray[0] = new Tile(0,  (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        cardArray[1] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        cardArray[2] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        cardArray[3] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        cardArray[4] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        cardArray[5] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4));
        //L-pieces WITHOUT treasures
        for(int i = 6; i < 16; i++) {
            cardArray[i] = new Tile(0, -1, 0, 0, intGen.nextInt(4));
        }

        intGen = new Random();

        //Straight Pieces (None have treasures)
        for(int i = 16; i < 28; i++){
            cardArray[i] = new Tile(1, -1,0,0, intGen.nextInt(4));
        }

        //T Pieces (ALL have treasures)
        int lowerThan = 34;
        if(tilesType == 0){
            lowerThan += 12;
        }
        for(int i = 28; i < lowerThan; i++) {
            cardArray[i] = new Tile(2, (Integer) treasurePlacer.pop(), 0, 0, intGen.nextInt(4));
        }

        intGen = new Random();
        for(int i = 0; i < intGen.nextInt(25); i++){
            Collections.shuffle(Arrays.asList(cardArray));
        }

        int j = 0;
        for(int i = 0; i < 50; i++){
            if(!hardPlaced.contains(i)){
                cardArray[j].setArrayPos(i%7, i/7);
                tileDeck[i] = cardArray[j];
                j++;
            }
        }

    }

    public Tile[] getTileDeck(){
        return tileDeck;
    }

    public Tile dealTile(){
        tilePoint ++;
        if(tilePoint >= tileDeck.length){
            tilePoint = 0;
        }
        return (Tile) tileDeck[tilePoint];
    }

    public int getTilePointer(){
        return tilePoint;
    }

    public Card[] getCardDeck(){
        return cardDeck;
    }

    public Card dealCard(){
        cardPoint ++;
        if(cardPoint >= cardDeck.length){
            cardPoint = 0;
        }
        return (Card) cardDeck[cardPoint];
    }

    public int getCardPointer(){
        return cardPoint;
    }

    private String[] makeTreasureDict(){

        return new String[]{
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

    }

}
//Collections.shuffle(list)
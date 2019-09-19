package com.mygdx.game;

import java.util.*;

public class Deck {

    private String[] treasureDict;
    private Card[] cardDeck;
    private int cardPoint = -1;
    private Tile[] tileDeck;
    private int[] hardPlaced;
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

           tileDeck = new Tile[49];

           hardPlaced = new int[treasureDict.length];
           for(int i = 0; i < treasureDict.length; i++){
               hardPlaced[i] = i;
           }
           intGen = new Random();
           for(int i = 0; i < intGen.nextInt(25); i++){
               Collections.shuffle(Arrays.asList(hardPlaced));
           }
           treasurePlacer = new Stack();
           for(int treasure : hardPlaced) {
               treasurePlacer.add(treasure);
           }

        if(tilesType == 0){

               tileDeck [0] = new Tile(0, -2,0,0,0);
               tileDeck [6] = new Tile(0, -3,6,0,1);
               tileDeck [42] = new Tile(0, -4,0,6,3);
               tileDeck [48] = new Tile(0, -5,6,6,2);
               hardPlaced = new int[]{0,6,42,48};

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

               hardPlaced = new int[]{0,6,42,48,2,4,14,16,18,20,28,30,32,34,44,46};

        }

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
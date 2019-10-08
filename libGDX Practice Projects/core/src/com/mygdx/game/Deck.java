package com.mygdx.game;

import java.util.*;

public class Deck {

    private Card[] cardDeck;
    private Tile[] tileDeck;
    private int dealPointer = -1;
    private int treasureListLength;
    private int dealType;

    public Deck(int deckType, int treasuresType){
        //DeckType: 0 = cards, 1 = corner set tiles, 2 = odds set tiles; TreasuresType: determines treasure type.

        treasureListLength = Treasures.getTreasureDict(treasuresType).length;

        if(deckType == 0){
            makeCardDeck(treasuresType);
            tileDeck = new Tile[0];
            dealType = 0;
        }else{
            makeTileDeck(deckType-1, treasuresType);
            cardDeck = new Card[0];
            dealType = 1;
        }

    }

    private void makeCardDeck(int treasuresType){
        cardDeck = new Card[treasureListLength];

        for(int i = 0; i < treasureListLength; i++){
            cardDeck[i] = new Card(i, treasuresType);
        }

        Random intGen = new Random();
        for(int i = 0; i < intGen.nextInt(25); i++){
            Collections.shuffle(Arrays.asList(cardDeck));
        }
    }

    private void makeTileDeck(int tilesType, int treasuresType){

        tileDeck = new Tile[50];

        int[] treasureMaker;
        List hardPlaced = null;
        Stack treasurePlacer;
        Random intGen;

        treasureMaker = new int[treasureListLength];
        for(int i = 0; i < treasureListLength; i++){
            treasureMaker[i] = i;
        }
        intGen = new Random();
        for(int i = 0; i < intGen.nextInt(25);i++){
            Collections.shuffle(Arrays.asList(treasureMaker));
        }
        treasurePlacer = new Stack();
        for(int treasure : treasureMaker){
            treasurePlacer.add(treasure);
        }

        if(tilesType == 0 || tilesType > 1){

            tileDeck [0] = new Tile(0, -2,0,0,0, treasuresType);
            tileDeck [6] = new Tile(0, -3,6,0,1, treasuresType);
            tileDeck [42] = new Tile(0, -4,0,6,3, treasuresType);
            tileDeck [48] = new Tile(0, -5,6,6,2, treasuresType);
            hardPlaced = Arrays.asList(0,6,42,48);

        }else if(tilesType == 1){

            tileDeck [0] = new Tile(0, -2,0,0,0, treasuresType);
            tileDeck [6] = new Tile(0, -3,6,0,1, treasuresType);
            tileDeck [42] = new Tile(0, -4,0,6,3, treasuresType);
            tileDeck [48] = new Tile(0, -5,6,6,2, treasuresType);

            tileDeck [2] = new Tile(2, (Integer) treasurePlacer.pop(),2,0,2, treasuresType);
            tileDeck [4] = new Tile(2, (Integer) treasurePlacer.pop(),4,0,2, treasuresType);

            tileDeck [14] = new Tile(2, (Integer) treasurePlacer.pop(),0,2,1, treasuresType);
            tileDeck [16] = new Tile(2, (Integer) treasurePlacer.pop(),2,2,1, treasuresType);
            tileDeck [18] = new Tile(2, (Integer) treasurePlacer.pop(),4,2,2, treasuresType);
            tileDeck [20] = new Tile(2, (Integer) treasurePlacer.pop(),6,2,3, treasuresType);

            tileDeck [28] = new Tile(2, (Integer) treasurePlacer.pop(),0,4,1, treasuresType);
            tileDeck [30] = new Tile(2, (Integer) treasurePlacer.pop(),2,4,0, treasuresType);
            tileDeck [32] = new Tile(2, (Integer) treasurePlacer.pop(),4,4,3, treasuresType);
            tileDeck [34] = new Tile(2, (Integer) treasurePlacer.pop(),6,4,3, treasuresType);

            tileDeck [44] = new Tile(2, (Integer) treasurePlacer.pop(),2,6,0, treasuresType);
            tileDeck [46] = new Tile(2, (Integer) treasurePlacer.pop(),4,6,0, treasuresType);

            hardPlaced = Arrays.asList(0,6,42,48,2,4,14,16,18,20,28,30,32,34,44,46);

        }

        intGen = new Random();
        Tile[] cardArray = new Tile[50-hardPlaced.size()];
        //L-pieces WITH treasures
        cardArray[0] = new Tile(0,  (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        cardArray[1] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        cardArray[2] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        cardArray[3] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        cardArray[4] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        cardArray[5] = new Tile(0, (Integer) treasurePlacer.pop(),0,0, intGen.nextInt(4), treasuresType);
        //L-pieces WITHOUT treasures
        for(int i = 6; i < 16; i++) {
            cardArray[i] = new Tile(0, -1, 0, 0, intGen.nextInt(4), treasuresType);
        }

        intGen = new Random();

        //Straight Pieces (None have treasures)
        for(int i = 16; i < 28; i++){
            cardArray[i] = new Tile(1, -1,0,0, intGen.nextInt(4), treasuresType);
        }

        //T Pieces (ALL have treasures)
        int lowerThan = 34;
        if(tilesType == 0){
            lowerThan += 12;
        }
        for(int i = 28; i < lowerThan; i++) {
            cardArray[i] = new Tile(2, (Integer) treasurePlacer.pop(), 0, 0, intGen.nextInt(4), treasuresType);
        }

        intGen = new Random();
        for(int i = 0; i < intGen.nextInt(25); i++){
            Collections.shuffle(Arrays.asList(cardArray));
        }

        int j = 0;
        for(int i = 0; i < 50; i++){
            if(!hardPlaced.contains(i)){
                cardArray[j].setBoardPosition(i%7, i/7);
                tileDeck[i] = cardArray[j];
                j++;
            }
        }

    }

    Tile[] getTileDeck(){
        return tileDeck;
    }

    Card[] getCardDeck(){
        return cardDeck;
    }

    void shuffle(){
        Random intGen = new Random();
        if(dealType == 0){
            for(int i = 0; i < intGen.nextInt(25); i++){
                Collections.shuffle(Arrays.asList(cardDeck));
            }
            dealPointer = -1;
        }else if(dealType == 1){
            for(int i = 0; i < intGen.nextInt(25); i++){
                Collections.shuffle(Arrays.asList(tileDeck));
            }
            dealPointer = -1;
        }
    }

    int getDealType(){
        return dealType;
    }

    Card dealCard(){
        dealPointer ++;
        return cardDeck[dealPointer];
    }

    Tile dealTile(){
        dealPointer ++;
        return tileDeck[dealPointer];
    }

}

package com.company;
/**
 * Assignment: GUI "Suit Match" Game
 * Module: 5
 * Author: Andrew Paulino and Brandon Mccarthy-santos
 * Description: Should print GUI of Suit Match Game
 */

import java.util.TimerTask;



public class Main {


    public static void main(String[] args) {
        int numPacksPerDeck = 5;
        int numJokersPerPack = 2;
        int numUnusedCardsPerPack = 0;


        Card[] unusedCardsPerPack = null;
        GameState game = new GameState(numPacksPerDeck, numJokersPerPack,
                numUnusedCardsPerPack, unusedCardsPerPack);


        game.start();

    }
}






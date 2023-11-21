package com.company;

import com.company.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

class CardGameOutline {
    private static final int MAX_PLAYERS = 50;
    private GameState gameState;
    private CardTable view;
    private int numPlayers;
    private int numPacks;            // # standard 52-card packs per deck
    private int numJokersPerPack;    // if 2 per pack & 3 packs per deck, get 6
    private int numUnusedCardsPerPack; // # cards removed from each pack
    private int numCardsPerHand;     // # cards to deal each player
    private Deck deck;               // holds the initial full deck and gets
    private Deck deckForPlayArea;
    private Hand[] hand;             // one com.company.Hand for each player
    private Card[] unusedCardsPerPack;   // an array holding the cards not used
    private Hand[] activeHands;

    public CardGameOutline() {
        this(1, 0,
                0,
                null, 4, 13);
    }

    public CardGameOutline(
            int numPacks, //#of decks
            int numJokersPerPack, //#of jokers
            int numUnusedCardsPerPack,  //#of unused cards
            Card[] unusedCardsPerPack, //#array of those cards
            int numPlayers,  //#number of players
            int numCardsPerHand//#of cards per hand.
    ) {

        int k;
        // filter bad values
        if (numPacks < 1 || numPacks > 6)
            numPacks = 1; //set minimum decks to 1.

        if (numJokersPerPack < 0 || numJokersPerPack > 4)
            numJokersPerPack = 0; //set default jokers to 0

        if (numUnusedCardsPerPack < 0 || numUnusedCardsPerPack > 50) //  > 1 card
            numUnusedCardsPerPack = 0; //set default unused cards to 0

        if (numPlayers < 1 || numPlayers > MAX_PLAYERS)
            numPlayers = 5; //set maximum players to 4

        // one of many ways to assure at least one full deal to all players
        if (numCardsPerHand < 1 ||
                numCardsPerHand > numPacks * (52 - numUnusedCardsPerPack)
                        / numPlayers)
            numCardsPerHand = numPacks * (52 - numUnusedCardsPerPack) / numPlayers;

        // allocate
        this.unusedCardsPerPack = new Card[numUnusedCardsPerPack];
        this.hand = new Hand[5];
        deck = new Deck(1);

        for (k = 0; k < 5; k++) {
            this.hand[k] = new Hand();
        }


        // assign to members
        this.numPacks = numPacks;
        this.numJokersPerPack = numJokersPerPack;
        this.numUnusedCardsPerPack = numUnusedCardsPerPack;
        this.numPlayers = numPlayers;
        this.numCardsPerHand = numCardsPerHand;
        for (k = 0; k < numUnusedCardsPerPack; k++)
            this.unusedCardsPerPack[k] = unusedCardsPerPack[k];
        // prepare deck and shuffle
        newGame();
    }

    //METHODS
    void sortHands() {
        int k;
        for (k = 0; k < numPlayers; k++)
            hand[k].sort();
    }

    public Hand getHand(int k) {
        if (k < 0 || k >= 5) {
            return new Hand();
        }
        return hand[k];
    }

    public Card getCardFromDeck() {
        return deck.dealCard();
    }

    public int getNumCardsRemainingInDeck() {
        return deck.getNumCards();
    }

    public void newGame() {
        int k, j;
        // clear the hands
        for (k = 0; k < 5; k++)
            hand[k].resetHand();

        // restock the deck
        deck.init(1);
        // remove unused cards
        for (k = 0; k < numUnusedCardsPerPack; k++)
            deck.removeCard(unusedCardsPerPack[k]);
        // add jokers
        for (k = 0; k < 1; k++)
            for (j = 0; j < numJokersPerPack; j++)
                deck.addCard(new Card('X', Card.Suit.values()[j]));

        deck.removeNullSpaces();
        deck.shuffle();
    }


    public void dealStack() {
        hand[2].takeCard(deck.dealCard());
        hand[3].takeCard(deck.dealCard());
        hand[4].takeCard(deck.dealCard());
        view.updateCardInDeck(deck.getTopCard());
        view.refreshView(getArrayOfCardStacks(), getActionListeners(), hand[0].inspectTopCard(), hand[1].inspectTopCard());
    }

    public void refresh(){
        view.refreshView(getArrayOfCardStacks(), getActionListeners(), hand[0].inspectTopCard() , hand[1].inspectTopCard());
    }

    public boolean deal() {
        // returns false if not enough cards, but deals what it can
        int k, j;
        boolean enoughCards;
        // clear all hands
        for (j = 0; j < numPlayers; j++)
            hand[j].resetHand();

        enoughCards = true;
        for (k = 0; k < 5; k++) {
            hand[k].takeCard(deck.dealCard());
        }
        return enoughCards;
    }


    boolean takeCard(int playerIndex) {
        // returns false if either argument is bad
        if (playerIndex < 0 || playerIndex > numPlayers - 1)
            return false;
        // Are there enough Cards?
        if (deck.getNumCards() <= 0)
            return false;
        return hand[playerIndex].takeCard(deck.dealCard());
    }

    boolean compareHandWithActiveCards() {
        int handIndex = 0;
        for (int j = 2; j < 5; j += 1) {
            if (Card.checkIfCardValueIsOneLevelHigherOrLower(hand[0].inspectTopCard().getValue(), hand[j].inspectTopCard().getValue())) {

                Card temp =  hand[j].inspectTopCard();
               temp.highlightCard();
               hand[j].resetHand();
               hand[j].takeCard( new Card(temp));
            }
        }
        return false;
    }

    void takeTurn(int handIndex, int cardIndex, int stackIndex) {
        // Step 1: Remove from hand
        Card cardToBeTaken = new Card(hand[handIndex].playCard(cardIndex));
        // Step 2: Add to stack
        hand[stackIndex].takeCard(cardToBeTaken);
        // Re-add card
        hand[handIndex].takeCard(deck.dealCard());
        // Re-render everything
        view.refreshView(getArrayOfCardStacks(), getActionListeners(), hand[0].inspectTopCard(), hand[1].inspectTopCard());
    }

    public void simulateComputerTurn(boolean playerDoesntHaveCard) throws InterruptedException {

        Timer timer = new Timer(this, view, playerDoesntHaveCard);
        timer.start();
//

//        boolean cardHasBeenTakenFromComputer = false;
//        char cardInRotationValue = hand[1].inspectTopCard().getValue();
//        timer.start();
//
//        for (int j = 2; j < 5; j += 1) {
//            if (!cardHasBeenTakenFromComputer &&
//                    Card.checkIfCardValueIsOneLevelHigherOrLower(cardInRotationValue, hand[j].inspectTopCard().getValue())) {
//                System.out.println(" I played my card u lil .... ");
//                takeTurn(1, 0, j);
//                cardHasBeenTakenFromComputer = true;
//                timer.start();
//
//            }
//        }
//        if (!cardHasBeenTakenFromComputer) {
//            timer.start();
//            System.out.println(" OOps don't have one ");
//            gameState.setComputerMulligen();
//            if (playerDoesntHaveCard) {
//                System.out.println(" Welll none of us have it ");
//                dealStack();
//
//            }
//
//        }
    }

    public Card[] getArrayOfCardStacks() {
        Card[] cardsOfStacks = new Card[3];

        cardsOfStacks[0] = new Card(hand[2].inspectTopCard());
        cardsOfStacks[1] = new Card(hand[3].inspectTopCard());
        cardsOfStacks[2] = new Card(hand[4].inspectTopCard());

        return cardsOfStacks;
    }


    public ActionListener[] getActionListeners() {
        ActionListener[] actionListeners = new ActionListener[3];
        int realIndex = 0;
        for (int b = 2; b < 5; b += 1) {
                Card temp = new Card(hand[b].inspectTopCard());
                int finalB = b;
                actionListeners[realIndex] = new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            playTurn(0, finalB, gameState, gameState.getView());

                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                };
                realIndex += 1;
        }
        return actionListeners;
    }

    public void initializeGame(CardTable view, GameState gameState) {
        this.gameState = gameState;
        this.view = view;
        compareHandWithActiveCards();
        view.refreshView(getArrayOfCardStacks(), getActionListeners(), hand[0].inspectTopCard() , hand[1].inspectTopCard());
        view.updateCardInDeck(getNumCardsRemainingInDeck());
    }

    void playTurn(int handIndex, int stackIndex, GameState gameState, CardTable view) throws InterruptedException {
        if (!Card.checkIfCardValueIsOneLevelHigherOrLower(hand[0].inspectTopCard().getValue(), hand[stackIndex].inspectTopCard().getValue())) {
            showError();
            return;
        }
        view.getPnlComputerHand().setForeground(Color.BLUE);
        view.getPnlComputerHand().repaint();
        this.gameState = gameState;

        takeTurn(0, handIndex, stackIndex);

        simulateComputerTurn(false);


        view.updateCardInDeck(deck.getTopCard());


        if (hand[0].isEmpty() || hand[1].isEmpty()) {
            if (gameState.getComputerMulligen() > gameState.getHumanMulligen()) {
                // humans wins
            } else if (gameState.getComputerMulligen() < gameState.getHumanMulligen()) {
                // computer wins
            } else {
                // tie
            }
        }
    }

    void showError() {
        view.showError();
    }

    Card playCard(int playerIndex, int cardIndex) {
        // returns bad card if either argument is bad
        if (playerIndex < 0 || playerIndex > numPlayers - 1 ||
                cardIndex < 0 || cardIndex > numCardsPerHand - 1) {
            //Creates a card that does not work
            return new Card('M', Card.Suit.SPADES);
        }
        // return the card played
        return hand[playerIndex].playCard(cardIndex);
    }


}//END


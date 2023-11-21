package com.company;

import java.util.Arrays;

class Hand {
    //VARIABLES
    public final int MAX_CARDS = 100;
    // We define a constant of 100 here, to avoid monster arrays.
    Card[] myCards; //represents all cards in present hand
    int numCards;   //represents number of cards in hand as int

    //CONSTRUCTOR
    public Hand() {
        /*
         * Constructor (no arguments)
         * This will initialize our member variables to basically
         * nothing.
         * */
        this.myCards = new Card[MAX_CARDS];
        this.numCards = 0;
    }

    //METHODS
    void sort() {
        myCards = Card.sortArray(myCards, numCards);
    }

    public void resetHand() {
        // Should fill all items in array with null.
        // We used Arrays.fill to loop and define all cards as null.
        Arrays.fill(myCards, null);
        numCards = 0;
    }

    public boolean isEmpty() {
        return 0 >= numCards;
    }

    public boolean takeCard(Card card) {
        /*
         * takeCard...
         * This will add to the myCards array
         * and also define a new card and not
         * reference in this array.
         * */
        if (numCards < MAX_CARDS) {
            // Getting primitive values from card class.
            char cardValue = card.getValue();
            Card.Suit suitValue = card.getSuit();
            // Note: We will take values and create new card.
            myCards[numCards] = new Card(cardValue, suitValue);
            // We go up 1 for the next possible card
            numCards += 1;
            return true;
        }
        return false;
    }

    public Card playCard(int cardIndex) {
        /*
         * playCard...
         * We will return the top card in the array, but
         * also delete it (point to null) and will return
         * a bad card if reaches the end of the hand.
         * */
        if (numCards == 0) //error
        {
            //Creates a card that does not work
            return new Card('M', Card.Suit.SPADES);
        }
        //Decreases numCards.
        Card card = myCards[cardIndex];

        numCards--;
        for (int i = cardIndex; i < numCards; i++) {
            myCards[i] = myCards[i + 1];
        }

        myCards[numCards] = null;

        return card;


    }

    public Card inspectTopCard() {
        if (numCards == 0) {
            return myCards[0];
        }
        return myCards[numCards - 1];
    }

    public Card inspectCard(int k) {
        /*
         * inspectCard...
         * Will grab a card based on index and return card.
         * Note: We will have to detract from passed index, due
         * to arrays starting at 0.
         * If out of bounce => return bad card.
         * */

        // Subtract 1 from passed index since arrays start at 0.
        //~~~
        // System.out.println(k + " *DEBUG inspectCard() index passed / inspectCard");
        int realIndex = k;
        if (numCards > realIndex && !(realIndex < 0)) {
            return myCards[realIndex];
        }
        // Returns bad card if not within index
        //System.out.println("throwing bad card");
        return new Card('!', Card.Suit.SPADES);
    }

    public int getNumCards() {
        // Accessor for numCards
        return numCards;
    }
    //!!! Is this string builder causing the null crash?
    public String toString() {
        /*
         * toString...
         * Presents the cards in a loop and
         * adds a new line ever 4 cards.
         * */
        StringBuilder finalString = new StringBuilder();
        for (int i = 0; i < numCards; i += 1) {
            finalString.append(myCards[i].toString() + ", ");
            // To insert a new line, we use modulo operator.
            if (i % 4 == 0 && i != 0) {
                finalString.append('\n');
            }
        }
        return finalString.toString();
    }

}
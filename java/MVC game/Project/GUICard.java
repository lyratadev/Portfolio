package com.company;

import javax.swing.*;
import java.io.File;

class GUICard { //INIT CARDICONS FOR GUI
    //VARIABLES
    private static Icon[][] iconCards = new ImageIcon[14][4];
    private static Icon iconBack; //storing img for back of card
    static boolean iconsLoaded = false;
    //CONSTRUCTOR
    GUICard() {
        GUICard.loadCardIcons();
    }
//METHODS

    static String turnIntIntoCardValue(int k) {
        /*
         * takes integer value and turns it into appropriate card String.
         */
        if (k == 0) {
            return "A"; //ACEC
        } else if (k == 1) {
            return "X"; //JOKER
        } else if (k >= 2 && 9 >= k) { //2-9
            return String.valueOf(k);
        } else if (k == 10) { //10
            return "T";
        } else if (k == 11) { //QUEEN
            return "Q";
        } else if (k == 12) { //KING
            return "K";
        } else {
            return "J"; //JACK
        }
    }

    static String turnIntIntoCardSuit(int j) {
        // turns 0 - 3 into "C", "D", "H", "S"
        if (j == 0) {
            return "C";
        } else if (j == 1) {
            return "D";
        } else if (j == 2) {
            return "H";
        } else {
            return "S";
        }
    }


    static void loadCardIcons() {
        String[] cardNames;

        if (!iconsLoaded) {
            File f = new File("images");
            cardNames = f.list();
            for (int i = 0; i < 14; i += 1) {

                for (int j = 0; j < 4; j += 1) {

                    iconCards[i][j] = new ImageIcon("images/" + turnIntIntoCardValue(i) + turnIntIntoCardSuit(j) + ".gif");
                }
            }
            iconBack = new ImageIcon("images/BK.gif");
            iconsLoaded = true;

            //DEBUG
            //System.out.println("Cardnames " + cardNames.toString());
        }

    }


    static public Icon getIcon(Card card) {
        loadCardIcons();
        return iconCards[valueAsInt(card)][suitAsInt(card)];
    }

    public static Icon getIconBack() {

        loadCardIcons();
        return iconBack;
    }


    static private int valueAsInt(Card card) {
        //System.out.println("*DEBUG valueAsInt " + card.getValue() + "test");
        char value = card.getValue();
        //System.out.println("*DEBUG value" + card.getValue() + "value");

        boolean isNonNumber =
                value == 'T' ||
                        value == 'J' ||
                        value == 'Q' ||
                        value == 'K' ||
                        value == 'A' ||
                        value == 'X';

        if (!isNonNumber) {
            //System.out.println("is not a nonnumber" + card.getValue());
            if (value == '0') {
                // System.out.println("nonnumber");
                return 0;
            }
            return value - '0';
        }

        if (value == 'A') {
            return 0;
        }
        if (value == 'Q') {
            return 11;
        }
        if (value == 'K') {
            return 12;
        }

        if (value == 'T') {
            return 10;
        }

        if (value == 'J') {
            return 13;
        }
        //System.out.println("*DEBUG joker");
        return 1;
    }

    static private int suitAsInt(Card card) {
        Card.Suit value = card.getSuit();

        if (value == Card.Suit.CLUBS) {
            return 0;
        } else if (value == Card.Suit.DIAMONDS) {
            return 1;
        } else if (value == Card.Suit.HEARTS) {
            return 2;
        } else {
            return 3;
        }
    }
}
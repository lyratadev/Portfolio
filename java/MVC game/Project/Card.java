package com.company;

public class Card {
    // VARIABLES
    public enum Suit {
        /*
         * Suit:
         * This is the definition
         * of the 4 possible
         * suits for the com.company.Card
         * class.
         * */
        CLUBS,
        DIAMONDS,
        HEARTS,
        SPADES
    }
    private char value;
    private Suit suit;
    private boolean errorFlag = false;
    public static char[] valueRanks = {
            '2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A', 'X'
    };



    //CONSTRUCTOR
    public Card() {
        /*
         * Constructor (no arguments)...
         * Will take reference of class
         * and define into the 2 constructor
         * arguments.
         * */
        this('A', Suit.SPADES);
    }

    public Card(Card origCard) {
        /*
         * Constructor (1 argument)...
         * This will take a com.company.Card (origCard)
         * and make a deep copy of the values
         * in the class and then redefine into
         * the member variables.
         * */
        if (origCard == null) {
            //System.out.println("*DEBUG NULL card()");
            return;

        }
        Suit suitValue = origCard.getSuit();
        char charValue = origCard.getValue();
        boolean errorFlagValue = origCard.getErrorFlag();

        suit = suitValue;
        value = charValue;
        errorFlag = errorFlagValue;
    }


    //~~~ !variable was set to 'value' the same as the private member variable
    public Card(char cardValue, Suit suit) {
        /*
         * Constructor (2 arguments)...
         * Will set based on value and suit.
         * */
        set(cardValue, suit);
    }

    //METHODS
    static public Card[] sortArray(Card[] cardArray, int arraySize) {
        int counter = 0;
        for (int i = 0; i < arraySize; i += 1) {
            if (i != arraySize - 1) {
                if (checkIfCharIsGreaterThanOtherChar(cardArray[i].getValue(), cardArray[i + 1].getValue())) {
                    Card next = new Card(cardArray[i + 1]);
                    Card current = new Card(cardArray[i]);
                    cardArray[i] = next;
                    cardArray[i + 1] = current;
                    counter += 1;
                }
            }
        }
        if (counter > 0) {
            return sortArray(cardArray, arraySize);
        } else {
            return cardArray;
        }
    }

    static boolean checkIfCharIsGreaterThanOtherChar(char compareOne, char compareTwo) {
        int indexOne = 0;
        int indexTwo = 0;
        for (int i = 0; i < valueRanks.length; i += 1) {
            if (compareOne == valueRanks[i]) {
                indexOne = i;
            }
            if (compareTwo == valueRanks[i]) {
                indexTwo = i;
            }
        }

        return indexOne > indexTwo;
    }

    static public boolean checkIfCardValueIsOneLevelHigherOrLower(char passedValue, char target) {
        int indexOne = 0;
        int indexTwo = 0;
        for (int i = 0; i < valueRanks.length; i += 1) {
            if (target == valueRanks[i]) {
                indexOne = i;
            }
            if (passedValue == valueRanks[i]) {
                indexTwo = i;
            }
        }

        if (indexOne == 0) {
            return indexTwo == 1;
        }

        if (indexOne == valueRanks.length - 1) {
            return indexTwo == valueRanks.length - 2;
        }

        return indexOne + 1 == indexTwo || indexOne - 1 == indexTwo;
    }

    private boolean isValid(char value, Suit suit) {
        /*
         * isValid...
         * Will mostly determine the char value and check within
         * allowed chars or a number range of 2-9.
         * */


        /*
         * This line will convert a char to a numeric value,
         * and if not numeric value it should return 0
         * which will fail in the conditional.
         * */
        int valueOfAsciiChar = Character.getNumericValue(value);
        int valueOfBeginningChar = 2;
        int valueOfEndingChar = 9;

        // We are checking if the user put an alphabetical char as well here.
        boolean isNonNumber = value == 'T' || value == 'J' || value == 'Q'
                || value == 'K' || value == 'A' || value == 'X';
        /* This condition will pass if an allowed char is passed or number
         * within the range of 2-9
         */
        return (valueOfAsciiChar >= valueOfBeginningChar
                && valueOfEndingChar >= valueOfAsciiChar) || isNonNumber;
    }

    public boolean set(char value, Suit suit) {
        /*
         * set...
         * This will take the 2 arguments and
         * run against 'isValid' to determine
         * if the char fits within the criteria
         * and then return true/false based on that.
         * */
        // isValid => checks if the char is within requirements
        if (isValid(value, suit)) {
            this.value = value;
            this.suit = suit;
            errorFlag = false;
            return true;
        }
        errorFlag = true;
        return false;
    }

    public boolean equals(Card card) {
        /*
         * equals...
         * Will take reference to member variables
         * and compare against passed in card.
         * */
        boolean isSuitEqual = card.getSuit() == suit;
        boolean isValueEqual = card.getValue() == value;
        boolean isErrorFlagEqual = card.getErrorFlag() == errorFlag;

        return isSuitEqual && isValueEqual && isErrorFlagEqual;
    }

    public boolean getErrorFlag() {
        //ACCESSOR FOR ERROR FLAG
        return errorFlag;
    }

    public Suit getSuit() {
        // Accessor for suit

        return suit;
    }

    public char getValue() {
        // Accessor for value

        return this.value;
    }

    @Override
    public String toString() {
        /*
         * toString...
         * Will return the value, and if
         * there is an error return the bad character
         * */
        if (errorFlag) {
            return "**Bad Character**";
        }
        return value + " of " + suit;
    }

    public void setValue(char value) {
        // Mutator for value

        set(value, this.suit);
    }

    public void setSuit(Suit suit) {
        // Mutator for suit

        set(this.value, suit);
    }


}
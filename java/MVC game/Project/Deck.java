package com.company;

import java.util.Arrays;
import java.util.Random;

/*DECK CLASS REPRESENTS A DECK OF CARDS
 * Stores those cards in an array of 56 cards, 4 slots at the end are null for
 * jokers
 * topCard represents the last non-null card in the array
 */
class Deck {
    //VARIABLES
    public final int MAX_CARDS = (6 * CARD_DECK_SIZE);
    static final int CARD_DECK_SIZE = 56;

    private Card cards[]; //card array for present cards
    private int topCard; //stores last items in array index
    private int numPacks; //keeps track of number of decks in play
    private static Boolean sentinelFlag = false; //flag for allocateMasterPack
    private static Card masterPack[];
   /*This is a private static com.company.Card array, masterPack[], containing exactly 52 card references,
   which point to all the standard cards.
   It will enable us to avoid capriciously and repeatedly declaring the same 52 cards
   which are needed as the game proceeds.
   In other words, once we have, say, a ('6', spades)
   com.company.Card constructed and stored (inside this masterPack[]),
   we use that same instance whenever we need it as a source to copy in various places,
   notably during a re-initialization of the com.company.Deck object;
   it will always be in the masterPack[] array for us to copy.
   */

    //CONSTRUCTORS
    public Deck()//overloaded constructor for 0 variables passed
    {
        this(1);
    }

    public Deck(int numPacks)
   /*populates arrays and assigns initial values to members.
   Overload so that if no parameters are passed, 1 pack is assumed.
   */ {
        allocateMasterPack(); //DECK OF 52 CARDS without JOKERS
        this.numPacks = numPacks;
        if ((numPacks * masterPack.length) <= MAX_CARDS) //limit max cards to 6 decks
        {

            //      cards = new com.company.Card[(masterPack.length * numPacks)]; //new card array of packs x cards.
            //topCard = cards.length - 1;//last item in index                     //index of last card in array
            init(this.numPacks);

        }
    }

    //PUBLIC METHODS
    public void init(int numPacks)
    /* re-populate cards[] with the standard 52 × numPacks cards.
     * We should not re-populate the static array, masterPack[],
     * since that was done once, in the (first-invoked) constructor and never changes.
     */ {
        // cards = new com.company.Card[(masterPack.length * numPacks)];  ~~~~~
        cards = new Card[(CARD_DECK_SIZE * numPacks)]; //note deck is now 56 items, 4 null

        this.numPacks = numPacks;


        int indexCount = 0;
        for (int packsInDeck = 0; packsInDeck < numPacks; packsInDeck++) {
            for (int count   = 0; count < (CARD_DECK_SIZE - 4) /* -4 for joker */; count++) {
                cards[indexCount] = new Card(); //create null card
                cards[indexCount] = masterPack[count]; //fill it with appropriate masterPack item
                indexCount++;
            }
        }

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        int iterator = 1;
        do
        {
            topCard = cards.length - iterator; //last item in index, -- for null items.
            iterator++;
        }
        while (cards[this.topCard] == null); //while top card is null
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    }

    public void shuffle()  /*
     *  void shuffle() - mixes up the cards with the
     *  help of the standard random number generator.
     */ {
        Random rand = new Random(); //init random object
        int tempTopCard = topCard;//fetching end of array
        //int indexCount = 0; //setting bounds
        for (int i = 0; i < cards.length; i += 1) //for each element within card array
        {
            // Should return a range from the top card
            int randomIndex = rand.nextInt(tempTopCard); //get random int
            // within card bounds and swap index values
            // We swap reference of the value
            Card currentCard = cards[i];
            Card cardToSwitch = cards[randomIndex];

            // Swapping the values at index
            cards[i] = cardToSwitch;
            cards[randomIndex] = currentCard;
        }
    }

    public void sort() {
        cards = Card.sortArray(cards, topCard);
    }

    public int getInt()  //Returns total number of items in array
    {
        return cards.length;
    }

    public int getTopCard() {
        return topCard;
    }

    public int getNumCards() {
        return getInt();
    }

    public Card dealCard()
        /* com.company.Card dealCard() - returns and removes the card in the top occupied
         *  position of cards[]. Make sure there are still cards available.
         *   This is an object copy, not a reference copy,
         *   since the source of the com.company.Card might destroy or change
         *   its data after it is sent out.
         */ {
        if (topCard >= 0) {
            Card dealtCard = new Card(cards[topCard]); //dealing top card
            cards[topCard] = null; // nullifying pointer
            if (topCard > 0 )
            {
                topCard--;
            }
            return dealtCard;
        } else {
            return new Card('!', Card.Suit.DIAMONDS); //return error card
        }
    }

    public Card inspectCard(int k)
        /* Accessor for individual card, returns card with errorFlag =
         * true if k is bad.
         * Returns object copy not reference.
         */ {
        int realIndex = k;
        if (topCard >= realIndex && realIndex < cards.length) //bounds checking
        {
            return cards[realIndex];
        }
        // Returns bad card if not within index
        System.out.println("com.company.Deck inspectCard()");
        return new Card('!', Card.Suit.SPADES);
    }

    public boolean addCard(Card card) {
        int addSlot = topCard + 1; //add card to 1 slot above topCard
        //System.out.println("*Debug addCard()" + addSlot);
        cards[addSlot] = card;
        topCard += 1;
        return true;
    }

    public void removeNullSpaces()
    {

        int iterator = 1;
        do
        {
            topCard = cards.length - iterator; //last item in index, -- for null items.
            iterator++;
        }
        while (cards[this.topCard] == null); //while top card is null
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Card[] newDeckWithJokers = new Card[(topCard)];

        for (int i = 0; i < topCard;i++)
        {
            newDeckWithJokers[i] = cards[i];//setting new card deck to old values, minus null spaces
        }


        cards = newDeckWithJokers; //referencing cards with new deck.
        //    System.out.println("New com.company.Deck init");
        topCard = cards.length - 1; //resetting topcard index to reflect new deck
    }

    public boolean removeCard(Card card) {
        Card topCard = new Card(cards[getTopCard()]);
        for (int i = 0; i < cards.length; i += 1) {
            if (cards[i].getValue() == card.getValue() && cards[i].getSuit() == card.getSuit()) {
                cards[i] = topCard;
                this.topCard = this.topCard - 1;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.company.Deck\n{" +
                " \n MAX_CARDS =" + MAX_CARDS +
                ",\n cards =" + Arrays.toString(cards) +
                ", topCard Index =" + topCard +
                ", numPacks =" + numPacks +
                '}';
    }
    //PRIVATE METHODS
    private static void allocateMasterPack()/*
     *called by the constructor.
     *if many com.company.Deck objects are constructed this static method
     *will not allow itself to be executed more than once.
     */ {
        if (!sentinelFlag)//if flag not triggered
        {
            masterPack = new Card[52];
            sentinelFlag = true;//set flag to prevent future iterations

            char[] cardValues = {//Array for 13 values representing cards
                    'A', 'K', 'Q', 'J', 'T', '9', '8',
                    '7', '6', '5', '4', '3', '2'};

            Card.Suit[] cardSuites = {
                    Card.Suit.CLUBS,
                    Card.Suit.DIAMONDS,
                    Card.Suit.HEARTS,
                    Card.Suit.SPADES
            }; //4 values representing suits of cards.

            int indexCounter = 0;

            for (Card.Suit suit : cardSuites) { //for each 4 suites
                for (char val : cardValues) { //for each 13 cards
                    masterPack[indexCounter] = new Card(val, suit); //fill new card
                    indexCounter++;
                }
            }
            // System.out.println("*DEBUG AllocateMaster");

        }
    }//end of allocateMasterPack()

}//end of deck class
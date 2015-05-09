package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import shared.enums.CardShape;
import shared.enums.SpellID;

public class Hand {

    /** Maximum # of cards allowed in one hand. */
    private final static int MAX_CARDS = 5;
    private HashMap<CardShape, Integer> counters = new HashMap<>();
    private Random r = new Random();

    public Hand() {
        counters.put(CardShape.SHAPE1, 0);
        counters.put(CardShape.SHAPE2, 0);
        counters.put(CardShape.SHAPE3, 0);
    }

    /** Returns the maximum number of cards a hand can hold. */
    public static int maxSize() { return MAX_CARDS; }

    /** Returns the number of cards in the hand. */
    public int size() {
        return counters.get(CardShape.SHAPE1) +
               counters.get(CardShape.SHAPE2) +
               counters.get(CardShape.SHAPE3);
    }

    /** Adds the card of this cardshape to the hand. */
    public void add(CardShape card) {
        // We don't add if it's the NOCARD or if we're already full
        if (size() < 5 && card != CardShape.NOCARD) {
            // Increment the counter of that card by 1
            counters.replace(card, counters.get(card) + 1);
        }
    }

    /** Removes the card of this cardshape from the hand. */
    public void remove(CardShape card) {
        if (counters.get(card) > 0) {
            // Decrement the counter of that card by 1
            counters.replace(card, counters.get(card) - 1);
        }
    }

    /** Returns the number of cards of a particular shape in this hand. */
    public int getNumberOfCards(CardShape thisShape) {
        // As long as they didn't give us the NOCARD,
        if (thisShape != CardShape.NOCARD) {
            // Return the number of instances of that card.
            return counters.get(thisShape);

        // Otherwise, return how much space we have left.
        } else {
            return MAX_CARDS - size();
        }
    }

    /** Returns an array of all the cards in your hand. */
    public CardShape[] getAllCards() {
        // This is literally the least object-oriented thing ever
        CardShape[] allCardsArray = new CardShape[MAX_CARDS];
        ArrayList<CardShape> allCardsList = new ArrayList<>();

        // Add the correct amount of each shape to our list (IN ORDER)
        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE1); i++) {
            allCardsList.add(CardShape.SHAPE1);
        }

        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE2); i++) {
            allCardsList.add(CardShape.SHAPE2);
        }

        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE3); i++) {
            allCardsList.add(CardShape.SHAPE3);
        }

        while (allCardsList.size() < MAX_CARDS) {
            allCardsList.add(CardShape.NOCARD);
        }

        // Convert to array
        allCardsList.toArray(allCardsArray);
        return allCardsArray;
    }

    /** Empties the hand of all of its cards. */
    public void clear() {
        for (CardShape shape : counters.keySet()) {
            counters.replace(shape, 0);
        }
    }

    /** Adds a random card to the hand. */
    public void addRandomCard() {
        switch(r.nextInt(3) + 1) {
        case 1: add(CardShape.SHAPE1);
                break;
        case 2: add(CardShape.SHAPE2);
                break;
        case 3: add(CardShape.SHAPE3);
                break;
        }
    }

    /** Fills the hand with random card shapes. */
    public void fillRandomly() {
        // Add randomly until our hand is full
        while (size() < MAX_CARDS) {
            addRandomCard();
        }
    }

    /** Returns an array of all spells that can possibly be cast. */
    public SpellID[] getCastableSpells() {
        ArrayList<SpellID> castableSpells = new ArrayList<>();
        castableSpells.add(SpellID.NOSPELL);

        // Convert to an array
        SpellID[] retVal = new SpellID[castableSpells.size()];
        castableSpells.toArray(retVal);
        return retVal;
    }
}

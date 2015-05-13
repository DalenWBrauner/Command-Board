package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import shared.enums.CardShape;

public class Hand {

    /** Maximum # of cards allowed in one hand. */
    private final static int MAX_CARDS = 5;
    private HashMap<CardShape, Integer> counters = new HashMap<>();
    private Random r = new Random();

    public Hand() {
        counters.put(CardShape.SHAPE1, 0);
        counters.put(CardShape.SHAPE2, 0);
        counters.put(CardShape.SHAPE3, 0);
        //fillRandomly(); TODO: Remove comment when the game is finished!
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
        add(card, 1);
    }

    /** Adds a certain number of the card of this cardshape to the hand. */
    public void add(CardShape card, int amount) {
        // We won't add if it's the NOCARD
        if (card != CardShape.NOCARD) {

            // And we won't add more than we can hold
            int canHold = maxSize() - size();
            int newAmount = counters.get(card) + Math.min(amount, canHold);

            counters.replace(card, newAmount);
        }
    }

    /** Removes the card of this cardshape from the hand. */
    public void remove(CardShape card) {
        remove(card, 1);
    }

    /** Removes a certain number of the card of this cardshape from the hand. */
    public void remove(CardShape card, int amount) {
        // We won't subtract if it's the NOCARD
        if (card != CardShape.NOCARD) {

            // And we won't subtract more than we have
            int weHave = counters.get(card);
            int newAmount = counters.get(card) - Math.min(amount, weHave);

            counters.replace(card, newAmount);
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
}

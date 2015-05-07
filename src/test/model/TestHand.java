package test.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import model.Hand;

import org.junit.Test;

import shared.enums.CardShape;

public class TestHand {

    @Test
    public void TestInstantiate() {

        // The hand should be completely empty
        Hand theHand = new Hand();
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));

        // The hand should be filled to the brim with NOCARD
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.NOCARD));

        CardShape[] cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }
    }

    @Test
    public void TestAdd() {

        Hand theHand = new Hand();
        assertEquals(0, theHand.size());

        CardShape[] cards;
        cards = theHand.getAllCards();

        // False Addition: NOCARD
        // Nothing should change
        theHand.add(CardShape.NOCARD);
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.NOCARD, cards[0]);
        assertEquals(CardShape.NOCARD, cards[1]);
        assertEquals(CardShape.NOCARD, cards[2]);
        assertEquals(CardShape.NOCARD, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // First Addition: Shape1
        theHand.add(CardShape.SHAPE1);
        assertEquals(1, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.NOCARD, cards[1]);
        assertEquals(CardShape.NOCARD, cards[2]);
        assertEquals(CardShape.NOCARD, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // Second Addition: Shape3
        theHand.add(CardShape.SHAPE3);
        assertEquals(2, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE3, cards[1]);
        assertEquals(CardShape.NOCARD, cards[2]);
        assertEquals(CardShape.NOCARD, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // False Addition: NOCARD
        // Nothing should have changed from before
        theHand.add(CardShape.NOCARD);
        assertEquals(2, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE3, cards[1]);
        assertEquals(CardShape.NOCARD, cards[2]);
        assertEquals(CardShape.NOCARD, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // Third Addition: Shape2
        theHand.add(CardShape.SHAPE2);
        assertEquals(3, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE2, cards[1]);
        assertEquals(CardShape.SHAPE3, cards[2]);
        assertEquals(CardShape.NOCARD, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // Fourth Addition: Shape3
        theHand.add(CardShape.SHAPE3);
        assertEquals(4, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE2, cards[1]);
        assertEquals(CardShape.SHAPE3, cards[2]);
        assertEquals(CardShape.SHAPE3, cards[3]);
        assertEquals(CardShape.NOCARD, cards[4]);

        // Fifth Addition: Shape2
        theHand.add(CardShape.SHAPE2);
        assertEquals(5, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE2, cards[1]);
        assertEquals(CardShape.SHAPE2, cards[2]);
        assertEquals(CardShape.SHAPE3, cards[3]);
        assertEquals(CardShape.SHAPE3, cards[4]);

        // False Addition: NOCARD
        // Nothing should have changed from before
        theHand.add(CardShape.NOCARD);
        assertEquals(5, theHand.size());
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE3));
        cards = theHand.getAllCards();
        assertEquals(CardShape.SHAPE1, cards[0]);
        assertEquals(CardShape.SHAPE2, cards[1]);
        assertEquals(CardShape.SHAPE2, cards[2]);
        assertEquals(CardShape.SHAPE3, cards[3]);
        assertEquals(CardShape.SHAPE3, cards[4]);
    }

    @Test
    public void TestFill() {

        Hand theHand = new Hand();
        assertEquals(0, theHand.size());

        theHand.fillRandomly();
        final CardShape[] filledCards = theHand.getAllCards();

        // Check we're full
        assertEquals(Hand.maxSize(), theHand.size());
        for (CardShape card : filledCards) {
            assertFalse(card == CardShape.NOCARD);
        }

        // Assure we can add nothing once full
        theHand.add(CardShape.SHAPE1);
        assertEquals(Hand.maxSize(), theHand.size());
        for (CardShape card : theHand.getAllCards()) {
            assertFalse(card == CardShape.NOCARD);
        }
        theHand.add(CardShape.SHAPE3);
        assertEquals(Hand.maxSize(), theHand.size());
        for (CardShape card : theHand.getAllCards()) {
            assertFalse(card == CardShape.NOCARD);
        }

        // Assure nothing changes if we try
        CardShape[] cardsAfterTrying;
        cardsAfterTrying = theHand.getAllCards();
        for (int i = 0; i < theHand.size(); i++) {
            assertEquals(filledCards[i], cardsAfterTrying[i]);
        }

        // No matter how hard we try
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE1);
        theHand.add(CardShape.SHAPE1);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE3);
        cardsAfterTrying = theHand.getAllCards();
        for (int i = 0; i < theHand.size(); i++) {
            assertEquals(filledCards[i], cardsAfterTrying[i]);
        }

        // Even if we ask the code to do it for us
        theHand.fillRandomly();
        cardsAfterTrying = theHand.getAllCards();
        for (int i = 0; i < theHand.size(); i++) {
            assertEquals(filledCards[i], cardsAfterTrying[i]);
        }
    }

    @Test
    public void TestRemove() {

        Hand theHand = new Hand();
        assertEquals(0, theHand.size());

        // Fill with SHAPE2
        while (theHand.size() < Hand.maxSize()) {
            theHand.add(CardShape.SHAPE2);
        }
        assertEquals(Hand.maxSize(), theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));

        // Remove them one at a time and check that they are removed
        int removed = 0;
        while (theHand.size() > 0) {
            theHand.remove(CardShape.SHAPE2);
            removed++;
            assertEquals(Hand.maxSize() - removed, theHand.size());
            assertEquals(Hand.maxSize() - removed, theHand.getNumberOfCards(CardShape.SHAPE2));
        }

        // Double-check we're empty
        CardShape[] cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }

        // Add one of each
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE1);
        theHand.add(CardShape.SHAPE3);
        assertEquals(theHand.size(), 3);
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));

        // Then remove them, assuring everything removes as it should.
        theHand.remove(CardShape.SHAPE1);
        assertEquals(theHand.size(), 2);
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));

        theHand.remove(CardShape.SHAPE2);
        assertEquals(theHand.size(), 1);
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));

        theHand.remove(CardShape.SHAPE3);
        assertEquals(theHand.size(), 0);
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));

        // Assure nothing weird happens if you mix add/remove statements
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE2);
        theHand.remove(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE1);
        theHand.add(CardShape.SHAPE3);
        assertEquals(theHand.size(), 4);
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(1, theHand.getNumberOfCards(CardShape.SHAPE3));
        theHand.remove(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE3);
        theHand.remove(CardShape.SHAPE1);
        assertEquals(theHand.size(), 2);
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE3));
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE1);
        assertEquals(theHand.size(), 4);
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(2, theHand.getNumberOfCards(CardShape.SHAPE3));

        // Empty out the hand once more
        theHand.remove(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE3);
        theHand.remove(CardShape.SHAPE3);
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.NOCARD));
        cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }

        // Lastly, assure nothing happens when you remove cards from an empy hand.
        theHand.remove(CardShape.SHAPE2);
        theHand.remove(CardShape.SHAPE3);
        theHand.remove(CardShape.SHAPE1);
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.NOCARD));
        cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }
    }

    @Test
    public void TestClear() {

        CardShape[] cards;
        Hand theHand = new Hand();
        assertEquals(0, theHand.size());

        // Fill first
        theHand.fillRandomly();
        final CardShape[] filledCards = theHand.getAllCards();

        // Check we're full
        assertEquals(Hand.maxSize(), theHand.size());
        for (CardShape card : filledCards) {
            assertFalse(card == CardShape.NOCARD);
        }

        // Now clear
        theHand.clear();

        // Test for complete emptiness
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.NOCARD));
        cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }

        // Add a few cards
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE3);
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE1);
        theHand.remove(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE2);
        theHand.add(CardShape.SHAPE1);

        // Clear again
        theHand.clear();

        // Test for complete emptiness
        assertEquals(0, theHand.size());
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE1));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE2));
        assertEquals(0, theHand.getNumberOfCards(CardShape.SHAPE3));
        assertEquals(Hand.maxSize(), theHand.getNumberOfCards(CardShape.NOCARD));
        cards = theHand.getAllCards();
        assertEquals(Hand.maxSize(), cards.length);
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(cards[i], CardShape.NOCARD);
        }

    }

}

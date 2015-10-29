package test.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.Hand;

import org.junit.Test;

import shared.enums.CardShape;


public class TestHandRandom {

    /** Generates 4 hands that all have the same seed. */
    private ArrayList<Hand> genSeededHands(long seed) {
        ArrayList<Hand> playerHands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Hand hand = new Hand();
            hand.setSeed(seed);
            playerHands.add(hand);
        }
        return playerHands;
    }

    /** Checks if the hands provided all receive identical cards
     * after calling .fillRandomly() on each. */
    private void fillRandomlyCheck(ArrayList<Hand> playerHands) {
        // Tell them each to fill randomly after clearing them
        for (Hand hand : playerHands) {
            hand.clear();
            hand.fillRandomly();
        }

        // Check that they're all the same

        // Compare them all against the first
        CardShape[] firstHand = playerHands.get(0).getAllCards();
        for (int h = 1; h < playerHands.size(); h++) {
            CardShape[] nextHand = playerHands.get(h).getAllCards();

            // Compare each card in the both
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(firstHand[i], nextHand[i]);
                assertEquals(firstHand[i], nextHand[i]);
                assertEquals(firstHand[i], nextHand[i]);
            }
        }
    }

    /** Tests whether multiple hands, when given the same seed,
     * are 'randomly' filled with the exact same cards.
     *
     * Performs this test X number of times. */
    private void fillRandomly_X(int x) {

        // Generate a random seed
        long seed = System.nanoTime();
        System.out.println("fillRandomly_"+x+"()'s seed is:\n" + seed);

        // Generate 4 hands with the same seed
        ArrayList<Hand> playerHands = genSeededHands(seed);

        // Check that they get the same cards X times.
        for (int i = 0; i < x; i++) {
            fillRandomlyCheck(playerHands);
        }
    }

    @Test
    public void fillRandomly_1() {
        fillRandomly_X(1);
    }

    @Test
    public void fillRandomly_10() {
        fillRandomly_X(10);
    }

    @Test
    public void fillRandomly_100() {
        fillRandomly_X(100);
    }

    @Test
    public void fillRandomly_1000() {
        fillRandomly_X(1000);
    }

    @Test
    public void fillRandomly_10000() {
        fillRandomly_X(10000);
    }

    @Test
    public void fillRandomly_100000() {
        fillRandomly_X(100000);
    }

    @Test
    public void fillRandomly_1000000() {
        fillRandomly_X(1000000);
    }


}

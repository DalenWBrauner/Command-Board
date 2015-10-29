package test.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.Hand;

import org.junit.Test;

import shared.enums.CardShape;


public class TestHandRandom {

    @Test
    /** Tests whether multiple hands, when given the same seed,
     * are 'randomly' filled with the exact same cards. */
    public void fillRandomly_1() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_1()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        // Tell them each to fill randomly
        for (Hand hand : playerHands) {
            hand.fillRandomly();
        }

        // Whatever
        CardShape[] p1Hand = playerHands.get(0).getAllCards();
        CardShape[] p2Hand = playerHands.get(1).getAllCards();
        CardShape[] p3Hand = playerHands.get(2).getAllCards();
        CardShape[] p4Hand = playerHands.get(3).getAllCards();

        // Check that they're all the same
        for (int i = 0; i < Hand.maxSize(); i++) {
            assertEquals(p1Hand[i], p2Hand[i]);
            assertEquals(p1Hand[i], p3Hand[i]);
            assertEquals(p1Hand[i], p4Hand[i]);
        }
    }

    @Test
    public void fillRandomly_2() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_2()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        for (int randomChecks = 0; randomChecks < 2; randomChecks++) {

            // Tell them each to fill randomly
            for (Hand hand : playerHands) {
                hand.fillRandomly();
            }

            // Whatever
            CardShape[] p1Hand = playerHands.get(0).getAllCards();
            CardShape[] p2Hand = playerHands.get(1).getAllCards();
            CardShape[] p3Hand = playerHands.get(2).getAllCards();
            CardShape[] p4Hand = playerHands.get(3).getAllCards();

            // Check that they're all the same
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(p1Hand[i], p2Hand[i]);
                assertEquals(p1Hand[i], p3Hand[i]);
                assertEquals(p1Hand[i], p4Hand[i]);
            }

            // Clear them all
            for (Hand hand : playerHands) {
                hand.clear();
            }
        }
    }

    @Test
    public void fillRandomly_10() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_10()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        for (int randomChecks = 0; randomChecks < 10; randomChecks++) {

            // Tell them each to fill randomly
            for (Hand hand : playerHands) {
                hand.fillRandomly();
            }

            // Whatever
            CardShape[] p1Hand = playerHands.get(0).getAllCards();
            CardShape[] p2Hand = playerHands.get(1).getAllCards();
            CardShape[] p3Hand = playerHands.get(2).getAllCards();
            CardShape[] p4Hand = playerHands.get(3).getAllCards();

            // Check that they're all the same
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(p1Hand[i], p2Hand[i]);
                assertEquals(p1Hand[i], p3Hand[i]);
                assertEquals(p1Hand[i], p4Hand[i]);
                System.out.println(p1Hand[i] + "," + p2Hand[i]);
                System.out.println(p1Hand[i] + "," + p3Hand[i]);
                System.out.println(p1Hand[i] + "," + p4Hand[i]);
            }

            // Clear them all
            for (Hand hand : playerHands) {
                hand.clear();
            }
        }
    }

    @Test
    public void fillRandomly_100() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_100()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        for (int randomChecks = 0; randomChecks < 100; randomChecks++) {

            // Tell them each to fill randomly
            for (Hand hand : playerHands) {
                hand.fillRandomly();
            }

            // Whatever
            CardShape[] p1Hand = playerHands.get(0).getAllCards();
            CardShape[] p2Hand = playerHands.get(1).getAllCards();
            CardShape[] p3Hand = playerHands.get(2).getAllCards();
            CardShape[] p4Hand = playerHands.get(3).getAllCards();

            // Check that they're all the same
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(p1Hand[i], p2Hand[i]);
                assertEquals(p1Hand[i], p3Hand[i]);
                assertEquals(p1Hand[i], p4Hand[i]);
            }

            // Clear them all
            for (Hand hand : playerHands) {
                hand.clear();
            }
        }
    }

    @Test
    public void fillRandomly_10000() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_10000()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        for (int randomChecks = 0; randomChecks < 10000; randomChecks++) {

            // Tell them each to fill randomly
            for (Hand hand : playerHands) {
                hand.fillRandomly();
            }

            // Whatever
            CardShape[] p1Hand = playerHands.get(0).getAllCards();
            CardShape[] p2Hand = playerHands.get(1).getAllCards();
            CardShape[] p3Hand = playerHands.get(2).getAllCards();
            CardShape[] p4Hand = playerHands.get(3).getAllCards();

            // Check that they're all the same
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(p1Hand[i], p2Hand[i]);
                assertEquals(p1Hand[i], p3Hand[i]);
                assertEquals(p1Hand[i], p4Hand[i]);
            }

            // Clear them all
            for (Hand hand : playerHands) {
                hand.clear();
            }
        }
    }

    @Test
    public void fillRandomly_1000000() {

        long seed = System.nanoTime();
        System.out.println("fillRandomly_1000000()'s seed is:\n"+seed);

        // Create a hand for 4 players
        ArrayList<Hand> playerHands = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            playerHands.add(new Hand());
        }

        // Set them all to the same seed
        for (Hand hand : playerHands) {
            hand.setSeed(seed);
        }

        for (int randomChecks = 0; randomChecks < 1000000; randomChecks++) {

            // Tell them each to fill randomly
            for (Hand hand : playerHands) {
                hand.fillRandomly();
            }

            // Whatever
            CardShape[] p1Hand = playerHands.get(0).getAllCards();
            CardShape[] p2Hand = playerHands.get(1).getAllCards();
            CardShape[] p3Hand = playerHands.get(2).getAllCards();
            CardShape[] p4Hand = playerHands.get(3).getAllCards();

            // Check that they're all the same
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(p1Hand[i], p2Hand[i]);
                assertEquals(p1Hand[i], p3Hand[i]);
                assertEquals(p1Hand[i], p4Hand[i]);
            }

            // Clear them all
            for (Hand hand : playerHands) {
                hand.clear();
            }
        }
    }
}

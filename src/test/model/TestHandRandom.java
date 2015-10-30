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
//        System.out.println("nextHandNo, cardNo; firstHand Card, nextHand Card");

        // Compare them all against the first
        CardShape[] firstHand = playerHands.get(0).getAllCards();
        for (int h = 1; h < playerHands.size(); h++) {
            CardShape[] nextHand = playerHands.get(h).getAllCards();

            // Compare each card in the both
            for (int i = 0; i < Hand.maxSize(); i++) {
                assertEquals(firstHand[i], nextHand[i]);
//                System.out.println(h + ", " + i + "; " + firstHand[i] + "," + nextHand[i]);
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

    /** Each item in the outer arraylist represents a player's local copy of the game,
     * with each item in the inner arraylist representing a player's hand.
     *
     * We need to test if Player 1's hand on Player 2's game
     * is the same as Player 1's hand on Player 1, 3 and 4's games.
     *
     * Each player has a local copy of the game,
     * e.g. each player has a local copy of everyone's hands.
     * This means that if there are 4 people playing online together,
     * they each have a hand for P1, P2, P3 and P4.
     * We need to ensure these are kept consistent,
     * and I believe this can be done with seeding.
     * Let's try this out.
     */
    private ArrayList<ArrayList<Hand>> genSeededGames(long seed, int players) {

        // Establish a local copy of the game for each player
        ArrayList<ArrayList<Hand>> localCopies = new ArrayList<>();
        for (int p = 0; p < players; p++) {
            ArrayList<Hand> localCopy = new ArrayList<>();

            // Give each copy NUMPLAYERS hands
            for (int i = 0; i < players; i++) {
                localCopy.add(new Hand());
            }
            localCopies.add(localCopy);
        }

        // Now, each Player 1 should have the same seed,
        // each Player 2 should have the same seed, and so on.
        // Easiest way to do this is to just have ONE seed,
        // and modify it by some consistent amount.
        for (int p = 0; p < players; p++) { // For each player
            final long mySeed = seed + p;
            System.out.println("Player "+p+"'s 'unique' seed is \n"+mySeed);
            for (int g = 0; g < players; g++) { // For each game
                // Get each game's player "p"
                localCopies.get(g).get(p).setSeed(mySeed);
            }
        }
        // Seeds should exclusively be consistent among players now.
        return localCopies;

    }

    /** Each item in the outer arraylist represents a player's local copy of the game,
     * with each item in the inner arraylist representing a player's hand.
     *
     * We need to test if Player 1's hand on Player 2's game
     * is the same as Player 1's hand on Player 1, 3 and 4's games.
     */
    private void checkGamesConsistent(ArrayList<ArrayList<Hand>> localCopies) {
        final int NUMPLAYERS = localCopies.size();
        for (int p = 0; p < NUMPLAYERS; p++) { // For each player

            // Compare the first games' copy to the rest
            CardShape[] firstHand = localCopies.get(0).get(p).getAllCards();

            for (int c = 1; c < NUMPLAYERS; c++) { // For each copy
                CardShape[] nextHand = localCopies.get(c).get(p).getAllCards();

                // Compare each card in both
                for (int i = 0; i < Hand.maxSize(); i++) {
                    assertEquals(firstHand[i], nextHand[i]);
//                    System.out.println(firstHand[i] + "," + nextHand[i]);
                }
            }
        }
    }

    /** Simulates a game with four different people playing online. */
    private void KeepConsistent_X(int x) {
        final int NUMPLAYERS = 4;
        final long THESEED = System.nanoTime(); // Truly random long <3
        ArrayList<ArrayList<Hand>> localCopies = genSeededGames(THESEED, NUMPLAYERS);

        for (int i = 0; i < x; i++) {
            // Now that seeds are consistent amongst players, we can begin testing.
            // We'll start by making each player fill their hand randomly.
            for (ArrayList<Hand> copy : localCopies) {
                for (Hand hand : copy) {
                    hand.clear();
                    hand.fillRandomly();
                }
            }
            checkGamesConsistent(localCopies);
        }
    }

    @Test
    public void KeepConsistent_1() {
        KeepConsistent_X(1);
    }

    @Test
    public void KeepConsistent_10() {
        KeepConsistent_X(10);
    }

    @Test
    public void KeepConsistent_100() {
        KeepConsistent_X(100);
    }

    @Test
    public void KeepConsistent_1000() {
        KeepConsistent_X(1000);
    }

    @Test
    public void KeepConsistent_10000() {
        KeepConsistent_X(10000);
    }

    @Test
    public void KeepConsistent_100000() {
        KeepConsistent_X(100000);
    }
}

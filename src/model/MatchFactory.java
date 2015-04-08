package model;

import java.util.ArrayList;

public class MatchFactory {

    private static BoardFactory boardFactory = new BoardFactory();

    public Match createMatch(int numberOfPlayers, int cashGoal, String whichBoard) {
        System.out.println("MatchFactory.createMatch(); START");

        // Determine Turn Order
        int[] turnOrder = new int[numberOfPlayers];
        // for player in number of players assign a random value to each player
        // order based on the random value

        // Create Player Objects
        ArrayList<Player> thePlayers = new ArrayList<>();
        for (int p = 0; p < numberOfPlayers; p++) {
            thePlayers.add(new ActualPlayer());
        }

        // Print some stuff
        System.out.println("There are " + numberOfPlayers + " players.");
        for (int i = 0; i < turnOrder.length; i++) {
            System.out.println("Player "+turnOrder[i]+" goes on turn "+(i+1)+".");
        }
        System.out.println("We're playing on the "+whichBoard+" Board!");
        System.out.println("First one back to the start with $"+cashGoal+" wins!");

        // Create Board Object
        Board theBoard = boardFactory.getBoard(whichBoard);

        // Create Match Object
        Match theMatch = new Match(theBoard, thePlayers);

        System.out.println("MatchFactory.createMatch() END");

        return theMatch;
    }
}

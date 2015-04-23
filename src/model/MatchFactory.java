package model;

import java.util.ArrayList;

import model.ActualPlayer.PlayerID;

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
        if (numberOfPlayers > 0) {
            thePlayers.add(new ActualPlayer(PlayerID.PLAYER1));
        }
        if (numberOfPlayers > 1) {
            thePlayers.add(new ActualPlayer(PlayerID.PLAYER2));
        }
        if (numberOfPlayers > 2) {
            thePlayers.add(new ActualPlayer(PlayerID.PLAYER3));
        }
        if (numberOfPlayers > 3) {
            thePlayers.add(new ActualPlayer(PlayerID.PLAYER4));
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

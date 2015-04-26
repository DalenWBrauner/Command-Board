package model;

import java.util.ArrayList;
import java.util.Collections;

import shared.enums.PlayerID;

public class MatchFactory {

    private static BoardFactory boardFactory = new BoardFactory();

    public Match createMatch(int numberOfPlayers, int cashGoal, String whichBoard) {
        System.out.println("MatchFactory.createMatch(); START");

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

        // Shuffle the Turn Order
        Collections.shuffle(thePlayers);

        // Print some stuff
        System.out.println("There are " + numberOfPlayers + " players.");
        System.out.println("The turn order is:");
        for (Player player : thePlayers) {
            if (player.getID() == PlayerID.PLAYER1) {
                System.out.println("Player 1");
            } else if (player.getID() == PlayerID.PLAYER2) {
                System.out.println("Player 2");
            } else if (player.getID() == PlayerID.PLAYER3) {
                System.out.println("Player 3");
            } else if (player.getID() == PlayerID.PLAYER4) {
                System.out.println("Player 4");
            }

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

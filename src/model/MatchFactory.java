package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import model.command.Command;
import model.command.PrintCommand;
import shared.WatchTower;
import shared.enums.PlayerID;

public class MatchFactory {

    private final BoardFactory boardFactory = new BoardFactory();

    public Match createMatch(int numberOfPlayers, int cashGoal, String whichBoard) {
        System.out.println("MatchFactory.createMatch(); START");

        // Create player objects
        HashMap<PlayerID, Player> playerMap = new HashMap<>();
        if (numberOfPlayers > 0) {
            playerMap.put(PlayerID.PLAYER1,
                        new ActualPlayer(PlayerID.PLAYER1));
        }
        if (numberOfPlayers > 1) {
            playerMap.put(PlayerID.PLAYER2,
                    new ActualPlayer(PlayerID.PLAYER2));
        }
        if (numberOfPlayers > 2) {
            playerMap.put(PlayerID.PLAYER3,
                    new ActualPlayer(PlayerID.PLAYER3));
        }
        if (numberOfPlayers > 3) {
            playerMap.put(PlayerID.PLAYER4,
                    new ActualPlayer(PlayerID.PLAYER4));
        }

        // Shuffle the Turn Order
        ArrayList<PlayerID> turnOrder = new ArrayList<>(playerMap.keySet());
        Collections.shuffle(turnOrder);

        // Print some stuff
        System.out.println("The " + numberOfPlayers + " players play in this order:");
        for (PlayerID id : turnOrder) {
            System.out.println(id);
        }
        System.out.println("We're playing on the "+whichBoard+" Board!");
        System.out.println("First one back to the start with $"+cashGoal+" wins!");

        // Instantiate the WatchTower
        WatchTower tower = new WatchTower();

        // Create Board Object
        boardFactory.setWatchTower(tower);
        boardFactory.setPlayerMap(playerMap);
        Board theBoard = boardFactory.getBoard(whichBoard);

        // Instantiate the command executed when a Player's balance falls below zero
        Command onNegativeCommand = new PrintCommand("balance fell below zero!");
        onNegativeCommand.addObserver(tower);

        // Assign each Player's...
        for (PlayerID id : turnOrder) {
            Player player = playerMap.get(id);

            // ..position to the Start
            player.setPosition(theBoard.getStartX(), theBoard.getStartY());
            player.setLastPosition(player.getX(), player.getY());

            // ...wallet the onNegativeCommand
            player.getWallet().setOnNegativeCommand(onNegativeCommand);

            // ...wallet $1000 to start with
            player.getWallet().addFunds(1000);
        }

        // Create the SpellCaster
        SpellCaster yensid = new SpellCaster(tower, theBoard, playerMap);

        // Create Match Object
        Match theMatch = new Match(cashGoal, theBoard, yensid, turnOrder, playerMap);
        tower.addObserver(theMatch);

        System.out.println("MatchFactory.createMatch() END");

        return theMatch;
    }
}

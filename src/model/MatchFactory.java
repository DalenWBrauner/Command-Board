package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Observer;
import java.util.Random;

import model.command.AddFundsCommand;
import model.command.Command;
import model.command.SellAnyTileCommand;
import model.command.SellTileCommand;
import shared.enums.PlayerID;

public class MatchFactory {

    private final BoardFactory boardFactory = new BoardFactory();

    public Match createMatch(int numPlayers, int cashGoal, String whichBoard) {
    	return createMatch(numPlayers, cashGoal, whichBoard, System.nanoTime());
    }

    public Match createMatch(int numPlayers, int cashGoal, String whichBoard, long seed) {
        System.out.println("MatchFactory.createMatch(); START");
        
        // Instantiate an Empty Match for Observation purposes
        Match theMatch = new Match();
        Observer observer = theMatch;
        
        // Instantiate our RNG
        Random rng = new Random(seed);

        // Instantiate Player containers
        PlayerID[] allIDs = PlayerID.getNPlayers(numPlayers);
        HashMap<PlayerID, Player> playerMap = new HashMap<>();
        HashMap<PlayerID, Long> playerSeeds = new HashMap<>(); // I'm not sure the best way to do this just yet
        ArrayList<PlayerID> turnOrder = new ArrayList<>();

        // Create Player objects & shuffle turn order
        for (PlayerID id : allIDs) {
            playerMap.put(id, new Player(id));
            turnOrder.add(id);
        }
        //Collections.shuffle(turnOrder);
        Collections.shuffle(turnOrder, rng);

        // Set the seed for each player
        for (PlayerID id : allIDs) {
            //playerSeeds.put(id, System.nanoTime());
        	playerSeeds.put(id, seed++);
            playerMap.get(id).setSeed(playerSeeds.get(id));
        }

        // Print some stuff
        System.out.println("The " + numPlayers + " players play in this order:");
        for (PlayerID id : turnOrder) {
            System.out.println(id);
        }
        System.out.println("We're playing on the "+whichBoard+" Board!");
        System.out.println("First one back to the start with $"+cashGoal+" wins!");

        // Create Board Object
        boardFactory.setObserver(observer);
        Board theBoard = boardFactory.getBoard(whichBoard);

        // Instantiate the command executed when a Player's balance falls below zero
        AddFundsCommand afc = new AddFundsCommand();
        SellTileCommand stc = new SellTileCommand(afc);
        Command onNegativeCommand = new SellAnyTileCommand(stc, theBoard);
        afc.addObserver(observer);
        stc.addObserver(observer);
        onNegativeCommand.addObserver(observer);

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

            // ...hand a random set of cards
            player.getHand().fillRandomly();
        }

        // Create the SpellCaster
        SpellCaster yensid = new SpellCaster(observer, theBoard, playerMap);

        // Create Match Object Properly
        theMatch.fillMatch(cashGoal, theBoard, yensid, turnOrder, playerMap);

        System.out.println("MatchFactory.createMatch() END");

        return theMatch;
    }
}

package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import model.command.AddFundsCommand;
import model.command.Command;
import model.command.SellAnyTileCommand;
import model.command.SellTileCommand;
import shared.WatchTower;
import shared.enums.PlayerID;

public class MatchFactory {

    private final BoardFactory boardFactory = new BoardFactory();

    public Match createMatch(int numPlayers, int cashGoal, String whichBoard) {
    	return createMatch(numPlayers, cashGoal, whichBoard, System.nanoTime());
    }

    public Match createMatch(int numPlayers, int cashGoal, String whichBoard, long seed) {
        System.out.println("MatchFactory.createMatch(); START");
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

        // Instantiate the WatchTower
        WatchTower tower = new WatchTower();

        // Create Board Object
        boardFactory.setWatchTower(tower);
        Board theBoard = boardFactory.getBoard(whichBoard);

        // Instantiate the command executed when a Player's balance falls below zero
        AddFundsCommand afc = new AddFundsCommand();
        SellTileCommand stc = new SellTileCommand(afc);
        Command onNegativeCommand = new SellAnyTileCommand(stc, theBoard);
        afc.addObserver(tower);
        stc.addObserver(tower);
        onNegativeCommand.addObserver(tower);

        // Assign each Player's...
        for (PlayerID id : turnOrder) {
            Player player = playerMap.get(id);

            // ..position to the Start
            player.setPosition(theBoard.getStartX(), theBoard.getStartY());
            player.setLastPosition(player.getX(), player.getY());

            // ...wallet the onNegativeCommand
            player.setBankruptcyCommand(onNegativeCommand);

            // ...wallet $1000 to start with
            player.addFunds(1000);

            // ...hand a random set of cards
            player.getHand().fillRandomly();
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

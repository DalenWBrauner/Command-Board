package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.tile.CheckpointTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class Match extends Observable implements Observer {

    private Board theBoard;
    private ArrayList<PlayerID> turnOrder     = new ArrayList<>();
    private HashMap<PlayerID, Player> players = new HashMap<>();
    private PlayerID currentPlayer;
    private PlayerID winner;
    private boolean matchIsOver;
    private int turnNumber;

    public Match(Board requestedBoard, ArrayList<Player> playersInTurnOrder) {
        System.out.println("new Match();");
        theBoard = requestedBoard;

        // Fills the maps with info from playersInTurnOrder.
        for (int i = 0; i < playersInTurnOrder.size(); i++) {
            Player eachPlayer = playersInTurnOrder.get(i);
            PlayerID id       = eachPlayer.getID();

            turnOrder.add(id);
            players.put(id, eachPlayer);
        }

        winner = PlayerID.NOPLAYER;
        matchIsOver = false;
    }


    /** Starts the game. */
    public void start() {
        System.out.println("Match.start(); START");

        turnNumber = 0;
        while (!matchIsOver) {
            // Setup the next player to start
            turnNumber++;
            currentPlayer = turnOrder.get((turnNumber - 1) % turnOrder.size());

            // Take that player's turn.
            matchIsOver = takeTurn();
        }

        // The Match is over!
        System.out.println("The match is over! " + winner.toString() + " wins!");
        System.out.println("Match.start(); END");
    }

    /** Takes the current player's turn.
     * @return true if and only if the game is over.
     */
    private boolean takeTurn() {

        // Setup
        System.out.print("\nTURN  " + turnNumber + ":\t");
        System.out.println("It's "+ currentPlayer.toString() + "'s turn!");
        boolean winCondition = false;

        // SpellCasting
        // TODO Replace this section with a call to a SpellCaster object
        // e.g. SpellCaster.prepare(currentPlayer);
        // First, determine the spells the player can cast.
        SpellID[] castableSpells = getPlayer(currentPlayer).getHand().getCastableSpells();

        // Second, ask that player which of those spells they'd like to cast.
        PlayerRepresentative currentRep = players.get(currentPlayer).getRepresentative();
        SpellID spellCast = currentRep.getSpellCast(castableSpells);

        // Third, cast that spell.
        cast(spellCast);

        // Move the player!
        BoardIterator itr = new BoardIterator(players.get(currentPlayer), theBoard);
        itr.addObserver(this); // We want to be notified when the BoardIterator moves players
        winCondition = itr.go();

        // Be prepared if someone won
        if (winCondition) winner = currentPlayer;
        return winCondition;
    }

    /** Returns the list of PlayerIDs (in turn order). */
    @SuppressWarnings("unchecked")
    public ArrayList<PlayerID> getAllPlayerIDs() {
        return (ArrayList<PlayerID>) turnOrder.clone();
    }

    /** Returns the Player given its ID.*/
    public Player getPlayer(PlayerID thisPlayer) {
        return players.get(thisPlayer);
    }

    /** Returns the current Board. */
    public Board getBoard() {
        return theBoard;
    }

    /** Returns the Tile object at a given position on the board. */
    public Tile getTile(int x, int y) {
        return theBoard.getTile(x, y);
    }

    /** Return the ID of the player whose turn it is. */
    public PlayerID getCurrentPlayerID() {
        return currentPlayer;
    }

    /** Return which turn is being taken.
     * (the number of turns taken by all players + 1)
    */
    public int getNumberOfTurns() {
        return turnNumber;
    }

    /** Returns whether or not the Match has ended.
     * @return true if the Match is over.
     */
    public boolean isTheMatchOver() {
        return matchIsOver;
    }

    /** Returns the winner of the game!
     * If the game isn't over yet, returns NOPLAYER.
     * @return The PlayerID of the winning Player.
     */
    public PlayerID whoWon() {
        if (matchIsOver) {
            return winner;
        } else {
            return PlayerID.NOPLAYER;
        }
    }
}

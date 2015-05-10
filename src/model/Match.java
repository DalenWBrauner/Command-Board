package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.tile.Tile;

import org.apache.commons.lang3.mutable.MutableBoolean;

import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;

public class Match extends Observable implements Observer, Runnable {

    private Board theBoard;
    private ArrayList<PlayerID> turnOrder     = new ArrayList<>();
    private HashMap<PlayerID, Player> players = new HashMap<>();
    private PlayerID currentPlayer;
    private PlayerID winner;
    private MutableBoolean matchIsOver;
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
        matchIsOver = new MutableBoolean(false);
    }

    /** Starts the game. */
    @Override
    public void run() {
        System.out.println("Match.start(); START");

        turnNumber = 0;
        while (!matchIsOver.booleanValue()) {
            // Setup the next player to start
            turnNumber++;
            currentPlayer = turnOrder.get((turnNumber - 1) % turnOrder.size());

            // Take that player's turn.
            takeTurn();
        }

        // The Match is over!
        System.out.println("The match is over! " + winner.toString() + " wins!");
        System.out.println("Match.start(); END");
    }

    /** Takes the current player's turn.
     * @return true if and only if the game is over.
     */
    private void takeTurn() {

        // Setup
        System.out.print("\nTURN  " + turnNumber + ":\t");
        System.out.println("It's "+ currentPlayer.toString() + "'s turn!");

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
        BoardIterator itr = new BoardIterator(players.get(currentPlayer), theBoard, matchIsOver);
        itr.addObserver(this); // We want to be notified when the BoardIterator moves players
        itr.go();
    }

    /** Passes news of an update onto its observers. */
    @Override
    public void update(Observable o, Object arg) {
        update();
    }

    /** Passes news of an update onto its observers,
     * but checks if anyone has won, first!
     */
    private void update() {
        //System.out.println("Match.update()");
        checkForVictory();
        setChanged();
        notifyObservers();
    }

    /** Checks if any Player has won the Match.
     * If so, declares that Player the winner! */
    private void checkForVictory() {
        final int startX = theBoard.getStartX();
        final int startY = theBoard.getStartY();

        // TODO: Implement real victory condition
        // Check if any player is at the start
        for (Player p : getAllPlayers()) {
            if (p.getX() == startX &&
                p.getY() == startY) {

                // If that player has a full hand
                if (p.getHand().size() == Hand.maxSize()) {
                    declareWinner(p.getID());
                }
            }
        }
    }

    /** Sets the given player as the winner and ends the Match! */
    private void declareWinner(PlayerID winningPlayer) {
        winner = winningPlayer;
        matchIsOver.setTrue();
        setChanged();
        notifyObservers();
    }

    /** Temporary function for casting spells. */
    private void cast(SpellID spellCast) {
        if (spellCast == SpellID.NOSPELL) {
            return;
        }
    }

    // Getters

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

    /** Returns the list of PlayerIDs (in turn order). */
    @SuppressWarnings("unchecked")
    public ArrayList<PlayerID> getAllPlayerIDs() {
        return (ArrayList<PlayerID>) turnOrder.clone();
    }

    /** Returns the Player given its ID.*/
    public Player getPlayer(PlayerID thisPlayer) {
        return players.get(thisPlayer);
    }

    /** Returns the list of Player objects (in turn order). */
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (PlayerID pID : getAllPlayerIDs()) {
            allPlayers.add(getPlayer(pID));
        }
        return allPlayers;
    }

    /** Return which turn is being taken.
     * (the number of turns taken by all players + 1) */
    public int getTurnNumber() {
        return turnNumber;
    }


    /** Returns whether or not the Match has ended.
     * @return true if the Match is over.
     */
    public boolean isTheMatchOver() {
        return matchIsOver.booleanValue();
    }

    /** Returns the winner of the game!
     * If the game isn't over yet, returns NOPLAYER.
     * @return The PlayerID of the winning Player.
     */
    public PlayerID whoWon() {
        if (matchIsOver.isTrue()) {
            return winner;
        } else {
            return PlayerID.NOPLAYER;
        }
    }

}

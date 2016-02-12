package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import model.player.Player;
import model.tile.Tile;

import org.apache.commons.lang3.mutable.MutableBoolean;

import shared.enums.PlayerID;

public class Match extends Observable implements Observer, Runnable {

    private final int cashGoal;
    private final Board theBoard;
    private final SpellCaster donald;
    private final ArrayList<PlayerID> turnOrder;
    private final HashMap<PlayerID, Player> players;
    private final MutableBoolean matchIsOver;
    private PlayerID currentPlayer;
    private PlayerID winner;
    private int turnNumber;

    public Match(int theCashGoal, Board requestedBoard, SpellCaster caster,
                 ArrayList<PlayerID> playerIDsInTurnOrder, HashMap<PlayerID, Player> idMap) {
        System.out.println("new Match();");
        cashGoal = theCashGoal;
        theBoard = requestedBoard;
        donald = caster;
        players = idMap;
        turnOrder = playerIDsInTurnOrder;
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
            try {
                takeTurn();
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // The Match is over!
        System.out.println("The match is over! " + winner.toString() + " wins!");
        update();

        System.out.println("Match.start(); END");
    }

    /** Takes the current player's turn.
     * @return true if and only if the game is over.
     * @throws RemoteException
     */
    private void takeTurn() throws RemoteException {
        System.out.print("\nTURN  " + turnNumber + ":\t");
        System.out.println("It's "+ currentPlayer.toString() + "'s turn!");
        System.out.println("They have in their wallet: $" +
                           getPlayer(currentPlayer).getWallet().getCashOnHand() + "!");
        System.out.println("They have a net value of:  $" +
                           getPlayer(currentPlayer).getWallet().getNetValue() + "!");

        // Let the Player cast a spell!
        donald.performMagic(getPlayer(currentPlayer));

        // Move the Player!
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

        // Check if any player is at the start
        for (Player p : getAllPlayers()) {
            if (p.getX() == startX &&
                p.getY() == startY) {

                // If that player has more $$ than the Cash Goal, they win!
                if (p.getWallet().getNetValue() > cashGoal) {
                    declareWinner(p.getID());
                }
            }
        }
    }

    /** Sets the given player as the winner and ends the Match! */
    private void declareWinner(PlayerID winningPlayer) {
        winner = winningPlayer;
        matchIsOver.setTrue();
    }

    // Getters

    /** Returns the amount of money needed to win! */
    public int getCashGoal() {
        return cashGoal;
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

    /** Returns the Player given its ID.*/
    public Player getPlayer(PlayerID thisPlayer) {
        return players.get(thisPlayer);
    }

    /** Returns the list of PlayerIDs (in turn order). */
    @SuppressWarnings("unchecked")
    public ArrayList<PlayerID> getTurnOrder() {
        return (ArrayList<PlayerID>) turnOrder.clone();
    }

    /** Returns the list of Player objects (in turn order). */
    public ArrayList<Player> getAllPlayers() {
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (PlayerID pID : getTurnOrder()) {
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

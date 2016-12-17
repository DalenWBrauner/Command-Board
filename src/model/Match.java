package model;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.mutable.MutableBoolean;

import model.tile.Tile;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

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
    
    // BoardIterator
    private static final int northY = -1;
    private static final int southY = +1;
    private static final int westX  = -1;
    private static final int eastX  = +1;

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
        go();
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

    // BoardIterator
    /** Physically moves the player across the board until their turn is over.
     * @throws RemoteException */
    public void go() throws RemoteException {
    	Player movingPlayer = players.get(currentPlayer);
        PlayerRepresentative currentRep = movingPlayer.getRepresentative();

        // Get the player's dice roll
        int diceRoll = currentRep.getUsersRoll();
        assert(diceRoll > 0);
        assert(diceRoll < 7);
        System.out.println(currentPlayer+" rolled a "+diceRoll+"!");

        System.out.println("Moving from ("+
        movingPlayer.getX()+", "+movingPlayer.getY()+")...");

        // While we're still passing by tiles
        boolean justPassingBy = true;
        while (diceRoll > 1) {

            // Choose which way we're going
            final CardinalDirection chosenPath = pickAPath(movingPlayer, currentRep);

            // Move the player in that direction
            moveTo(movingPlayer, chosenPath, justPassingBy);

            // Decrement the dice roll
            diceRoll--;

            // Short-circuit if someone wins
            if (matchIsOver.booleanValue()) return;
        }

        // Now we're going to land on a tile
        justPassingBy = false;

        // Choose which way we're going
        final CardinalDirection chosenPath = pickAPath(movingPlayer, currentRep);

        // Move the player in that direction
        moveTo(movingPlayer, chosenPath, justPassingBy);
        System.out.println(currentPlayer+" is done moving!");
    }

    /** Discern which of the available paths the Player is going to move in.
     * @throws RemoteException */
    private CardinalDirection pickAPath(Player movingPlayer, PlayerRepresentative currentRep) throws RemoteException {
        CardinalDirection chosenPath;
        CardinalDirection[] availablePaths = getAvailablePaths(movingPlayer);

        // If there's only one way we can go, that's where we're going
        assert(availablePaths.length > 0);
        if (availablePaths.length == 1) {
            chosenPath = availablePaths[0];

        // Otherwise, ask the representative
        } else {
            chosenPath =  currentRep.forkInTheRoad(availablePaths);

            // Assert this matches a valid option
            boolean validOption = false;
            for (CardinalDirection validPath : availablePaths) {
                if (chosenPath == validPath) validOption = true;
            }
            assert(validOption);
        }

        return chosenPath;
    }

    /** Return all possible paths the player can move to. */
    private CardinalDirection[] getAvailablePaths(Player movingPlayer) {
        ArrayList<CardinalDirection> availablePaths = new ArrayList<>();

        // Get the player's position
        int xPos = movingPlayer.getX();
        int yPos = movingPlayer.getY();

        // Get the player's last position
        int lastX = movingPlayer.getLastX();
        int lastY = movingPlayer.getLastY();

        // Get the types of each adjacent tile
        TileType northType = theBoard.getTile(xPos, yPos + northY).getTileType();
        TileType southType = theBoard.getTile(xPos, yPos + southY).getTileType();
        TileType eastType  = theBoard.getTile(xPos + eastX,  yPos).getTileType();
        TileType westType  = theBoard.getTile(xPos + westX,  yPos).getTileType();

        // Get which direction you just came from
        boolean wasLastNorth = (xPos == lastX && yPos + northY == lastY);
        boolean wasLastSouth = (xPos == lastX && yPos + southY == lastY);
        boolean wasLastEast  = (xPos + eastX  == lastX && yPos == lastY);
        boolean wasLastWest  = (xPos + westX  == lastX && yPos == lastY);

        // Add each available direction
        if (!wasLastNorth && northType != TileType.NONE) {
            availablePaths.add(CardinalDirection.NORTH);
        }
        if (!wasLastSouth && southType != TileType.NONE) {
            availablePaths.add(CardinalDirection.SOUTH);
        }
        if (!wasLastEast  && eastType  != TileType.NONE) {
            availablePaths.add(CardinalDirection.EAST );
        }
        if (!wasLastWest  && westType  != TileType.NONE) {
            availablePaths.add(CardinalDirection.WEST );
        }

        // Convert to array
        assert(availablePaths.size() > 0);
        CardinalDirection[] asArray = new CardinalDirection[availablePaths.size()];
        availablePaths.toArray(asArray);
        assert(asArray.length > 0);
        return asArray;
    }

    /** Physically moves the player in the given direction.
     * Calls the Tile's onPass() or onLand() functions.
     * @throws RemoteException */
    private void moveTo(Player movingPlayer, CardinalDirection chosenPath, boolean justPassingBy) throws RemoteException {

        // Get where the Player is & was
        int xPos = movingPlayer.getX();
        int yPos = movingPlayer.getY();
        int movedFromX = xPos;
        int movedFromY = yPos;

        // Adjust the coordinates to that of the new tile
        if      (chosenPath == CardinalDirection.NORTH) yPos--;
        else if (chosenPath == CardinalDirection.SOUTH) yPos++;
        else if (chosenPath == CardinalDirection.EAST)  xPos++;
        else if (chosenPath == CardinalDirection.WEST)  xPos--;
        else assert(false); // This should never happen (EVER)

        // Actually move to that tile
        movingPlayer.setX(xPos);
        movingPlayer.setY(yPos);
        movingPlayer.setLastPosition(movedFromX, movedFromY);

        // Call that Tile's .onPass() or .onLand() (or both)
        Tile movedTo = theBoard.getTile(xPos, yPos);
        if (justPassingBy) {
            movedTo.onPass(movingPlayer);
        } else {
            movedTo.onPass(movingPlayer);
            update();                       // Update inbetween!
            movedTo.onLand(movingPlayer);
        }

        // Update afterwards
        update();
        System.out.println("Finished moving to ("+xPos+", "+yPos+")!");
    }
    
}

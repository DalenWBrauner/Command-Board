package model;

import java.util.ArrayList;
import java.util.Observable;

import model.tile.Tile;

import org.apache.commons.lang3.mutable.MutableBoolean;

import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class BoardIterator extends Observable {

    // Modifiers
    private final static int northY = -1;
    private final static int southY = +1;
    private final static int westX  = -1;
    private final static int eastX  = +1;

    private final Board    theBoard;
    private final PlayerID movingPlayerID;
    private final Player   movingPlayer;
    private final MutableBoolean  matchIsOver;

    public BoardIterator(Player currentPlayer, Board currentBoard, MutableBoolean isOver) {
        theBoard = currentBoard;
        movingPlayer = currentPlayer;
        movingPlayerID = currentPlayer.getID();
        matchIsOver = isOver;
    }

    /** Physically moves the player across the board until their turn is over.
     * @return Whether or not the current player has won
     */
    public void go() {
        PlayerRepresentative currentRep = movingPlayer.getRepresentative();

        System.out.println(movingPlayerID+" is moving from ("+
        movingPlayer.getX()+", "+movingPlayer.getY()+")");

        // Get the player's dice roll
        int diceRoll = currentRep.getUsersRoll();
        assert(diceRoll > 0);
        assert(diceRoll < 7);

        System.out.println(movingPlayerID+" rolled a "+diceRoll+"!");

        // While we're still passing by tiles
        boolean justPassingBy = true;
        while (diceRoll > 1) {

            // Choose which way we're going
            final CardinalDirection chosenPath = pickAPath(currentRep);

            // Move the player in that direction
            moveTo(chosenPath, justPassingBy);

            // Decrement the dice roll
            diceRoll--;

            // Short-circuit if someone wins
            if (matchIsOver.booleanValue()) return;
        }

        // Now we're going to land on a tile
        justPassingBy = false;

        // Choose which way we're going
        final CardinalDirection chosenPath = pickAPath(currentRep);

        // Move the player in that direction
        moveTo(chosenPath, justPassingBy);
        System.out.println(movingPlayerID+" is done moving!");
    }

    /** Discern which of the available paths the Player is going to move in. */
    private CardinalDirection pickAPath(PlayerRepresentative currentRep) {
        CardinalDirection chosenPath;
        CardinalDirection[] availablePaths = getAvailablePaths();

        // If there's only one way we can go, that's where we're going
        assert(availablePaths.length > 0);
        if (availablePaths.length == 1) {
            chosenPath = availablePaths[0];

        // Otherwise, ask the representative
        } else {
            chosenPath =  currentRep.forkInTheRoad(availablePaths);
        }

        return chosenPath;
    }

    /** Return all possible paths the player can move to. */
    private CardinalDirection[] getAvailablePaths() {
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
     * Calls the Tile's onPass() or onLand() functions. */
    private void moveTo(CardinalDirection chosenPath, boolean justPassingBy) {

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

    /** Notifies the observers of an update. */
    private void update() {
        //System.out.println("BoardIterator.update()");
        setChanged();
        notifyObservers();
    }
}

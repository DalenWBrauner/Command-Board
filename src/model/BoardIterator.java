package model;

import java.util.ArrayList;
import java.util.Observable;

import model.tile.Tile;
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

    private final Match    theMatch;
    private final Board    theBoard;
    private final PlayerID movingPlayerID;
    private final Player   movingPlayer;

    public BoardIterator(Match currentMatch) {
        theMatch = currentMatch;
        theBoard = currentMatch.getBoard();
        movingPlayerID = currentMatch.getCurrentPlayerID();
        movingPlayer = currentMatch.getPlayer(currentMatch.getCurrentPlayerID());
    }

    /** Physically moves the player across the board until their turn is over.
     * @return Whether or not the current player has won
     */
    public boolean go() {
        boolean playerWon = false;
        PlayerRepresentative currentRep = theMatch.getRepresentative(movingPlayerID);

        System.out.println(movingPlayerID+" is moving from ("+
        movingPlayer.getX()+", "+movingPlayer.getY()+")");

        // Get the player's dice roll
        int diceRoll = currentRep.getUsersRoll();
        assert(diceRoll > 0);
        assert(diceRoll < 7);

        System.out.println(movingPlayerID+" rolled a "+diceRoll+"!");

        // While we're still passing by tiles
        boolean passingBy = true;
        while (diceRoll > 1) {

            // Choose which way we're going
            final CardinalDirection chosenPath = pickAPath(currentRep);

            // Move the player in that direction
            playerWon = moveTo(chosenPath, passingBy);

            // Update the Board
            theMatch.hasChanged();
            theMatch.notifyObservers();

            // Decrement the dice roll
            diceRoll--;

            // If the winner has been declared, quit early
            if (playerWon) return playerWon;
        }

        // Now we're going to land on a tile
        passingBy = false;

        // Choose which way we're going
        final CardinalDirection chosenPath = pickAPath(currentRep);

        // Move the player in that direction
        playerWon = moveTo(chosenPath, passingBy);

        System.out.println(movingPlayerID+" is done moving!");

        // Update the Board
        theMatch.hasChanged();
        theMatch.notifyObservers();

        // The player's turn is over!
        return playerWon;
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

//        System.out.println("Player was last at ("+lastX+", "+lastY+")");

//        System.out.println("I believe the tile north of me is at ("+xPos+", "+(yPos + northY)+")");
//        System.out.println("I believe the tile south of me is at ("+xPos+", "+(yPos + southY)+")");
//        System.out.println("I believe the tile  east of me is at ("+(xPos + eastX)+", "+yPos+")");
//        System.out.println("I believe the tile  west of me is at ("+(xPos + westX)+", "+yPos+")");

        // Get the types of each adjacent tile
        TileType northType = theBoard.getTile(xPos, yPos + northY).getTileType();
        TileType southType = theBoard.getTile(xPos, yPos + southY).getTileType();
        TileType eastType  = theBoard.getTile(xPos + eastX,  yPos).getTileType();
        TileType westType  = theBoard.getTile(xPos + westX,  yPos).getTileType();

//        System.out.println("Tile northType: "+northType);
//        System.out.println("Tile southType: "+southType);
//        System.out.println("Tile  eastType: "+ eastType);
//        System.out.println("Tile  westType: "+ westType);

        // Get which direction you just came from
        boolean wasLastNorth = (xPos == lastX && yPos + northY == lastY);
        boolean wasLastSouth = (xPos == lastX && yPos + southY == lastY);
        boolean wasLastEast  = (xPos + eastX  == lastX && yPos == lastY);
        boolean wasLastWest  = (xPos + westX  == lastX && yPos == lastY);

//        System.out.println("wasLastNorth: "+wasLastNorth);
//        System.out.println("wasLastSouth: "+wasLastSouth);
//        System.out.println("wasLastEast:  "+wasLastEast );
//        System.out.println("wasLastWest:  "+wasLastWest );

        // Add each available direction
        if        (northType != TileType.NONE && !wasLastNorth) {
            availablePaths.add(CardinalDirection.NORTH);
//            System.out.println("The player can move north!");
        } else {
//            System.out.println("The player cannot move north!");
        }
        if (southType != TileType.NONE && !wasLastSouth) {
            availablePaths.add(CardinalDirection.SOUTH);
//            System.out.println("The player can move south!");
        } else {
//            System.out.println("The player cannot move south!");
        }
        if (eastType  != TileType.NONE && !wasLastEast ) {
            availablePaths.add(CardinalDirection.EAST );
//            System.out.println("The player can move east!");
        } else {
//            System.out.println("The player cannot move east!");
        }
        if (westType  != TileType.NONE && !wasLastWest ) {
            availablePaths.add(CardinalDirection.WEST );
//            System.out.println("The player can move west!");
        } else {
//            System.out.println("The player cannot move west!");
        }

        // Convert to array
        assert(availablePaths.size() > 0);
        CardinalDirection[] retVal = new CardinalDirection[availablePaths.size()];
//        for (int i = 0; i < availablePaths.size(); i++) {
//            retVal[i] = availablePaths.get(i);
//        }
        availablePaths.toArray(retVal);
        assert(retVal.length > 0);
        return retVal;
    }

    /** Physically moves the player in the given direction.
     * Calls the Tile's onPass() or onLand() functions */
    private boolean moveTo(CardinalDirection chosenPath, boolean passingBy) {
        boolean wonYet = false;

        int xPos = movingPlayer.getX();
        int yPos = movingPlayer.getY();
        int lastX = xPos;
        int lastY = yPos;

        // Get the coordinates of the new tile
        if (chosenPath == CardinalDirection.NORTH)      yPos--;
        else if (chosenPath == CardinalDirection.SOUTH) yPos++;
        else if (chosenPath == CardinalDirection.EAST)  xPos++;
        else if (chosenPath == CardinalDirection.WEST)  xPos--;
        else assert(false); // This should never happen (EVER)

        // Actually move to that tile
        movingPlayer.setX(xPos);
        movingPlayer.setY(yPos);
        movingPlayer.setLastPosition(lastX, lastY);

        Tile movedTo = theBoard.getTile(xPos, yPos);
        if (passingBy)      wonYet = movedTo.onPass();
        else                wonYet = movedTo.onLand();

        System.out.println("Moved to ("+xPos+", "+yPos+")");

        // Bootleg victory condition
        if (movedTo.getTileType() == TileType.START) wonYet = true;

        // Return if we've won
        return wonYet;
    }
}

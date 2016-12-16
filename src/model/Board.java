package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Observable;

import org.apache.commons.lang3.mutable.MutableBoolean;

import model.tile.CheckpointTile;
import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.StartTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class Board extends Observable implements Serializable {
	private static final long serialVersionUID = 2454401015775985663L;
	
    // Board Iterator Vars
    private static final int northY = -1;
    private static final int southY = +1;
    private static final int westX  = -1;
    private static final int eastX  = +1;
    
    private PlayerID movingPlayerID;
    private Player	 movingPlayer;
    private MutableBoolean  matchIsOver;
    
    // Board Vars
	
	private static final int BOARD_WIDTH = 15;
    private static final int BOARD_HEIGHT = 15;

    private int startX = 0;
    private int startY = 0;

    private boolean finished;
    private String boardName = "untitled";

    private Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

    public Board() {
        System.out.println("new Board();");
        movingPlayerID = PlayerID.NOPLAYER;
        movingPlayer = null;
        
        finished = false;

        for (int x = 0; x < BOARD_WIDTH; x++) {
            for (int y = 0; y < BOARD_HEIGHT; y++) {
                tiles[x][y] = new NullTile(x, y);
            }
        }
    }

    /** Sets the Board as having been completed, preventing it from being
     * edited from here on.
     */
    public void setFinished() {
        finished = true;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setName(String name) {
        assert(!finished);
        boardName = name;
    }

    public void addTile(Tile tile) {
        assert(!finished);
        tiles[tile.getX()][tile.getY()] = tile;
    }

    public void addStartingTile(StartTile startTile) {
        assert(!finished);
        startX = startTile.getX();
        startY = startTile.getY();
        tiles[startX][startY] = startTile;
    }

    public Tile getTile(int x, int y) {
        // If out-of-bounds, return a null tile
        if (x >= tiles.length || y >= tiles[0].length || x < 0 || y < 0) {
            return new NullTile();

        // Otherwise return the tile
        } else {
            return tiles[x][y];
        }
    }

    public Tile getTile(int[] positions) {
        // Return a null tile in cases of invalid input
        if (positions.length != 2) {
            return new NullTile();

        // Otherwise return the tile
        } else {
            return getTile(positions[0], positions[1]);
        }
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public int getHeight() {
        return BOARD_HEIGHT;
    }

    public String getName() {
        return boardName;
    }

    public int getStartX() {
        assert(finished);
        return startX;
    }

    public int getStartY() {
        assert(finished);
        return startY;
    }

    /** Returns detailed information about the current board. */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Command Board '"+boardName+"'.\n");

        // For each tile on the board,
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Tile thisTile = getTile(x,y);

                // If the tile exists,
                TileType tileType = thisTile.getTileType();
                if (tileType != TileType.NONE) {

                    // Print it out!
                    sb.append("The Tile at (" + x + ", " + y + ") is ");

                    if (tileType == TileType.CHECKPOINT) {
                        // If you're a checkpoint, print out your color!
                        CheckpointColor whichColor = ((CheckpointTile) thisTile).getColor();
                        sb.append("the " + whichColor.toString() + " Checkpoint!\n");

                    } else if (tileType == TileType.PROPERTY) {

                        // Display Tile Level
                        sb.append("a Level " + ((PropertyTile) thisTile).getLevel()
                                + " Property Tile");

                        // Display Tile Owner
                        PlayerID tileOwner = ((PropertyTile) thisTile).getOwner().getID();
                        sb.append(" owned by " + tileOwner.toString());

                        // Display Card
                        sb.append(" with");
                        CardShape tileCard = ((PropertyTile) thisTile).getCard();

                        if (tileCard == CardShape.NOCARD) {
                            sb.append(" no card");
                        } else if (tileCard == CardShape.SHAPE1) {
                            sb.append(" a Shape1 card");
                        } else if (tileCard == CardShape.SHAPE2) {
                            sb.append(" a Shape2 card");
                        } else if (tileCard == CardShape.SHAPE3) {
                            sb.append(" a Shape3 card");
                        }

                        // Display Tile Value
                        sb.append(" and with a value of $");
                        sb.append( ((PropertyTile) thisTile).getValue()  );

                        // End of sentence
                        sb.append(".\n");

                    } else if (tileType == TileType.START) {
                        sb.append("the START!\n");
                    }
                }
            }
        }
        sb.append("\n");
        return sb.toString();
    }

    // Board Iterator Code

    public void setMovingPlayer(Player currentPlayer, MutableBoolean isOver) {
    	movingPlayer = currentPlayer;
        movingPlayerID = currentPlayer.getID();
        matchIsOver = isOver;
    }

    /** Physically moves the player across the board until their turn is over.
     * @throws RemoteException */
    public void go() throws RemoteException {
        PlayerRepresentative currentRep = movingPlayer.getRepresentative();

        // Get the player's dice roll
        int diceRoll = currentRep.getUsersRoll();
        assert(diceRoll > 0);
        assert(diceRoll < 7);
        System.out.println(movingPlayerID+" rolled a "+diceRoll+"!");

        System.out.println("Moving from ("+
        movingPlayer.getX()+", "+movingPlayer.getY()+")...");

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


    /** Discern which of the available paths the Player is going to move in.
     * @throws RemoteException */
    private CardinalDirection pickAPath(PlayerRepresentative currentRep) throws RemoteException {
        CardinalDirection chosenPath;
        CardinalDirection[] availablePaths = getAvailablePaths();

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
    private CardinalDirection[] getAvailablePaths() {
        ArrayList<CardinalDirection> availablePaths = new ArrayList<>();

        // Get the player's position
        int xPos = movingPlayer.getX();
        int yPos = movingPlayer.getY();

        // Get the player's last position
        int lastX = movingPlayer.getLastX();
        int lastY = movingPlayer.getLastY();

        // Get the types of each adjacent tile
        TileType northType = getTile(xPos, yPos + northY).getTileType();
        TileType southType = getTile(xPos, yPos + southY).getTileType();
        TileType eastType  = getTile(xPos + eastX,  yPos).getTileType();
        TileType westType  = getTile(xPos + westX,  yPos).getTileType();

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
    private void moveTo(CardinalDirection chosenPath, boolean justPassingBy) throws RemoteException {

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
        Tile movedTo = getTile(xPos, yPos);
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


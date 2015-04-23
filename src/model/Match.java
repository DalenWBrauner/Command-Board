package model;

import java.util.Collection;
import java.util.Observable;

import model.tile.CheckpointTile;
import model.tile.CheckpointTile.CheckpointColor;
import model.tile.Tile;
import model.tile.Tile.TileType;

public class Match extends Observable {

    private Board theBoard;
    private Collection<Player> players;

    public Match(Board requestedBoard, Collection<Player> playersInTurnOrder) {
        System.out.println("new Match();");
        theBoard = requestedBoard;
        players = playersInTurnOrder;
    }

    /** Starts the game */
    public void start() {
        System.out.println("Match.start(); START");

        // For each tile on the board,
        for (int x = 0; x < theBoard.getWidth(); x++) {
            for (int y = 0; y < theBoard.getHeight(); y++) {

                // Get the type of the tile,
                Tile thisTile = theBoard.getTile(x,y);
                TileType tileType = thisTile.getTileType();

                // And if the tile exists,
                if (tileType != TileType.NONE) {

                    // Print it out!
                    System.out.print("The Tile at (" + x + ", " + y + ") is ");


                    if (tileType == TileType.CHECKPOINT) {
                        // If you're a checkpoint, print out your color!
                        CheckpointColor whichColor = ((CheckpointTile) thisTile).getColor();
                        if (whichColor == CheckpointColor.RED) {
                            System.out.println("the Red Checkpoint!");
                        } else if (whichColor == CheckpointColor.BLU) {
                            System.out.println("the Blue Checkpoint!");
                        } else if (whichColor == CheckpointColor.GRN) {
                            System.out.println("the Green Checkpoint!");
                        } else if (whichColor == CheckpointColor.YLW) {
                            System.out.println("the Yellow Checkpoint!");
                        } else {
                            // If getColor() did not return one of the above enum values,
                            assert(false);
                        }

                    } else if (tileType == TileType.PROPERTY) {
                        System.out.println("a property tile!");

                    } else if (tileType == TileType.START) {
                        System.out.println("the START!");
                    }
                }
            }
        }
        System.out.println("Match.start(); END");
    }

    public Tile getTile(int x, int y) {
        return theBoard.getTile(x, y);
    }
}

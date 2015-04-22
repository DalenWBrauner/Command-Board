package model;

import java.util.Collection;
import java.util.Observable;

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

                // Print out their type
                TileType tileType = theBoard.getTile(x,y).getTileType();
                // (unless they don't exist)
                if (tileType != TileType.NONE) {
                    System.out.print("The Tile at (" + x + ", " + y + ") is ");

                } if (tileType == TileType.CHECKPOINT) {
                    System.out.println("a checkpoint!");
                } else if (tileType == TileType.PROPERTY) {
                    System.out.println("a property tile!");
                } else if (tileType == TileType.START) {
                    System.out.println("the starting tile!");
                }
            }
        }
        System.out.println("Match.start(); END");
    }

    public Tile getTile(int x, int y) {
        return theBoard.getTile(x, y);
    }
}

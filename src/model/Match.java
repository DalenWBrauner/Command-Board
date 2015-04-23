package model;

import java.util.ArrayList;
import java.util.Observable;

import model.ActualPlayer.PlayerID;
import model.Hand.CardShape;
import model.tile.CheckpointTile;
import model.tile.CheckpointTile.CheckpointColor;
import model.tile.PropertyTile;
import model.tile.Tile;
import model.tile.Tile.TileType;

public class Match extends Observable {

    private Board theBoard;
    private ArrayList<Player> players;

    public Match(Board requestedBoard, ArrayList<Player> playersInTurnOrder) {
        System.out.println("new Match();");
        theBoard = requestedBoard;
        players = playersInTurnOrder;
    }

    /** Starts the game. */
    public void start() {
        System.out.println("Match.start();");
    }

    /** Returns the Tile object at a given position on the board. */
    public Tile getTile(int x, int y) {
        return theBoard.getTile(x, y);
    }

    public void debug() {
        assert(players.size() == 4);
        System.out.println("Match.debug(); START");
        printBoard();

        Tile tileA = theBoard.getTile(2,2);
        if (tileA.getTileType() == TileType.PROPERTY) {
            ((PropertyTile) tileA).setCard(CardShape.SHAPE1);
            players.get(0).giveTile((PropertyTile) tileA);
        } else { assert(false); }
        System.out.println("Player 1 now owns the tile at (2,2)...");

        Tile tileB = theBoard.getTile(3,3);
        if (tileB.getTileType() == TileType.PROPERTY) {
            players.get(1).giveTile((PropertyTile) tileB);
            ((PropertyTile) tileB).setCard(CardShape.SHAPE2);
        } else { assert(false); }
        System.out.println("Player 2 now owns the tile at (3,3)...");

        Tile tileC = theBoard.getTile(5,4);
        if (tileC.getTileType() == TileType.PROPERTY) {
            players.get(2).giveTile((PropertyTile) tileC);
            ((PropertyTile) tileC).setCard(CardShape.SHAPE3);
        } else { assert(false); }
        System.out.println("Player 3 now owns the tile at (5,4)...");

        Tile tileD = theBoard.getTile(2,7);
        if (tileD.getTileType() == TileType.PROPERTY) {
            players.get(3).giveTile((PropertyTile) tileD);
            ((PropertyTile) tileD).setCard(CardShape.SHAPE1);
        } else { assert(false); }
        System.out.println("Player 4 now owns the tile at (2,7)...");
        System.out.println();

        printBoard();

        ((PropertyTile) tileA).setLevel(2);
        System.out.println("Player 1 has leveled up their tile by 1!");
        ((PropertyTile) tileB).setLevel(3);
        System.out.println("Player 2 has leveled up their tile by 2!");
        ((PropertyTile) tileC).setLevel(4);
        System.out.println("Player 3 has leveled up their tile by 3!");
        ((PropertyTile) tileD).setLevel(5);
        System.out.println("Player 4 has leveled up their tile by 4!");
        System.out.println();

        printBoard();
        System.out.println("Match.debug(); END");
    }

    /** Prints out basic information about the current board. */
    public void printBoard() {
     // For each tile on the board,
        for (int y = 0; y < theBoard.getHeight(); y++) {
            for (int x = 0; x < theBoard.getWidth(); x++) {
                Tile thisTile = theBoard.getTile(x,y);

                // If the tile exists,
                TileType tileType = thisTile.getTileType();
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

                        // Display Tile Level
                        System.out.print("a Level " + ((PropertyTile) thisTile).getLevel()
                                + " Property Tile");

                        // Display Tile Owner
                        System.out.print(" owned by");
                        PlayerID tileOwner = ((PropertyTile) thisTile).getOwner();

                        if (tileOwner == PlayerID.NOPLAYER) {
                            System.out.print(" nobody");
                        } else if (tileOwner == PlayerID.PLAYER1) {
                            System.out.print(" Player 1");
                        } else if (tileOwner == PlayerID.PLAYER2) {
                            System.out.print(" Player 2");
                        } else if (tileOwner == PlayerID.PLAYER3) {
                            System.out.print(" Player 3");
                        } else if (tileOwner == PlayerID.PLAYER4) {
                            System.out.print(" Player 4");
                        }

                        // Display Card
                        System.out.print(" with");
                        CardShape tileCard = ((PropertyTile) thisTile).getCard();

                        if (tileCard == CardShape.NOCARD) {
                            System.out.print(" no card");
                        } else if (tileCard == CardShape.SHAPE1) {
                            System.out.print(" a Shape1 card");
                        } else if (tileCard == CardShape.SHAPE2) {
                            System.out.print(" a Shape2 card");
                        } else if (tileCard == CardShape.SHAPE3) {
                            System.out.print(" a Shape3 card");
                        }

                        // Display Tile Value
                        System.out.print(" and with a value of $");
                        System.out.print( ((PropertyTile) thisTile).getValue()  );

                        // End of sentence
                        System.out.println(".");

                    } else if (tileType == TileType.START) {
                        System.out.println("the START!");
                    }
                }
            }
        }
        System.out.println();
    }
}

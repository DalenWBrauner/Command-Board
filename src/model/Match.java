package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

import model.tile.CheckpointTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.enums.TileType;
import shared.interfaces.NullRepresentative;
import shared.interfaces.PlayerRepresentative;

public class Match extends Observable {

    private Board theBoard;
    private ArrayList<PlayerID> turnOrder                      = new ArrayList<>();
    private HashMap<PlayerID, Player> players                  = new HashMap<>();
    private HashMap<PlayerID, PlayerRepresentative> playerReps = new HashMap<>();
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
            playerReps.put(id, new NullRepresentative(this));
            // These are just placeholder PlayerRepresentatives!
            // Real ones need to be assigned ASAP through setPlayerRepresentative()!
        }

        winner = PlayerID.NOPLAYER;
        matchIsOver = false;
    }

    /** Sets the representative for a given player.
     * Make sure to set these before the match starts!
     * (That said, this could easily be set later if Users, say, wanted their
     * Player to be replaced by AI.)
     */
    public void setRepresentative(PlayerID thisPlayer, PlayerRepresentative thisRep) {
        playerReps.replace(thisPlayer, thisRep);
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
        System.out.print("TURN  " + turnNumber + ":\t");
        System.out.println("It's "+ currentPlayer.toString() + "'s turn!");

        if (turnNumber >= 20) {
            winner = currentPlayer;
            return true;
        }
        return false;
    }

    /** Returns the Player given its ID.
     */
    public Player getPlayer(PlayerID thisPlayer) {
        return players.get(thisPlayer);
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
                        System.out.println("the " + whichColor.toString() + " Checkpoint!");

                    } else if (tileType == TileType.PROPERTY) {

                        // Display Tile Level
                        System.out.print("a Level " + ((PropertyTile) thisTile).getLevel()
                                + " Property Tile");

                        // Display Tile Owner
                        PlayerID tileOwner = ((PropertyTile) thisTile).getOwner();
                        System.out.print(" owned by " + tileOwner.toString());

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

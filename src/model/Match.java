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
import shared.enums.SpellID;
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
            playerReps.put(id, new NullRepresentative(this, id));
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

    /** Sets the representative for all current players. */
    public void setAllRepresentatives(PlayerRepresentative thisRep) {
        for (PlayerID id : playerReps.keySet()) {
            playerReps.replace(id, thisRep);
        }
    }

    /** Returns the representative for the given player.
     *
     * The idea is that objects inside the Match can ask the Match for the rep,
     * so that the Match doesn't have to try to hold any of the functions.
     *
     * @param thisPlayer The ID of the Player we need to ask a question.
     * @return The Representative for the given PlayerID.
     */
    public PlayerRepresentative getRepresentative(PlayerID thisPlayer) {
        return playerReps.get(thisPlayer);
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

        // Setup
        System.out.print("\nTURN  " + turnNumber + ":\t");
        System.out.println("It's "+ currentPlayer.toString() + "'s turn!");
        boolean winCondition = false;

        // SpellCasting
        // TODO Replace this section with a call to a SpellCaster object
        // e.g. SpellCaster.prepare(currentPlayer);
        // First, determine the spells the player can cast.
        SpellID[] castableSpells = getPlayer(currentPlayer).getHand().getCastableSpells();

        // Second, ask that player which of those spells they'd like to cast.
        SpellID spellCast = playerReps.get(currentPlayer).getSpellCast(castableSpells);

        // Third, cast that spell.
        cast(spellCast);

        // Move the player!
        BoardIterator itr = new BoardIterator(this);
        winCondition = itr.go();

        // Be prepared if someone won
        if (winCondition) winner = currentPlayer;
        return winCondition;
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

    /** Temporary function for casting spells */
    private void cast(SpellID spellCast) {
        if (spellCast == SpellID.NOSPELL) {
            return;
        }
    }

}

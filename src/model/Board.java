package model;

import model.tile.CheckpointTile;
import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.StartTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.enums.TileType;

public class Board {

    private final static int BOARD_WIDTH = 15;
    private final static int BOARD_HEIGHT = 15;

    private int startX = 0;
    private int startY = 0;

    private boolean finished;
    private String boardName = "untitled";

    private Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

    public Board() {
        System.out.println("new Board();");
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
                        PlayerID tileOwner = ((PropertyTile) thisTile).getOwner();
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

}


package model;

import model.tile.NullTile;
import model.tile.Tile;

public class Board {

    private final static int BOARD_WIDTH = 15;
    private final static int BOARD_HEIGHT = 15;

    private boolean isFinished;

    private Tile[][] tiles = new Tile[BOARD_WIDTH][BOARD_HEIGHT];

    public Board() {
        System.out.println("new Board();");
        isFinished = false;

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
        isFinished = true;
    }

    public void addTile(Tile tile) throws FinishedBoardException {
        if (!isFinished) { throw new FinishedBoardException(); }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public int getWidth() {
        return BOARD_WIDTH;
    }

    public int getHeight() {
        return BOARD_HEIGHT;
    }

}

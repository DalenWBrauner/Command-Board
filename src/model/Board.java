package model;

import java.util.Collection;

import model.tile.Tile;

public class Board {

    private boolean isFinished;

    private Collection<Tile> tiles;

    public Board() {
        System.out.println("new Board();");
        isFinished = false;
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

    /*
    public getTile(int x, int y) {
        // Do we return null if there's no tile there?
        // How do we store the tile data so we know what the x and y positions are?
        return
    }
    */
}

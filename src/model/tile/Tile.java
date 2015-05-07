package model.tile;

import shared.enums.TileType;

public abstract class Tile {

    private int xPos;
    private int yPos;

    public Tile(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }

    abstract public TileType getTileType();

    // Super bootleg and temporary
    public boolean onPass() {
        return false;
    }

    // Super bootleg and temporary
    public boolean onLand() {
        return false;
    }
}

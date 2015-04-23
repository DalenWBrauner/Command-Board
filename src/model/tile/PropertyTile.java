package model.tile;

import model.ActualPlayer.PlayerID;

public class PropertyTile extends Tile {

    private final static int DEFAULT_BASE_VALUE = 300;

    private final int baseValue;
    private int currentValue;
    private PlayerID owner;

    public PropertyTile(int x, int y) {
        super(x, y);
        baseValue = DEFAULT_BASE_VALUE;
        currentValue = baseValue;
        owner = PlayerID.NOPLAYER;
    }

    public PropertyTile(int x, int y, int theBaseValue) {
        super(x, y);
        baseValue = theBaseValue;
        currentValue = baseValue;
        owner = PlayerID.NOPLAYER;
    }

    @Override
    public TileType getTileType() {
        return TileType.PROPERTY;
    }

    public int getValue() {
        return currentValue;
    }

    public int getCost() {
        return currentValue;
    }

    public int getToll() {
        return (int) (currentValue / 10f);
    }

    public PlayerID getOwner() {
        return owner;
    }
}

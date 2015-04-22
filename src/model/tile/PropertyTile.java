package model.tile;

public class PropertyTile extends Tile {

    private final static float DEFAULT_BASE_VALUE = 300;

    private final float baseValue;

    public PropertyTile(int x, int y) {
        super(x, y);
        baseValue = DEFAULT_BASE_VALUE;
    }

    public PropertyTile(int x, int y, float theBaseValue) {
        super(x, y);
        baseValue = theBaseValue;
    }

    @Override
    public TileType getTileType() {
        return TileType.PROPERTY;
    }
}

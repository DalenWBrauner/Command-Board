package model.tile;

public class PropertyTile extends Tile {

    private final float DEFAULT_BASE_VALUE = 200;

    private final float baseValue;

    PropertyTile(int x, int y) {
        super(x, y);
        baseValue = DEFAULT_BASE_VALUE;
    }

    PropertyTile(int x, int y, float theBaseValue) {
        super(x, y);
        baseValue = theBaseValue;
    }
}

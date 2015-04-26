package model.tile;

import shared.enums.TileType;

public class NullTile extends Tile {

    public NullTile(int x, int y) {
        super(x, y);
    }

    @Override
    public TileType getTileType() {
        return TileType.NONE;
    }

}

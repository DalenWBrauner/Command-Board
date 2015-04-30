package model.tile;

import shared.enums.TileType;

public class NullTile extends Tile {

    public NullTile(int x, int y) {
        super(x, y);
    }

    public NullTile() {
       //TODO double-check this is impossible before turning in assignment
        super(-1, -1);
    }

    @Override
    public TileType getTileType() {
        return TileType.NONE;
    }

}

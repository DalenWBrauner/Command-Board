package model.tile;

import shared.enums.TileType;

public class StartTile extends Tile {

    public StartTile(int x, int y) {
        super(x, y);
    }

    @Override
    public TileType getTileType() {
        return TileType.START;
    }

}

package model.tile;

import shared.enums.TileType;

public class StartTile extends Tile {
    private static final long serialVersionUID = -4081897235836158925L;

    public StartTile(int x, int y) {
        super(x, y);
    }

    @Override
    public TileType getTileType() {
        return TileType.START;
    }

}

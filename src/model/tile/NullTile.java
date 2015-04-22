package model.tile;


public class NullTile extends Tile {

    public NullTile(int x, int y) {
        super(x, y);
    }

    @Override
    public TileType getTileType() {
        return TileType.NONE;
    }

}

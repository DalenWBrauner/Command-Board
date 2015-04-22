package model.tile;


public class CheckpointTile extends Tile {

    public CheckpointTile(int x, int y) {
        super(x, y);
    }

    @Override
    public TileType getTileType() {
        return TileType.CHECKPOINT;
    }

}

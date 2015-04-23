package model.tile;


public class CheckpointTile extends Tile {

    public enum CheckpointColor { RED, BLU, GRN, YLW };

    private CheckpointColor myColor;

    public CheckpointTile(int x, int y, CheckpointColor color) {
        super(x, y);
        myColor = color;
    }

    @Override
    public TileType getTileType() {
        return TileType.CHECKPOINT;
    }

    public CheckpointColor getColor() {
        return myColor;
    }

}

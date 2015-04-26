package model.tile;

import shared.enums.CheckpointColor;
import shared.enums.TileType;

public class CheckpointTile extends Tile {

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

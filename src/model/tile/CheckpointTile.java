package model.tile;

import model.command.PrintCommand;
import shared.enums.CheckpointColor;
import shared.enums.TileType;

public class CheckpointTile extends Tile {

    private CheckpointColor myColor;

    public CheckpointTile(int x, int y, CheckpointColor color) {
        super(x, y);
        myColor = color;
        setOnPassCommand(new PrintCommand(
                "You passed the "+color+" checkpoint!"));
        setOnLandCommand(new PrintCommand(
                "You landed on the "+color+" checkpoint!"));
    }

    @Override
    public TileType getTileType() {
        return TileType.CHECKPOINT;
    }

    public CheckpointColor getColor() {
        return myColor;
    }

}

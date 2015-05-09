package model.tile;

import model.command.PrintCommand;
import shared.enums.TileType;

public class StartTile extends Tile {

    public StartTile(int x, int y) {
        super(x, y);
        setOnPassCommand(new PrintCommand("You passed the Start Tile!"));
        setOnLandCommand(new PrintCommand("You landed on the Start Tile!"));
    }

    @Override
    public TileType getTileType() {
        return TileType.START;
    }

}

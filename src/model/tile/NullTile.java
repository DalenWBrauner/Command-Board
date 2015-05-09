package model.tile;

import model.command.PrintCommand;
import shared.enums.TileType;

public class NullTile extends Tile {

    public NullTile(int x, int y) {
        super(x, y);
        setOnPassCommand(new PrintCommand(
                "You passed a Null Tile!\nHow did you do that?!"));
        setOnLandCommand(new PrintCommand(
                "You landed on a Null Tile!\nHow did you do that?!"));
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

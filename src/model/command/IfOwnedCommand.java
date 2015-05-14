package model.command;

import model.Player;
import model.tile.PropertyTile;
import shared.enums.PlayerID;

public class IfOwnedCommand extends Command {
    private PropertyTile whichTile;
    private Command ifOwned;
    private Command ifNotOwned;

    public IfOwnedCommand(PropertyTile tile,
            Command ifOwnedCommand, Command ifNotOwnedCommand) {
        whichTile = tile;
        ifOwned = ifOwnedCommand;
        ifNotOwned = ifNotOwnedCommand;
    }

    @Override
    public void execute(Player sourcePlayer) {
        if (whichTile.getOwner() != PlayerID.NOPLAYER) ifOwned.execute(sourcePlayer);
        else                                        ifNotOwned.execute(sourcePlayer);
    }
}

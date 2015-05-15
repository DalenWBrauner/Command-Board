package model.command;

import model.Player;
import model.tile.PropertyTile;

public class IfOwnedByYouCommand extends Command {
    private PropertyTile whichTile;
    private Command ifOwned;
    private Command ifNotOwned;


    public IfOwnedByYouCommand(PropertyTile tile,
        Command ifOwnedByYou, Command ifNotOwnedByYou) {
        whichTile = tile;
        ifOwned = ifOwnedByYou;
        ifNotOwned = ifNotOwnedByYou;
    }

    @Override
    public void execute(Player sourcePlayer) {
        if (whichTile.getOwner() == sourcePlayer.getID()) ifOwned.execute(sourcePlayer);
        else                                           ifNotOwned.execute(sourcePlayer);
    }

}

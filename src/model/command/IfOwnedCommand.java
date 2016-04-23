package model.command;

import java.rmi.RemoteException;

import model.player.Player;
import model.tile.PropertyTile;
import shared.enums.PlayerID;

public class IfOwnedCommand extends Command {
	private static final long serialVersionUID = 3681088870462332789L;
	
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
    public void execute(Player sourcePlayer) throws RemoteException {
        if (whichTile.getOwner().getID() != PlayerID.NOPLAYER) ifOwned.execute(sourcePlayer);
        else                                        ifNotOwned.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

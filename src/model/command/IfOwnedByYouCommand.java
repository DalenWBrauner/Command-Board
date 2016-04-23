package model.command;

import java.rmi.RemoteException;

import model.player.Player;
import model.tile.PropertyTile;

public class IfOwnedByYouCommand extends Command {
	private static final long serialVersionUID = -8849554145417627875L;
	
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
    public void execute(Player sourcePlayer) throws RemoteException {
        if (whichTile.getOwner().getID() == sourcePlayer.getID()) ifOwned.execute(sourcePlayer);
        else                                           ifNotOwned.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

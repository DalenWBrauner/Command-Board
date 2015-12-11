package model.command;

import java.rmi.RemoteException;

import model.Player;
import model.tile.PropertyTile;
import shared.interfaces.PlayerRepresentative;

public class SellAnyTileCommand extends Command {
	private static final long serialVersionUID = 6406447739265233321L;
	
	private SellTileCommand tileSalesman;

    public SellAnyTileCommand(SellTileCommand stc) {
        tileSalesman = stc;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        PropertyTile soldTile = rep.sellWhichTile(sourcePlayer.getID());

        // SELL, SELL, SELL!
        tileSalesman.setTile(soldTile);
        tileSalesman.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

package model.command;

import java.rmi.RemoteException;

import model.Player;
import model.tile.PropertyTile;

public class UpgradeTileCommand extends Command {
    private PropertyTile thisTile;
    private SubtractFundsCommand subtractFunds;

    public UpgradeTileCommand(SubtractFundsCommand sfc) {
        subtractFunds = sfc;
        thisTile = null;
    }

    public UpgradeTileCommand(SubtractFundsCommand sfc, PropertyTile tile) {
        subtractFunds = sfc;
        thisTile = tile;
    }

    public void setTile(PropertyTile tile) {
        thisTile = tile;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        // Ask the player to what level they want to upgrade
        int newLevel = sourcePlayer.getRepresentative().upgradeToWhatLevel(thisTile);

        // TODO: If desired, check against a level cap
        System.out.println(sourcePlayer.getID() + " is upgrading the tile at ("+
                thisTile.getX() + ", " + thisTile.getY() + ")");

        // Subtract Funds
        subtractFunds.setAmount(thisTile.getUpgradeCost(newLevel));
        subtractFunds.execute(sourcePlayer);

        // Upgrade the tile's level!
        thisTile.setLevel(newLevel);

        setChanged();
        notifyObservers();
    }
}

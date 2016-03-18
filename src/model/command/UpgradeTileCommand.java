package model.command;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.player.Player;
import model.tile.PropertyTile;

public class UpgradeTileCommand extends Command {
	private static final long serialVersionUID = -547836592224128812L;
	
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
    	
    	// We need to provide a list of upgrades the player can afford
    	int currentFunds = sourcePlayer.getWallet().getCashOnHand();
    	ArrayList<Integer> affordableUpgrades = new ArrayList<>();
    	
    	// Add each upgrade (up to 5) that we can afford
    	int i = thisTile.getLevel();
    	while (i <= 5
    		&& currentFunds > thisTile.getUpgradeCost(i)) {
    		affordableUpgrades.add(i);
    		i++;
    	}
    	
    	// Convert to array
    	int[] affordableUpgradesArray = new int[affordableUpgrades.size()];
    	for (int j = 0; j < affordableUpgradesArray.length; j++) {
    		affordableUpgradesArray[j] = affordableUpgrades.get(j);
    	}
    	
        // Ask the player to what level they want to upgrade
        int newLevel = sourcePlayer.getRepresentative().upgradeToWhatLevel(
        		affordableUpgradesArray, thisTile);

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

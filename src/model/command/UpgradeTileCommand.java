package model.command;

import java.rmi.RemoteException;

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
    	
    	// Figure out which upgrades the player can afford
    	int currentFunds = sourcePlayer.getWallet().getCashOnHand();
    	
    	System.out.println("THIS PLAYER HAS: " + String.valueOf(currentFunds));
    	
    	// Count the number of upgrades the player can afford
    	int affordableUpgrades = 0;
    	for (int i = thisTile.getLevel(); i < 6; i++) {
    		int upgradeCost = thisTile.getUpgradeCost(i);
    		System.out.println(String.valueOf(i) + " : " + String.valueOf(upgradeCost));
    		if (currentFunds >= upgradeCost) affordableUpgrades++;
    	}
    	
    	// Stick em in a list of options for the player
    	int[] affordableLevels = new int[affordableUpgrades];
    	for (int i = 0; i < affordableUpgrades; i++) {
    		affordableLevels[i] = thisTile.getLevel() + i; 
    	}
    	
        // Ask the player to what level they want to upgrade
        int newLevel = sourcePlayer.getRepresentative().upgradeToWhatLevel(
        		affordableLevels, thisTile);

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

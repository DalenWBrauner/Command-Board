package model.command;

import java.rmi.RemoteException;
import java.util.ArrayList;

import model.Board;
import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class UpgradeAnyTileCommand extends Command {
	private static final long serialVersionUID = 2804563968670864024L;
	private UpgradeTileCommand tileUpgrader;
    private Board theBoard;

    public UpgradeAnyTileCommand(UpgradeTileCommand utc, Board b) {
        tileUpgrader = utc;
        theBoard = b;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // Get which tiles the player can upgrade
        int currentFunds = sourcePlayer.getWallet().getCashOnHand();
        ArrayList<PropertyTile> ownedTiles = sourcePlayer.getTilesOwned();
        for (PropertyTile tile :
            (ArrayList<PropertyTile>) ownedTiles.clone()) {

            // Remove any tiles they can't afford to upgrade once
            if (tile.getUpgradeCost(tile.getLevel() + 1) > currentFunds) {
                ownedTiles.remove(tile);
            }
        }

        // If there aren't any upgradeable tiles, quit early
        if (ownedTiles.size() == 0) return;

        // Convert to array
        PropertyTile[] upgradeableTiles = new PropertyTile[ownedTiles.size()];
        ownedTiles.toArray(upgradeableTiles);

        // Ask the player which tile they'd like to upgrade
        Tile whichTile = theBoard.getTile(rep.upgradeWhichTile(upgradeableTiles));

        // If they don't want to upgrade a tile, quit early
        if (whichTile.getTileType() != TileType.PROPERTY) return;

        // Otherwise, go ahead and upgrade!
        tileUpgrader.setTile((PropertyTile) whichTile);
        tileUpgrader.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

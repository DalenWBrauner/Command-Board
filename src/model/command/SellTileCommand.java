package model.command;

import model.Player;
import model.tile.PropertyTile;

public class SellTileCommand extends Command {
    private PropertyTile tileForSale;
    private AddFundsCommand addFunds;

    public SellTileCommand(AddFundsCommand afc) {
        addFunds = afc;
        tileForSale = null;
    }

    public SellTileCommand(AddFundsCommand afc, PropertyTile tile) {
        addFunds = afc;
        tileForSale = tile;
    }

    public void setTile(PropertyTile tile) {
        tileForSale = tile;
    }

    @Override
    public void execute(Player sourcePlayer) {
        // For now, we've already asked the player if they want to sell

        // Add Funds
        addFunds.setAmount(tileForSale.getCost());
        addFunds.execute(sourcePlayer);

        // Reset the tile
        tileForSale.reset();

        setChanged();
        notifyObservers();
    }
}

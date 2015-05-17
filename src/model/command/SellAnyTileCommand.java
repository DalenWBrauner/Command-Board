package model.command;

import model.Player;
import model.tile.PropertyTile;
import shared.interfaces.PlayerRepresentative;

public class SellAnyTileCommand extends Command {
    private SellTileCommand tileSalesman;

    public SellAnyTileCommand(SellTileCommand stc) {
        tileSalesman = stc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Player sourcePlayer) {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        PropertyTile soldTile = rep.sellWhichTile(sourcePlayer.getID());

        // SELL, SELL, SELL!
        tileSalesman.setTile(soldTile);
        tileSalesman.execute(sourcePlayer);

        // The player is forced to sell,
        // Therefore we can rely on the sellCommand updating for us
    }
}

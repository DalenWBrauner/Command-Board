package model.command;

import model.Board;
import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class SellAnyTileCommand extends Command {
    private SellTileCommand tileSalesman;
    private Board theBoard;

    public SellAnyTileCommand(SellTileCommand stc, Board b) {
        tileSalesman = stc;
        theBoard = b;
    }

    @Override
    public void execute(Player sourcePlayer) {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        int[] soldTilePos = rep.sellWhichTile(sourcePlayer.getID());

        // Lookup the corresponding Tile from the Board.
        Tile toBeSoldTile = theBoard.getTile(soldTilePos[0], soldTilePos[1]);

        // Assert this is a PropertyTile (unngh)
        assert(toBeSoldTile.getTileType() == TileType.PROPERTY);
        PropertyTile soldTile = (PropertyTile) toBeSoldTile;

        // SELL, SELL, SELL!
        tileSalesman.setTile(soldTile);
        tileSalesman.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

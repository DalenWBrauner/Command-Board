package model.command;

import java.rmi.RemoteException;

import model.Board;
import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class SellAnyTileCommand extends Command {
	private static final long serialVersionUID = 6406447739265233321L;
    private SellTileCommand tileSalesman;
    private Board theBoard;

    public SellAnyTileCommand(SellTileCommand stc, Board b) {
        tileSalesman = stc;
        theBoard = b;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        Tile toBeSoldTile = theBoard.getTile(rep.sellWhichTile(sourcePlayer.getID()));

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

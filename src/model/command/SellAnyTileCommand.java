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
//      PropertyTile soldTile = rep.sellWhichTile(sourcePlayer.getID());
        int[] soldTilePos = rep.sellWhichTile(sourcePlayer.getID());

        Tile toBeSoldTile = theBoard.getTile(soldTilePos[0], soldTilePos[1]);

//        System.out.println("What did I get? - SellCardAnyTileCommand");
//        System.out.println(toBeSoldTile.getTileType());
//        System.out.println("Where is it?");
//        System.out.print(soldTilePos[0]);
//        System.out.print(", ");
//        System.out.print(soldTilePos[1]);
//        System.out.println();

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

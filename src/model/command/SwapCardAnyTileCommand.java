package model.command;

import model.Board;
import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class SwapCardAnyTileCommand extends Command {
    private SwapCardCommand cardSwapper;
    private Board theBoard;

    public SwapCardAnyTileCommand(SwapCardCommand scc, Board b) {
        cardSwapper = scc;
        theBoard = b;
    }

    @Override
    public void execute(Player sourcePlayer) {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        Tile swappingTile = theBoard.getTile(rep.swapCardOnWhichTile());

        // If they didn't want to swap with anything, quit early
        if (swappingTile.getTileType() == TileType.NONE) return;

        // SWAP! SWAP! SWAP! SWAP!
        cardSwapper.setTile((PropertyTile) swappingTile);
        cardSwapper.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

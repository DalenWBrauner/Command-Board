package model.command;


import java.rmi.RemoteException;

import model.Board;
import model.player.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class SwapCardAnyTileCommand extends Command {
	private static final long serialVersionUID = 7182083183077118545L;
	private SwapCardCommand cardSwapper;
    private Board theBoard;

    public SwapCardAnyTileCommand(SwapCardCommand scc, Board b) {
        cardSwapper = scc;
        theBoard = b;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // If the player doesn't have any tiles, quit early
        if (sourcePlayer.getTilesOwned().size() == 0) return;

        // Ask the player which tile they have to sell
        Tile swappingTile = theBoard.getTile(rep.swapCardOnWhichTile());

        // If they didn't want to swap with anything, quit early
        if (swappingTile.getTileType() != TileType.PROPERTY) return;

        // SWAP! SWAP! SWAP! SWAP!
        cardSwapper.setTile((PropertyTile) swappingTile);
        cardSwapper.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

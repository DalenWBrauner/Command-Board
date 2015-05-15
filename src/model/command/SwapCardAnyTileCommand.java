package model.command;

import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.TileType;
import shared.interfaces.PlayerRepresentative;

public class SwapCardAnyTileCommand extends Command {
    private SwapCardCommand cardSwapper;

    public SwapCardAnyTileCommand(SwapCardCommand scc) {
        cardSwapper = scc;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void execute(Player sourcePlayer) {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();

        // Ask the player which tile they have to sell
        Tile swappingTile = rep.swapCardOnWhichTile();

        // If they didn't want to swap with anything, quit now
        if (swappingTile.getTileType() == TileType.NONE) return;

        // SWAP! SWAP! SWAP! SWAP!
        cardSwapper.setTile((PropertyTile) swappingTile);
        cardSwapper.execute(sourcePlayer);

        setChanged();
        notifyObservers();
    }
}

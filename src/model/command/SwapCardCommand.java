package model.command;

import java.rmi.RemoteException;

import model.player.Player;
import model.tile.PropertyTile;
import shared.enums.CardShape;

public class SwapCardCommand extends Command {
	private static final long serialVersionUID = -9008064653495994529L;
	
	private PropertyTile thisTile;

    public SwapCardCommand() {
        thisTile = null;
    }

    public SwapCardCommand(PropertyTile tile) {
        thisTile = tile;
    }

    public void setTile(PropertyTile tile) {
        thisTile = tile;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        // Ask the player which card they'd like to swap
        CardShape card = sourcePlayer.getRepresentative().swapCardOnThisTile(
        		sourcePlayer.getHand().getCards(), thisTile);

        // If they wanted to swap,
        if (card != CardShape.NOCARD) {

            // Take the card out of their hand
            sourcePlayer.getHand().remove(card);

            // Add to their hand the card on the tile
            CardShape newCard = thisTile.getCard();
            sourcePlayer.getHand().add(newCard);

            // Place the card from their hand onto the tile
            thisTile.setCard(card);

            setChanged();
            notifyObservers();
        }
    }
}

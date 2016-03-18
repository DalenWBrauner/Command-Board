package model.command;

import java.rmi.RemoteException;
import java.util.HashMap;

import model.player.Player;
import model.tile.PropertyTile;
import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.interfaces.PlayerRepresentative;

public class BuyTileCommand extends Command {
	private static final long serialVersionUID = 2147776889265297840L;
	
	private HashMap<PlayerID, Player> players;
    private SubtractFundsCommand subtractFunds;
    private AddFundsCommand addFunds;
    private PropertyTile tileForPurchase;

    public BuyTileCommand(SubtractFundsCommand sfc, AddFundsCommand afc,
                          HashMap<PlayerID, Player> thePlayers) {
        players = thePlayers;
        addFunds = afc;
        subtractFunds = sfc;
        tileForPurchase = null;
    }

    public BuyTileCommand(SubtractFundsCommand sfc, AddFundsCommand afc,
                          HashMap<PlayerID, Player> thePlayers, PropertyTile tile) {
        players = thePlayers;
        addFunds = afc;
        subtractFunds = sfc;
        tileForPurchase = tile;
    }

    public void setTile(PropertyTile tile) {
        tileForPurchase = tile;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        PlayerRepresentative rep = sourcePlayer.getRepresentative();
        PlayerID tileOwner = tileForPurchase.getOwner();

        // If they can't afford it, tough luck
        if (tileForPurchase.getCost() > sourcePlayer.getWallet().getCashOnHand()) return;

        // If they need to place a card and their hand is empty, tough luck
        if (tileOwner == PlayerID.NOPLAYER && sourcePlayer.getHand().size() == 0) return;

        // Ask if they want to buy it
        boolean theyAgreed = rep.buyThisTile(tileForPurchase);

        // If yes, buy the tile!
        if (theyAgreed) {
            System.out.println(sourcePlayer.getID() + " bought the tile at (" +
                    tileForPurchase.getX() + ", " + tileForPurchase.getY() + ")");

            // Subtract Funds
            subtractFunds.setAmount(tileForPurchase.getCost());
            subtractFunds.execute(sourcePlayer);

            // Add funds to the previous owner
            if (tileOwner != PlayerID.NOPLAYER) {
                Player previousOwner = players.get(tileForPurchase.getOwner());
                addFunds.setAmount(tileForPurchase.getCost());
                addFunds.execute(previousOwner);

                // Remove the tile from their possession
                previousOwner.loseTile(tileForPurchase);

                System.out.println("They bought it from "+previousOwner.getID()+"!");

            // Otherwise place a card on it
            } else {

                // Get the card they'd like to place
                CardShape card = rep.placeWhichCard(sourcePlayer.getHand().getCards());
                tileForPurchase.setCard(card);

                // Remove that card from their hand
                sourcePlayer.getHand().remove(card);

                System.out.println("They placed a "+card+" card on it!");
            }

            // Set the tile's new owner!
            tileForPurchase.setOwner(sourcePlayer.getID());
            sourcePlayer.gainTile(tileForPurchase);

            setChanged();
            notifyObservers();
        }
    }
}

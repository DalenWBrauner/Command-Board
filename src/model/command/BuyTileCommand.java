package model.command;

import java.util.HashMap;

import model.Player;
import model.tile.PropertyTile;
import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.interfaces.PlayerRepresentative;

public class BuyTileCommand extends Command {
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
    public void execute(Player sourcePlayer) {
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

            // Otherwise place a card on it
            } else {
                // Get the card they'd like to place
                CardShape card = rep.placeWhichCard();
                tileForPurchase.setCard(card);
            }

            // Set the tile's new owner!
            tileForPurchase.setOwner(sourcePlayer.getID());
            sourcePlayer.gainTile(tileForPurchase);

            setChanged();
            notifyObservers();
        }
    }
}

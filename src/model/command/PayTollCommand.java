package model.command;

import java.util.HashMap;

import model.Player;
import model.tile.PropertyTile;
import shared.enums.PlayerID;

public class PayTollCommand extends Command {
    private HashMap<PlayerID, Player> players;
    private SubtractFundsCommand subtractFunds;
    private AddFundsCommand addFunds;
    private PropertyTile tile;

    public PayTollCommand(SubtractFundsCommand sfc, AddFundsCommand afc,
                          HashMap<PlayerID, Player> thePlayers, PropertyTile theTile) {
        players = thePlayers;
        addFunds = afc;
        subtractFunds = sfc;
        tile = theTile;
    }

    @Override
    public void execute(Player sourcePlayer) {
        int toll = tile.getToll();

        // Subtract their funds no matter what
        subtractFunds.setAmount(toll);
        subtractFunds.execute(sourcePlayer);

        // Give these to the player who owns the tile
        addFunds.setAmount(toll);
        addFunds.execute(players.get(tile.getOwner()));

        setChanged();
        notifyObservers();
    }
}
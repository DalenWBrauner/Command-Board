package model.command;

import java.util.ArrayList;
import java.util.HashMap;

import model.player.Player;
import shared.enums.PlayerID;

public class CashMagnetCommand extends Command {
	private static final long serialVersionUID = -1561212064205002924L;
	
	private HashMap<PlayerID, Player> players;
    private AddFundsCommand addFunds;

    public CashMagnetCommand(AddFundsCommand afc, int payout,
            HashMap<PlayerID, Player> thePlayers) {
        players = thePlayers;
        addFunds = afc;
        setPayout(payout);
    }

    public CashMagnetCommand(AddFundsCommand afc,
            HashMap<PlayerID, Player> thePlayers) {
        players = thePlayers;
        addFunds = afc;
        setPayout(0);
    }

    public void setPayout(int payout) {
        addFunds.setAmount(payout);
    }

    @Override
    public void execute(Player sourcePlayer) {

        // For every player
        ArrayList<Player> allPlayers = new ArrayList<>();
        for (PlayerID id : players.keySet()) {
            allPlayers.add(players.get(id));
        }

        // that isn't the current player
        allPlayers.remove(sourcePlayer);

        // Give the current player some amount of money
        // For each tile they own
        for (Player p : allPlayers) {
            for (int i = 0; i < p.getTilesOwned().size(); i++) {
                addFunds.execute(sourcePlayer);
            }
        }

        setChanged();
        notifyObservers();
    }
}

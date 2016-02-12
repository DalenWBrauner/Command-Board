package model.command;

import model.player.Player;

public class AddFundsCommand extends Command {
	private static final long serialVersionUID = -5453368569251933786L;
	
	private int funds;

    public AddFundsCommand() {
        funds = 0;
    }

    public AddFundsCommand(int amount) {
        funds = amount;
    }

    public void setAmount(int amount) {
        funds = amount;
    }

    @Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getWallet().addFunds(funds);

        setChanged();
        notifyObservers();
    }
}

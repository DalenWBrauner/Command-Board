package model.command;

import model.Player;

public class AddFundsCommand extends Command {
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

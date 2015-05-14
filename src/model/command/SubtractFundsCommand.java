package model.command;

import model.Player;

public class SubtractFundsCommand extends Command {
    private int funds;

    public SubtractFundsCommand() {
        funds = 0;
    }

    public SubtractFundsCommand(int amount) {
        funds = amount;
    }

    public void setAmount(int amount) {
        funds = amount;
    }

    @Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getWallet().subtractFunds(funds);

        setChanged();
        notifyObservers();
    }
}

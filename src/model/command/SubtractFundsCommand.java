package model.command;

import java.rmi.RemoteException;

import model.Player;

public class SubtractFundsCommand extends Command {
	private static final long serialVersionUID = -8375067438786634212L;
	
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
    public void execute(Player sourcePlayer) throws RemoteException {
        sourcePlayer.subtractFunds(funds);

        setChanged();
        notifyObservers();
    }
}

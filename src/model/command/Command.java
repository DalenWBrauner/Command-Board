package model.command;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Observable;

import model.player.Player;

public abstract class Command extends Observable implements Serializable {
	private static final long serialVersionUID = 2396042693184124470L;

	/** Executes a Command! Could do ANYTHING!
     * @param sourcePlayer The Player that triggered the Command.
     * @throws RemoteException
     */
    public abstract void execute(Player sourcePlayer) throws RemoteException;
}

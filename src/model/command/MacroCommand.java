package model.command;

import java.rmi.RemoteException;

import model.player.Player;

public class MacroCommand extends Command {
	private static final long serialVersionUID = -5242038025848708012L;
	
	private Command[] macro;

    public MacroCommand(Command[] commands) {
        macro = commands;
    }

    public void execute(Player sourcePlayer) throws RemoteException {
        for (Command cmd : macro) {
            cmd.execute(sourcePlayer);
        }

        // If you'd like to notify everyone after the macro is over
        // Rather than notify them at each step of the macro
        setChanged();
        notifyObservers();
    }
}

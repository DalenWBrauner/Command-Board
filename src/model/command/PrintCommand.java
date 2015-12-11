package model.command;

import model.Player;

public class PrintCommand extends Command {
	private static final long serialVersionUID = 8438997610010816070L;
	
	String output;

    public PrintCommand(String printThis) {
        output = printThis;
    }

    @Override
    public void execute(Player sourcePlayer) {
        setChanged();
        notifyObservers();
        System.out.println("PrintCommand invoked by " +
                sourcePlayer.getID() + ": " + output);

        setChanged();
        notifyObservers();
    }
}

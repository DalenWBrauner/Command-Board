package model.command;

import model.Player;

public class PrintCommand extends Command {
    String output;

    public PrintCommand(String printThis) {
        output = printThis;
    }

    public void execute(Player sourcePlayer) {
        setChanged();
        notifyObservers();
        System.out.println("PrintCommand invoked by " +
                sourcePlayer.getID() + ": " + output);
    }
}

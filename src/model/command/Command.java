package model.command;

import java.util.Observable;

import model.Player;

public abstract class Command extends Observable {

    /** Executes a Command! Could do ANYTHING!
     * @param sourcePlayer The Player that triggered the Command.
     */
    public abstract void execute(Player sourcePlayer);
}

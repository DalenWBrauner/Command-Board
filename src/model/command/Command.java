package model.command;

import model.Player;

public interface Command {

    /** Executes a Command! Could do ANYTHING!
     * @param sourcePlayer The Player that triggered the Command.
     */
    public void execute(Player sourcePlayer);
}

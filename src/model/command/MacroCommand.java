package model.command;

import model.Player;

public class MacroCommand extends Command {
    private Command[] macro;

    public MacroCommand(Command[] commands) {
        macro = commands;
    }

    public void execute(Player sourcePlayer) {
        for (Command cmd : macro) {
            cmd.execute(sourcePlayer);
        }

        // If you'd like to notify everyone after the macro is over
        // Rather than notify them at each step of the macro
        setChanged();
        notifyObservers();
    }
}

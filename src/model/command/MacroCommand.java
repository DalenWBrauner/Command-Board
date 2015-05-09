package model.command;

import model.Player;

public class MacroCommand implements Command {
    private Command[] macro;

    public MacroCommand(Command[] commands) {
        macro = commands;
    }

    public void execute(Player sourcePlayer) {
        for (Command cmd : macro) {
            cmd.execute(sourcePlayer);
        }
    }
}

package model.command;

import model.Player;
import shared.enums.CheckpointColor;

public class MarkCheckpointCommand extends Command {
    CheckpointColor myColor;

    public MarkCheckpointCommand(CheckpointColor color) {
        myColor = color;
    }

    public void execute(Player sourcePlayer) {
        sourcePlayer.setPassed(myColor, true);
        setChanged();
        notifyObservers();
        System.out.println("MarkCheckpointCommand invoked by " +
                sourcePlayer.getID() + " on " + myColor + " Checkpoint!");
    }
}

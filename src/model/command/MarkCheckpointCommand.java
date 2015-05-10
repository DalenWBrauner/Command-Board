package model.command;

import model.Player;
import shared.enums.CheckpointColor;

public class MarkCheckpointCommand extends Command {
    CheckpointColor myColor;
    /** Executes only if the Player has yet to reach this checkpoint this lap. */
    Command executeThis;

    public MarkCheckpointCommand(CheckpointColor color, Command ifNotYetPassed) {
        myColor = color;
        executeThis = ifNotYetPassed;
    }

    public void execute(Player sourcePlayer) {
        // If the player has yet to pass this checkpoint
        if (!sourcePlayer.hasPassed(myColor)) {

            // Set it as passed
            sourcePlayer.setPassed(myColor, true);
            setChanged();
            notifyObservers();

            // And execute what follows
            executeThis.execute(sourcePlayer);
        }
        // Otherwise, don't do anything; don't even bother to update
    }
}
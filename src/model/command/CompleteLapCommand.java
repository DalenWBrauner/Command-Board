package model.command;

import model.Player;
import shared.enums.CheckpointColor;

public class CompleteLapCommand extends Command {
    /** executeThis is only executed if the player truly completes the lap. */
    Command executeThis;

    public CompleteLapCommand(Command onLapCompletion) {
        executeThis = onLapCompletion;
    }

    @Override
    public void execute(Player sourcePlayer) {
        // If the Player has completed the lap
        if (sourcePlayer.hasPassed(CheckpointColor.RED) &&
            sourcePlayer.hasPassed(CheckpointColor.BLU) &&
            sourcePlayer.hasPassed(CheckpointColor.GRN) &&
            sourcePlayer.hasPassed(CheckpointColor.YLW)) {

            // Remove the checkpoints (and notify the observers)
            sourcePlayer.setPassed(CheckpointColor.RED, false);
            sourcePlayer.setPassed(CheckpointColor.BLU, false);
            sourcePlayer.setPassed(CheckpointColor.GRN, false);
            sourcePlayer.setPassed(CheckpointColor.YLW, false);
            setChanged();
            notifyObservers();

            // Now execute whatever happens as a result
            executeThis.execute(sourcePlayer);
        }
        // Otherwise, don't do anything; don't even bother to update
    }
}

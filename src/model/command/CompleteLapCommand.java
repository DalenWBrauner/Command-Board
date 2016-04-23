package model.command;

import java.rmi.RemoteException;

import model.Player;
import shared.enums.CheckpointColor;

public class CompleteLapCommand extends Command {
	private static final long serialVersionUID = -2694000314720776695L;
	/** executeThis is only executed if the player truly completes the lap. */
    Command executeThis;

    public CompleteLapCommand(Command onLapCompletion) {
        executeThis = onLapCompletion;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
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

        // Otherwise, don't do anything
        setChanged();
        notifyObservers();
    }
}

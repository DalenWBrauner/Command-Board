package model.command;

import model.Player;

public class EnableWalkBackwardsCommand extends Command {

    @Override
    public void execute(Player sourcePlayer) {
        // Sets their previous position to their current one
        // i.e. they can walk anywhere except where they already are
        // (walking where you are isn't an option)
        sourcePlayer.setLastPosition(sourcePlayer.getX(), sourcePlayer.getY());

        setChanged();
        notifyObservers();
    }
}

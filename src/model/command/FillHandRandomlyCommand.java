package model.command;

import model.Player;

public class FillHandRandomlyCommand extends Command {

    @Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getHand().fillRandomly();
        setChanged();
        notifyObservers();
    }
}

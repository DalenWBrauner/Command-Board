package model.command;

import model.Player;

public class GiveRandomCardCommand extends Command {

    @Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getHand().addRandomCard();
        setChanged();
        notifyObservers();
    }
}

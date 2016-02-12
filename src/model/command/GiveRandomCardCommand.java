package model.command;

import model.player.Player;

public class GiveRandomCardCommand extends Command {
	private static final long serialVersionUID = -195837818771793569L;

	@Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getHand().addRandomCard();
        setChanged();
        notifyObservers();
    }
}

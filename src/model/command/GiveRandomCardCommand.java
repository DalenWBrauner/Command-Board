package model.command;

import model.Player;

public class GiveRandomCardCommand extends Command {
	private static final long serialVersionUID = -195837818771793569L;

	@Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.addRandomCard();
        setChanged();
        notifyObservers();
    }
}

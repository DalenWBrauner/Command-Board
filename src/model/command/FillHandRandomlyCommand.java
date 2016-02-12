package model.command;

import model.player.Player;

public class FillHandRandomlyCommand extends Command {
	private static final long serialVersionUID = 8268132047707911012L;

	@Override
    public void execute(Player sourcePlayer) {
        sourcePlayer.getHand().fillRandomly();
        setChanged();
        notifyObservers();
    }
}

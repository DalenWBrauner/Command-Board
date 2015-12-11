package model.command;

import model.Player;

/** Does nothing when executed!
 *
 * @author Dalen W. Brauner
 */
public class NullCommand extends Command {
	private static final long serialVersionUID = 11L;

	@Override
    public void execute(Player sourcePlayer) {

        setChanged();
        notifyObservers();
    }
}

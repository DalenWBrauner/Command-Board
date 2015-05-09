package model.command;

import model.Player;

/** Does nothing when executed!
 *
 * @author Dalen W. Brauner
 */
public class NullCommand implements Command {

    @Override
    public void execute(Player sourcePlayer) {}

}

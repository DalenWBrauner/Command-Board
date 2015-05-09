package model.command;

import model.Player;

/** Does nothing when executed!
 *
 * @author Dalen W. Brauner
 */
public class NullCommand extends Command {

    @Override
    public void execute(Player sourcePlayer) {}

}

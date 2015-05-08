package model.command;

/** Does nothing when executed!
 *
 * @author Dalen W. Brauner
 */
public class NullCommand implements Command {

    @Override
    public void execute() {}

}

package model.command;

import java.rmi.RemoteException;

import model.Player;
import model.tile.PropertyTile;

public class PayTollCommand extends Command {
	private static final long serialVersionUID = -6624564889015659203L;
	
    private SubtractFundsCommand subtractFunds;
    private AddFundsCommand addFunds;
    private PropertyTile tile;

    public PayTollCommand(SubtractFundsCommand sfc, AddFundsCommand afc,
                          PropertyTile theTile) {
        addFunds = afc;
        subtractFunds = sfc;
        tile = theTile;
    }

    @Override
    public void execute(Player sourcePlayer) throws RemoteException {
        int toll = tile.getToll();

        System.out.println(sourcePlayer.getID() + " has to pay "
                         + tile.getOwner().getID() + " a toll of " + toll + "!");

        // Subtract their funds no matter what
        subtractFunds.setAmount(toll);
        subtractFunds.execute(sourcePlayer);

        // Give these to the player who owns the tile
        addFunds.setAmount(toll);
        addFunds.execute(tile.getOwner());

        setChanged();
        notifyObservers();
    }
}

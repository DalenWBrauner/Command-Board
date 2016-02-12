package model.tile;

import java.io.Serializable;
import java.rmi.RemoteException;

import model.command.Command;
import model.player.Player;
import shared.enums.TileType;

public abstract class Tile implements Serializable {
    private static final long serialVersionUID = -7544649620753892580L;

    private int xPos;
    private int yPos;
    protected Command onLand;
    protected Command onPass;

    public Tile(int x, int y) {
        setX(x);
        setY(y);
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int[] getPos() {
        return new int[] {xPos, yPos};
    }

    public void setX(int x) {
        xPos = x;
    }

    public void setY(int y) {
        yPos = y;
    }

    abstract public TileType getTileType();

    public void setOnLandCommand(Command command) {
        onLand = command;
    }

    public void setOnPassCommand(Command command) {
        onPass = command;
    }

    public void onPass(Player movingPlayer) throws RemoteException {
        onPass.execute(movingPlayer);
    }

    public void onLand(Player movingPlayer) throws RemoteException {
        onLand.execute(movingPlayer);
    }
}

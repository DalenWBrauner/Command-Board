package model.tile;

import model.Player;
import model.command.Command;
import shared.enums.TileType;

public abstract class Tile {

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

    public boolean onPass(Player movingPlayer) {
        onPass.execute(movingPlayer);
        return false;
    }

    public boolean onLand(Player movingPlayer) {
        onLand.execute(movingPlayer);
        return false;
    }
}

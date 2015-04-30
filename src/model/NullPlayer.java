package model;

import java.util.ArrayList;

import model.tile.PropertyTile;
import shared.enums.PlayerID;

public class NullPlayer implements Player {

    @Override
    public int getX() { return 0; }

    @Override
    public int getY() { return 0; }

    @Override
    public void setX(int x) {}

    @Override
    public void setY(int y) {}

    @Override
    public void setPosition(int x, int y) {}

    @Override
    public Hand getHand() { return null; }

    @Override
    public Wallet getWallet() { return null; }

    @Override
    public ArrayList<PropertyTile> getTilesOwned() { return null; }

    @Override
    public PlayerID getID() { return PlayerID.NOPLAYER; }

    @Override
    public void giveTile(PropertyTile newTile) { newTile.reset(); }

    @Override
    public void removeTile(PropertyTile oldTile) {}

}

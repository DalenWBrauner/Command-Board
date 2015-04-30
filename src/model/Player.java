package model;

import java.util.ArrayList;

import model.tile.PropertyTile;
import shared.enums.PlayerID;

public interface Player {
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public void setPosition(int x, int y);
    public Hand getHand();
    public Wallet getWallet();
    public ArrayList<PropertyTile> getTilesOwned();
    public PlayerID getID();
    public void giveTile(PropertyTile newTile);
    void removeTile(PropertyTile oldTile);
}

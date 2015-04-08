package model;

import java.util.ArrayList;

import model.tile.PropertyTile;

public interface Player {

    public Hand getHand();
    public Wallet getWallet();
    public ArrayList<PropertyTile> getTilesOwned();
}

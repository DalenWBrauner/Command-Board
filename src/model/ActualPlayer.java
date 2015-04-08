package model;

import java.util.ArrayList;

import model.tile.PropertyTile;

public class ActualPlayer implements Player {

    private Hand myHand;
    private Wallet myWallet;
    private ArrayList<PropertyTile> myTiles;

    public ActualPlayer() {
        System.out.println("new ActualPlayer();");
        myHand = new Hand();
        myWallet = new Wallet();
        myTiles = new ArrayList<PropertyTile>();
    }

    @Override
    public Hand getHand() {
        return myHand;
    }

    @Override
    public Wallet getWallet() {
        return myWallet;
    }

    @Override
    public ArrayList<PropertyTile> getTilesOwned() {
        // Do I maybe want to return a copy instead?
        return myTiles;
    }

}

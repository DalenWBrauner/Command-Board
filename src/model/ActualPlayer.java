package model;

import java.util.ArrayList;

import model.tile.PropertyTile;

public class ActualPlayer implements Player {

    public static enum PlayerID {PLAYER1, PLAYER2, PLAYER3, PLAYER4, NOPLAYER}

    private Hand myHand;
    private Wallet myWallet;
    private ArrayList<PropertyTile> myTiles;
    private PlayerID myID;

    public ActualPlayer(PlayerID id) {
        System.out.println("new ActualPlayer();");
        myHand = new Hand();
        myWallet = new Wallet();
        myTiles = new ArrayList<PropertyTile>();
        myID = id;
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
    @SuppressWarnings("unchecked")
    public ArrayList<PropertyTile> getTilesOwned() {
        return (ArrayList<PropertyTile>) myTiles.clone();
    }

    @Override
    public PlayerID getPlayerID() {
        return myID;
    }

    @Override
    public void giveTile(PropertyTile newTile) {
        myTiles.add(newTile);
        newTile.setOwner(myID);
    }
}

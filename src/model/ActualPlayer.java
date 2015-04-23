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
    public ArrayList<PropertyTile> getTilesOwned() {
        // Do I maybe want to return a copy instead?
        return myTiles;
    }

    @Override
    public PlayerID getPlayerID() {
        return myID;
    }
}

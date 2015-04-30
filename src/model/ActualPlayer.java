package model;

import java.util.ArrayList;

import model.tile.PropertyTile;
import shared.enums.PlayerID;

public class ActualPlayer implements Player {

    private int xPos;
    private int yPos;
    private Hand myHand;
    private Wallet myWallet;
    private ArrayList<PropertyTile> myTiles;
    private PlayerID myID;

    public ActualPlayer(PlayerID id) {
        System.out.println("new ActualPlayer("+id.toString()+");");
        myHand = new Hand();
        myWallet = new Wallet();
        myTiles = new ArrayList<PropertyTile>();
        myID = id;
        xPos = 0;
        yPos = 0;
    }

    @Override
    public int getX() {
        return xPos;
    }

    @Override
    public int getY() {
        return yPos;
    }

    @Override
    public void setX(int x) {
        xPos = x;
    }

    @Override
    public void setY(int y) {
        yPos = y;
    }

    @Override
    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
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
    public PlayerID getID() {
        return myID;
    }

    @Override
    public void giveTile(PropertyTile newTile) {
        myTiles.add(newTile);
        newTile.setOwner(myID);
    }

    @Override
    public void removeTile(PropertyTile oldTile) {
        myTiles.remove(oldTile);
    }
}

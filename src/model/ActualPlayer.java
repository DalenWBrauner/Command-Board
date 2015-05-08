package model;

import java.util.ArrayList;

import model.tile.PropertyTile;
import shared.enums.PlayerID;
import shared.interfaces.NullRepresentative;
import shared.interfaces.PlayerRepresentative;

public class ActualPlayer implements Player {

    private int xPos;
    private int yPos;
    private int xLast;
    private int yLast;
    private Hand myHand;
    private Wallet myWallet;
    private ArrayList<PropertyTile> myTiles;
    private PlayerID myID;
    private PlayerRepresentative myRep;

    public ActualPlayer(PlayerID id) {
        System.out.println("new ActualPlayer("+id.toString()+");");
        myHand = new Hand();
        myWallet = new Wallet();
        myTiles = new ArrayList<PropertyTile>();
        myID = id;
        xPos = 0;
        yPos = 0;
        xLast = 0;
        yLast = 0;
        myRep = new NullRepresentative(this);
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
    public void gainTile(PropertyTile newTile) {
        myTiles.add(newTile);
        newTile.setOwner(myID);
    }

    @Override
    public void loseTile(PropertyTile oldTile) {
        myTiles.remove(oldTile);
    }

    @Override
    public int getLastX() {
        return xLast;
    }

    @Override
    public int getLastY() {
        return yLast;
    }

    @Override
    public void setLastPosition(int x, int y) {
        xLast = x;
        yLast = y;
    }

    @Override
    public void setRepresentative(PlayerRepresentative newRep) {
        myRep = newRep;
    }

    @Override
    public PlayerRepresentative getRepresentative() {
        return myRep;
    }
}

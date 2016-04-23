package model.player;

import java.util.ArrayList;
import java.util.HashMap;

import model.Hand;
import model.Wallet;
import model.tile.PropertyTile;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.interfaces.NullRepresentative;
import shared.interfaces.PlayerRepresentative;

public class ActualPlayer implements Player {
	private static final long serialVersionUID = -7866756744763225409L;
	
	private PlayerID myID;
    private int xPos;
    private int yPos;
    private int xLast;
    private int yLast;
    private Hand myHand;
    private Wallet myWallet;
    private ArrayList<PropertyTile> myTiles;
    private HashMap<CheckpointColor, Boolean> checkpointsPassed;
    private PlayerRepresentative myRep;

    public ActualPlayer(PlayerID id) {
        System.out.println("new ActualPlayer("+id.toString()+");");
        myID = id;
        xPos = 0;
        yPos = 0;
        xLast = 0;
        yLast = 0;
        myHand = new Hand();
        myWallet = new Wallet(this);
        myTiles = new ArrayList<PropertyTile>();
        checkpointsPassed = new HashMap<>();
        checkpointsPassed.put(CheckpointColor.RED, false);
        checkpointsPassed.put(CheckpointColor.BLU, false);
        checkpointsPassed.put(CheckpointColor.GRN, false);
        checkpointsPassed.put(CheckpointColor.YLW, false);
        myRep = new NullRepresentative(this);
    }

    @Override
    public void setSeed(long seed) {
        myHand.setSeed(seed);
    }

    @Override
    public PlayerID getID() {
        return myID;
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
    public void gainTile(PropertyTile newTile) {
        myTiles.add(newTile);
        newTile.setOwner(this);
    }

    @Override
    public void loseTile(PropertyTile oldTile) {
        myTiles.remove(oldTile);
    }

    @Override
    public void setPassed(CheckpointColor color, boolean passedOrNot) {
        checkpointsPassed.replace(color, passedOrNot);
    }

    @Override
    public boolean hasPassed(CheckpointColor color) {
        return checkpointsPassed.get(color);
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

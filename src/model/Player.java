package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import model.command.Command;
import model.command.NullCommand;
import model.tile.PropertyTile;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.interfaces.NullRepresentative;
import shared.interfaces.PlayerRepresentative;

public class Player implements Serializable {
	private static final long serialVersionUID = -715917839463006593L;
	
	// Player Vars
	private PlayerID myID;
    private int xPos;
    private int yPos;
    private int xLast;
    private int yLast;
    private Hand myHand;
    private ArrayList<PropertyTile> myTiles;
    private HashMap<CheckpointColor, Boolean> checkpointsPassed;
    private PlayerRepresentative myRep;
    
    // Wallet Vars
    private int cashOnHand;
    private Command uponBankruptcy = new NullCommand();

    public Player(PlayerID id) {
        System.out.println("new Player("+id.toString()+");");
        myID = id;
        xPos = 0;
        yPos = 0;
        xLast = 0;
        yLast = 0;
        cashOnHand = 0;
        myHand = new Hand();
        myTiles = new ArrayList<PropertyTile>();
        checkpointsPassed = new HashMap<>();
        checkpointsPassed.put(CheckpointColor.RED, false);
        checkpointsPassed.put(CheckpointColor.BLU, false);
        checkpointsPassed.put(CheckpointColor.GRN, false);
        checkpointsPassed.put(CheckpointColor.YLW, false);
        myRep = new NullRepresentative(this);
    }

    public void setSeed(long seed) {
        myHand.setSeed(seed);
    }

    public PlayerID getID() {
        return myID;
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

    public void setPosition(int x, int y) {
        xPos = x;
        yPos = y;
    }

    public int getLastX() {
        return xLast;
    }

    public int getLastY() {
        return yLast;
    }

    public void setLastPosition(int x, int y) {
        xLast = x;
        yLast = y;
    }

    public Hand getHand() {
        return myHand;
    }

    public int getCashOnHand() {
        return cashOnHand;
    }

    public int getNetValue() {
        int netValue = cashOnHand;

        // Sum up the value of owned tiles
        for (PropertyTile asset : getTilesOwned())
            netValue += asset.getValue();

        return netValue;
    }

    public void addFunds(int funds) {
    	// If you were passed a negative number,
    	// treat it as zero; don't subtract funds.
        Math.max(funds, 0);
        cashOnHand += funds;
    }

    public void subtractFunds(int funds) throws RemoteException {
    	// If you were passed a negative number,
    	// treat it as zero; don't add funds.
        Math.max(funds, 0);
        cashOnHand -= funds;

        // If funds fall below zero
        // And the player has any tiles
        // TODO: Don't hardcode tile checking
        while (funds < 0 && getTilesOwned().size() > 0) uponBankruptcy.execute(this);
    }

    public void setBankruptcyCommand(Command command) {
        uponBankruptcy = command;
    }

    @SuppressWarnings("unchecked")
	public ArrayList<PropertyTile> getTilesOwned() {
        return (ArrayList<PropertyTile>) myTiles.clone();
    }

    public void gainTile(PropertyTile newTile) {
        myTiles.add(newTile);
        newTile.setOwner(this);
    }

    public void loseTile(PropertyTile oldTile) {
        myTiles.remove(oldTile);
    }

    public void setPassed(CheckpointColor color, boolean passedOrNot) {
        checkpointsPassed.replace(color, passedOrNot);
    }

    public boolean hasPassed(CheckpointColor color) {
        return checkpointsPassed.get(color);
    }

    public void setRepresentative(PlayerRepresentative newRep) {
        myRep = newRep;
    }

    public PlayerRepresentative getRepresentative() {
        return myRep;
    }
}

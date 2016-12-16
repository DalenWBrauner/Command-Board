package model;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import model.command.Command;
import model.command.NullCommand;
import model.tile.PropertyTile;
import shared.enums.CardShape;
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
    private ArrayList<PropertyTile> myTiles;
    private HashMap<CheckpointColor, Boolean> checkpointsPassed;
    private PlayerRepresentative myRep;
    
    // Hand Vars
    /** Maximum # of cards allowed in one hand. */
    private static final int MAX_CARDS = 5;
    private HashMap<CardShape, Integer> counters = new HashMap<>();
    private Random r = new Random();
    
    // Wallet Vars
    private int cashOnHand;
    private Command uponBankruptcy = new NullCommand();

    public Player(PlayerID id) {
    	// Player Constructor
        System.out.println("new Player("+id.toString()+");");
        myID = id;
        xPos = 0;
        yPos = 0;
        xLast = 0;
        yLast = 0;
        cashOnHand = 0;
        myTiles = new ArrayList<PropertyTile>();
        checkpointsPassed = new HashMap<>();
        checkpointsPassed.put(CheckpointColor.RED, false);
        checkpointsPassed.put(CheckpointColor.BLU, false);
        checkpointsPassed.put(CheckpointColor.GRN, false);
        checkpointsPassed.put(CheckpointColor.YLW, false);
        myRep = new NullRepresentative(this);
        
        // Hand Constructor
        counters.put(CardShape.SHAPE1, 0);
        counters.put(CardShape.SHAPE2, 0);
        counters.put(CardShape.SHAPE3, 0);
    }

    // Player Methods

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
    
    // Hand Methods

    /** Sets the seed for the random card generator.
     * With any luck, this will allow non-local games
     * to receive consistent random cards. */
    public void setSeed(long seed) {
        r.setSeed(seed);
    }

    /** Returns the maximum number of cards a hand can hold. */
    public static int maxHandSize() { return MAX_CARDS; }

    /** Returns the number of cards in the hand. */
    public int handSize() {
        return counters.get(CardShape.SHAPE1) +
               counters.get(CardShape.SHAPE2) +
               counters.get(CardShape.SHAPE3);
    }

    /** Adds the card of this cardshape to the hand. */
    public void addCard(CardShape card) {
        addCard(card, 1);
    }

    /** Adds a certain number of the card of this cardshape to the hand. */
    public void addCard(CardShape card, int amount) {
        // We won't add if it's the NOCARD
        if (card != CardShape.NOCARD) {

            // And we won't add more than we can hold
            int canHold = maxHandSize() - handSize();
            int newAmount = counters.get(card) + Math.min(amount, canHold);

            counters.replace(card, newAmount);
        }
    }

    /** Removes the card of this cardshape from the hand. */
    public void removeCard(CardShape card) {
        removeCard(card, 1);
    }

    /** Removes a certain number of the card of this cardshape from the hand. */
    public void removeCard(CardShape card, int amount) {
        // We won't subtract if it's the NOCARD
        if (card != CardShape.NOCARD) {

            // And we won't subtract more than we have
            int weHave = counters.get(card);
            int newAmount = counters.get(card) - Math.min(amount, weHave);

            counters.replace(card, newAmount);
        }
    }

    /** Returns the number of cards of a particular shape in this hand. */
    public int getNumberOfCards(CardShape thisShape) {
        // As long as they didn't give us the NOCARD,
        if (thisShape != CardShape.NOCARD) {
            // Return the number of instances of that card.
            return counters.get(thisShape);

        // Otherwise, return how much space we have left.
        } else {
            return MAX_CARDS - handSize();
        }
    }

    /** Returns an array of all the cards in your hand. */
    public CardShape[] getAllCards() {
        // This is literally the least object-oriented thing ever
        CardShape[] allCardsArray = new CardShape[MAX_CARDS];
        ArrayList<CardShape> allCardsList = new ArrayList<>();

        // Add the correct amount of each shape to our list (IN ORDER)
        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE1); i++) {
            allCardsList.add(CardShape.SHAPE1);
        }

        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE2); i++) {
            allCardsList.add(CardShape.SHAPE2);
        }

        for (int i = 0; i < getNumberOfCards(CardShape.SHAPE3); i++) {
            allCardsList.add(CardShape.SHAPE3);
        }

        while (allCardsList.size() < MAX_CARDS) {
            allCardsList.add(CardShape.NOCARD);
        }

        // Convert to array
        allCardsList.toArray(allCardsArray);
        return allCardsArray;
    }

    /** Empties the hand of all of its cards. */
    public void clearHand() {
        for (CardShape shape : counters.keySet()) {
            counters.replace(shape, 0);
        }
    }

    /** Adds a random card to the hand. */
    public void addRandomCard() {
        switch(r.nextInt(3) + 1) {
        case 1: addCard(CardShape.SHAPE1);
                break;
        case 2: addCard(CardShape.SHAPE2);
                break;
        case 3: addCard(CardShape.SHAPE3);
                break;
        }
    }

    /** Fills the hand with random card shapes. */
    public void fillHandRandomly() {
        // Add randomly until our hand is full
        while (handSize() < MAX_CARDS) {
            addRandomCard();
        }
    }
}

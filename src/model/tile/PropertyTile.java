package model.tile;

import model.Player;
import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.enums.TileType;

public class PropertyTile extends Tile {
    private static final long serialVersionUID = -6620741612615500871L;

    private static final int DEFAULT_BASE_VALUE = 300;
    private static final double UPGRADE_PERCENT = .70;
    private static final double UPC = .20; // Upgrade Percent Increment

    private final int baseValue;
    private int currentValue;   // Should be set to the baseValue
    private int myLevel         = 1;
    private Player owner        = new Player(PlayerID.NOPLAYER);
    private CardShape myCard    = CardShape.NOCARD;

    public PropertyTile(int x, int y) {
        super(x, y);
        baseValue = DEFAULT_BASE_VALUE;
        updateValue();
    }

    public PropertyTile(int x, int y, int theBaseValue) {
        super(x, y);
        baseValue = theBaseValue;
        updateValue();
    }

    @Override
    public TileType getTileType() {
        return TileType.PROPERTY;
    }

    private void updateValue() {
        currentValue = getValueAtLevel(myLevel);
    }

    private int getValueAtLevel(int level) {
        // Start at the base value.
        int value = baseValue;

        // The value increases by (UPGRADE_PERCENT + UPC) at each level
        for (int i = 1; i < myLevel; i++) {
            value += (baseValue * (UPGRADE_PERCENT + (i - 1) * UPC));
        }

        // Round to the nearest 10
        value = (int) (Math.round(value / 10.0) * 10.0);
        return value;
    }

    public int getValue() {
        return currentValue;
    }

    public int getCost() {
        return currentValue;
    }

    public int getToll() {
        // The toll is 20% of the value.
        return (int) (currentValue * .20f);
    }

    public int getUpgradeCost(int newLevel) {
        if (newLevel <= myLevel) { return 0; }
        else                     { return currentValue - getValueAtLevel(newLevel); }
    }

    public int getLevel() {
        return myLevel;
    }

    public void setLevel(int newLevel) {
        myLevel = newLevel;
        updateValue();
    }

    public Player getOwner() {
        return owner;
    }
    
    public void setOwner(Player newOwner) {
        owner = newOwner;
    }

    public CardShape getCard() {
        return myCard;
    }

    public void setCard(CardShape newCard) {
        myCard = newCard;
    }

    public void reset() {
        setCard(CardShape.NOCARD);
        setOwner(new Player(PlayerID.NOPLAYER));
        setLevel(1);
    }
}

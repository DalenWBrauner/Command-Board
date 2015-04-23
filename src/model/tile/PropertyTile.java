package model.tile;

import model.ActualPlayer.PlayerID;
import model.Hand.CardShape;

public class PropertyTile extends Tile {

    private final static int DEFAULT_BASE_VALUE = 300;

    private final int baseValue;
    private int currentValue;   // Should be set to the baseValue
    private int myLevel         = 1;
    private PlayerID owner      = PlayerID.NOPLAYER;
    private CardShape myCard    = CardShape.NOCARD;

    public PropertyTile(int x, int y) {
        super(x, y);
        baseValue = DEFAULT_BASE_VALUE;
        currentValue = baseValue;
    }

    public PropertyTile(int x, int y, int theBaseValue) {
        super(x, y);
        baseValue = theBaseValue;
        currentValue = baseValue;
    }

    @Override
    public TileType getTileType() {
        return TileType.PROPERTY;
    }

    public int getValue() {
        return currentValue;
    }

    public int getCost() {
        return currentValue;
    }

    public int getToll() {
        return (int) (currentValue / 10f);
    }

    public int getLevel() {
        return myLevel;
    }

    public PlayerID getOwner() {
        return owner;
    }

    public CardShape getCard() {
        return myCard;
    }
}

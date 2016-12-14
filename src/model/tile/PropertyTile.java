package model.tile;

import model.Player;
import model.command.AddFundsCommand;
import model.command.BuyTileCommand;
import model.command.Command;
import model.command.IfOwnedByYouCommand;
import model.command.IfOwnedCommand;
import model.command.MacroCommand;
import model.command.NullCommand;
import model.command.PayTollCommand;
import model.command.SubtractFundsCommand;
import model.command.SwapCardCommand;
import model.command.UpgradeTileCommand;
import shared.WatchTower;
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

    public PropertyTile(int x, int y, WatchTower currentTower) {
        this(x, y, DEFAULT_BASE_VALUE, currentTower);
    }

    public PropertyTile(int x, int y, int theBaseValue, WatchTower currentTower) {
        super(x, y);
        baseValue = theBaseValue;
        updateValue();

        // We'll need these soon
        AddFundsCommand afc = new AddFundsCommand();
        SubtractFundsCommand sfc = new SubtractFundsCommand();
        Command buyCommand = new BuyTileCommand(sfc, afc, this);

        // Create the onPass Command
        Command onPass = new NullCommand();
        onPass.addObserver(currentTower);

        /* The process that happens after landing on a property tile is complex.
         *
         * First, you check if the tile landed on is owned or not.
         * If it is not,
         *      If you can afford it,
         *          You have the option to buy it!
         * If it is,
         *      You check if the owner of the tile is YOU or not.
         *      if it is,
         *          You have the option to swap cards with it!
         *          You have the option to upgrade it!
         *      if it is not,
         *           You must pay the toll.
         *           If you can afford it,
         *                  You have the option to buy it!
         */

        // Create the macro for:
        // If you own the tile
        Command[] ifOwnedByYouMacro = new Command[2];
        ifOwnedByYouMacro[0] = new SwapCardCommand(this);
        ifOwnedByYouMacro[1] = new UpgradeTileCommand(sfc, this);
        Command ifOwnedByYou = new MacroCommand(ifOwnedByYouMacro);

        // Create the macro for:
        // If the tile is owned by someone else
        Command[] ifNotOwnedByYouMacro = new Command[2];
        ifNotOwnedByYouMacro[0] = new PayTollCommand(sfc, afc, this);
        ifNotOwnedByYouMacro[1] = buyCommand;
        Command ifNotOwnedByYou = new MacroCommand(ifNotOwnedByYouMacro);

        // You need to know how much money you lost before you buy a tile
        ifNotOwnedByYouMacro[0].addObserver(currentTower);

        // Create the onLand Command
        Command checkWhoOwns = new IfOwnedByYouCommand(this, ifOwnedByYou, ifNotOwnedByYou);
        Command onLand = new IfOwnedCommand(this, checkWhoOwns, buyCommand);
        // Finish
        setOnPassCommand(onPass);
        setOnLandCommand(onLand);
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

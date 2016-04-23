package model.tile;

import model.Board;
import model.command.AddFundsCommand;
import model.command.BuyTileCommand;
import model.command.Command;
import model.command.CompleteLapCommand;
import model.command.FillHandRandomlyCommand;
import model.command.GiveRandomCardCommand;
import model.command.IfOwnedByYouCommand;
import model.command.IfOwnedCommand;
import model.command.MacroCommand;
import model.command.MarkCheckpointCommand;
import model.command.NullCommand;
import model.command.PayTollCommand;
import model.command.PrintCommand;
import model.command.SubtractFundsCommand;
import model.command.SwapCardCommand;
import model.command.UpgradeAnyTileCommand;
import model.command.UpgradeTileCommand;
import shared.WatchTower;
import shared.enums.CheckpointColor;

public class TileFactory {

    private WatchTower currentTower;
    private Board theBoard;

    public void setWatchTower(WatchTower tower) {
        currentTower = tower;
    }

    public void setBoard(Board board) {
        theBoard = board;
    }

    /** New rule for tile creation and adding observers:
     * Only add an observer to commands that come immediately before player decisions.
     *
     *
     * As we should know, we are going to update the board after a player has moved,
     * and after a player has landed. Once for each.
     *
     * The ONLY time we should add observers ourselves is if, for ANY reason,
     * the view would NEED to know that we've changed BEFORE we go on to execute the next command.
     * For example, updating would be required before a Player is asked to buy a tile.
     * Updating would not be required if all that's happening is the Player is being showered with goodies.
     */

    public StartTile createStart(int x, int y) {
        StartTile tile = new StartTile(x, y);

        // I'll need this soon
        SubtractFundsCommand sfc = new SubtractFundsCommand();
        sfc.addObserver(currentTower);

        // Create a macro for:
        // When a player passes the start after passing all 4 checkpoints
        Command[] completeLapMacro = new Command[2];
        completeLapMacro[0] = new AddFundsCommand(3000);
        completeLapMacro[1] = new FillHandRandomlyCommand();
//        completeLapMacro[0].addObserver(currentTower);
//        completeLapMacro[1].addObserver(currentTower);
        Command onLapCompletion = new MacroCommand(completeLapMacro);
//        onLapCompletion.addObserver(currentTower);

        // Create the onPass Command
        Command onPass = new CompleteLapCommand(onLapCompletion);
//        onPass.addObserver(currentTower);

        // Create the onLand Command
        UpgradeTileCommand utc = new UpgradeTileCommand(sfc);
        Command onLand = new UpgradeAnyTileCommand(utc, theBoard);
//        utc.addObserver(currentTower);
//        onLand.addObserver(currentTower);

        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public CheckpointTile makeCheckpoint(int x, int y, CheckpointColor color) {
        CheckpointTile tile = new CheckpointTile(x, y, color);

        // I'll need this soon
        SubtractFundsCommand sfc = new SubtractFundsCommand();
        sfc.addObserver(currentTower);

        // Create a macro for:
        // First time a player passes a checkpoint in a given lap
        Command[] ifNotYetPassedMacro = new Command[3];
        ifNotYetPassedMacro[0] = new GiveRandomCardCommand();
        ifNotYetPassedMacro[1] = new AddFundsCommand(500);
        ifNotYetPassedMacro[2] = new PrintCommand("First time this lap!");
        Command ifNotYetPassed = new MacroCommand(ifNotYetPassedMacro);

        // Create the onPass Command
        Command[] onPassMacro = new Command[2];
        onPassMacro[0] = new PrintCommand("You passed the "+color+" Checkpoint!");
        onPassMacro[1] = new MarkCheckpointCommand(color, ifNotYetPassed);
        Command onPass = new MacroCommand(onPassMacro);

        // Create the onLand Command
        UpgradeTileCommand utc = new UpgradeTileCommand(sfc);
        Command onLand = new UpgradeAnyTileCommand(utc, theBoard);

        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public PropertyTile makeProperty(int x, int y, int propertyValue) {
        PropertyTile tile = new PropertyTile(x, y, propertyValue);

        // We'll need these soon
        AddFundsCommand afc = new AddFundsCommand();
        SubtractFundsCommand sfc = new SubtractFundsCommand();
        Command buyCommand = new BuyTileCommand(sfc, afc, tile);

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
        ifOwnedByYouMacro[0] = new SwapCardCommand(tile);
        ifOwnedByYouMacro[1] = new UpgradeTileCommand(sfc, tile);
        Command ifOwnedByYou = new MacroCommand(ifOwnedByYouMacro);

        // Create the macro for:
        // If the tile is owned by someone else
        Command[] ifNotOwnedByYouMacro = new Command[2];
        ifNotOwnedByYouMacro[0] = new PayTollCommand(sfc, afc, tile);
        ifNotOwnedByYouMacro[1] = buyCommand;
        Command ifNotOwnedByYou = new MacroCommand(ifNotOwnedByYouMacro);

        // You need to know how much money you lost before you buy a tile
        ifNotOwnedByYouMacro[0].addObserver(currentTower);

        // Create the onLand Command
        Command checkWhoOwns = new IfOwnedByYouCommand(tile, ifOwnedByYou, ifNotOwnedByYou);
        Command onLand = new IfOwnedCommand(tile, checkWhoOwns, buyCommand);
        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }
}

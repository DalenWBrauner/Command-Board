package model;

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
import model.tile.CheckpointTile;
import model.tile.PropertyTile;
import model.tile.StartTile;
import shared.WatchTower;
import shared.enums.CheckpointColor;

public class BoardFactory {

    private WatchTower currentTower;
    private Board theBoard;

    public void setWatchTower(WatchTower tower) {
        currentTower = tower;
    }

    public Board getBoard(String boardName) throws IllegalArgumentException {
        System.out.println("BoardFactory.getBoard("+boardName+"); START");

        // Instantiate the board object
        Board theBoard = new Board();
        theBoard.setName(boardName);

        // Fill it with tiles depending on the given layout
        switch(boardName) {
        case "Rings":      createRingsBoard(theBoard);
            break;
        case "Keyblade":   createKeybladeBoard(theBoard);
            break;
        case "Snailshell": createSnailshellBoard(theBoard);
            break;
        case "Butterfly":  createButterflyBoard(theBoard);
            break;
        case "Honeypot":   createHoneypotBoard(theBoard);
            break;
        default:
            throw new IllegalArgumentException("I don't know how to make a "+boardName+" Board!");
        }

        // Done editing the board
        theBoard.setFinished();
        System.out.println("BoardFactory.getBoard("+boardName+"); END");
        return theBoard;
    }


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
    
    private void createRingsBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(createStart(3, 2));

        // Add the Checkpoint Tiles
        theBoard.addTile(makeCheckpoint(5, 0, CheckpointColor.RED));
        theBoard.addTile(makeCheckpoint(0, 2, CheckpointColor.BLU));
        theBoard.addTile(makeCheckpoint(3, 5, CheckpointColor.GRN));
        theBoard.addTile(makeCheckpoint(5, 7, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(makeProperty(0, 3, 600));
        theBoard.addTile(makeProperty(0, 4, 800));
        theBoard.addTile(makeProperty(0, 5, 1000));
        theBoard.addTile(makeProperty(1, 2, 600));
        theBoard.addTile(makeProperty(1, 5, 800));
        theBoard.addTile(makeProperty(2, 2, 400));
        theBoard.addTile(makeProperty(2, 5, 600));
        theBoard.addTile(makeProperty(2, 6, 550));
        theBoard.addTile(makeProperty(2, 7, 500));
        theBoard.addTile(makeProperty(3, 0, 220));
        theBoard.addTile(makeProperty(3, 1, 200));
        theBoard.addTile(makeProperty(3, 3, 400));
        theBoard.addTile(makeProperty(3, 4, 600));
        theBoard.addTile(makeProperty(3, 7, 450));
        theBoard.addTile(makeProperty(4, 0, 250));
        theBoard.addTile(makeProperty(4, 2, 200));
        theBoard.addTile(makeProperty(4, 4, 550));
        theBoard.addTile(makeProperty(4, 7, 350));
        theBoard.addTile(makeProperty(5, 1, 250));
        theBoard.addTile(makeProperty(5, 2, 220));
        theBoard.addTile(makeProperty(5, 4, 500));
        theBoard.addTile(makeProperty(5, 5, 450));
        theBoard.addTile(makeProperty(5, 6, 350));
    }

    private void createKeybladeBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(createStart(3, 2));

        // Add the Checkpoint Tiles
        theBoard.addTile(makeCheckpoint(3, 0, CheckpointColor.RED));
        theBoard.addTile(makeCheckpoint(0, 2, CheckpointColor.BLU));
        theBoard.addTile(makeCheckpoint(3, 4, CheckpointColor.GRN));
        theBoard.addTile(makeCheckpoint(9, 2, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(makeProperty(0, 0, 480));
        theBoard.addTile(makeProperty(0, 1, 600));
        theBoard.addTile(makeProperty(0, 3, 600));
        theBoard.addTile(makeProperty(0, 4, 480));
        theBoard.addTile(makeProperty(1, 0, 360));
        theBoard.addTile(makeProperty(1, 4, 360));
        theBoard.addTile(makeProperty(2, 0, 240));
        theBoard.addTile(makeProperty(2, 4, 240));
        theBoard.addTile(makeProperty(3, 1, 120));
        theBoard.addTile(makeProperty(3, 3, 120));
        theBoard.addTile(makeProperty(4, 2, 240));
        theBoard.addTile(makeProperty(5, 2, 360));
        theBoard.addTile(makeProperty(6, 2, 480));
        theBoard.addTile(makeProperty(7, 2, 600));
        theBoard.addTile(makeProperty(7, 3, 900));
        theBoard.addTile(makeProperty(7, 4, 1200));
        theBoard.addTile(makeProperty(8, 2, 720));
        theBoard.addTile(makeProperty(8, 4, 800));
        theBoard.addTile(makeProperty(9, 3, 600));
        theBoard.addTile(makeProperty(9, 4, 700));
    }

    private void createSnailshellBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(createStart(4, 3));

        // Add the Checkpoint Tiles
        theBoard.addTile(makeCheckpoint(0, 3, CheckpointColor.RED));
        theBoard.addTile(makeCheckpoint(8, 7, CheckpointColor.BLU));
        theBoard.addTile(makeCheckpoint(8, 0, CheckpointColor.GRN));
        theBoard.addTile(makeCheckpoint(6, 5, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(makeProperty(3, 3, 400));
        theBoard.addTile(makeProperty(4, 2, 550));
        theBoard.addTile(makeProperty(5, 2, 700));
        theBoard.addTile(makeProperty(6, 2, 700));
        theBoard.addTile(makeProperty(6, 3, 700));
        theBoard.addTile(makeProperty(6, 4, 550));
        theBoard.addTile(makeProperty(5, 5, 550));
        theBoard.addTile(makeProperty(4, 5, 600));
        theBoard.addTile(makeProperty(3, 5, 600));
        theBoard.addTile(makeProperty(2, 5, 550));
        theBoard.addTile(makeProperty(2, 4, 400));
        theBoard.addTile(makeProperty(2, 3, 800));
        theBoard.addTile(makeProperty(2, 2, 400));
        theBoard.addTile(makeProperty(2, 1, 250));
        theBoard.addTile(makeProperty(2, 0, 100));
        theBoard.addTile(makeProperty(3, 0, 1000));
        theBoard.addTile(makeProperty(4, 0, 100));
        theBoard.addTile(makeProperty(5, 0, 150));
        theBoard.addTile(makeProperty(6, 0, 200));
        theBoard.addTile(makeProperty(7, 0, 250));
        theBoard.addTile(makeProperty(8, 1, 300));
        theBoard.addTile(makeProperty(8, 2, 600));
        theBoard.addTile(makeProperty(8, 3, 900));
        theBoard.addTile(makeProperty(8, 4, 900));
        theBoard.addTile(makeProperty(8, 5, 600));
        theBoard.addTile(makeProperty(8, 6, 300));
        theBoard.addTile(makeProperty(7, 7, 300));
        theBoard.addTile(makeProperty(6, 7, 240));
        theBoard.addTile(makeProperty(5, 7, 200));
        theBoard.addTile(makeProperty(4, 7, 160));
        theBoard.addTile(makeProperty(3, 7, 120));
        theBoard.addTile(makeProperty(2, 7, 12000));
        theBoard.addTile(makeProperty(1, 7, 120));
        theBoard.addTile(makeProperty(0, 7, 160));
        theBoard.addTile(makeProperty(0, 6, 200));
        theBoard.addTile(makeProperty(0, 5, 240));
        theBoard.addTile(makeProperty(0, 4, 300));
        theBoard.addTile(makeProperty(1, 3, 400));
    }

    private void createButterflyBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(createStart(7, 3));

        // Add the Checkpoint Tiles
        theBoard.addTile(makeCheckpoint(8, 0, CheckpointColor.RED));
        theBoard.addTile(makeCheckpoint(8, 6, CheckpointColor.BLU));
        theBoard.addTile(makeCheckpoint(1, 3, CheckpointColor.GRN));
        theBoard.addTile(makeCheckpoint(5, 2, CheckpointColor.YLW));

        // Add the Property Tiles
        // The ring in the center
        theBoard.addTile(makeProperty(4, 2, 100));
        theBoard.addTile(makeProperty(3, 2, 900));
        theBoard.addTile(makeProperty(3, 3, 100));
        theBoard.addTile(makeProperty(3, 4, 1200));
        theBoard.addTile(makeProperty(4, 4, 100));
        theBoard.addTile(makeProperty(5, 4, 900));
        theBoard.addTile(makeProperty(5, 3, 100));
        // West Wing
        theBoard.addTile(makeProperty(3, 1, 600));
        theBoard.addTile(makeProperty(3, 0, 400));
        theBoard.addTile(makeProperty(2, 0, 300));
        theBoard.addTile(makeProperty(1, 0, 200));
        theBoard.addTile(makeProperty(0, 0, 900));
        theBoard.addTile(makeProperty(0, 1, 750));
        theBoard.addTile(makeProperty(0, 2, 550));
        theBoard.addTile(makeProperty(1, 2, 300));
        theBoard.addTile(makeProperty(2, 3, 300));
        theBoard.addTile(makeProperty(1, 4, 300));
        theBoard.addTile(makeProperty(0, 4, 550));
        theBoard.addTile(makeProperty(0, 5, 750));
        theBoard.addTile(makeProperty(0, 6, 900));
        theBoard.addTile(makeProperty(1, 6, 200));
        theBoard.addTile(makeProperty(2, 6, 300));
        theBoard.addTile(makeProperty(3, 6, 400));
        theBoard.addTile(makeProperty(3, 5, 600));
        // East Wing
        theBoard.addTile(makeProperty(5, 1, 600));
        theBoard.addTile(makeProperty(5, 0, 777));
        theBoard.addTile(makeProperty(6, 0, 600));
        theBoard.addTile(makeProperty(7, 0, 480));
        theBoard.addTile(makeProperty(8, 1, 360));
        theBoard.addTile(makeProperty(8, 2, 240));
        theBoard.addTile(makeProperty(7, 2, 120));
        theBoard.addTile(makeProperty(6, 3, 300));
        theBoard.addTile(makeProperty(7, 4, 120));
        theBoard.addTile(makeProperty(8, 4, 240));
        theBoard.addTile(makeProperty(8, 5, 360));
        theBoard.addTile(makeProperty(7, 6, 480));
        theBoard.addTile(makeProperty(6, 6, 600));
        theBoard.addTile(makeProperty(5, 6, 777));
        theBoard.addTile(makeProperty(5, 5, 600));
    }

    private void createHoneypotBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(createStart(3, 8));

        // Add the Checkpoint Tiles
        theBoard.addTile(makeCheckpoint(6, 0, CheckpointColor.RED));
        theBoard.addTile(makeCheckpoint(1, 2, CheckpointColor.BLU));
        theBoard.addTile(makeCheckpoint(0, 7, CheckpointColor.GRN));
        theBoard.addTile(makeCheckpoint(6, 7, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(makeProperty(0, 0, 650));
        theBoard.addTile(makeProperty(1, 0, 600));
        theBoard.addTile(makeProperty(2, 0, 550));
        theBoard.addTile(makeProperty(3, 0, 400));
        theBoard.addTile(makeProperty(4, 0, 550));
        theBoard.addTile(makeProperty(5, 0, 600));
        theBoard.addTile(makeProperty(0, 1, 700));
        theBoard.addTile(makeProperty(6, 1, 600));
        theBoard.addTile(makeProperty(0, 2, 750));
        theBoard.addTile(makeProperty(2, 2, 750));
        theBoard.addTile(makeProperty(3, 2, 1000));
        theBoard.addTile(makeProperty(5, 2, 700));
        theBoard.addTile(makeProperty(6, 2, 650));
        theBoard.addTile(makeProperty(1, 3, 750));
        theBoard.addTile(makeProperty(3, 3, 1000));
        theBoard.addTile(makeProperty(4, 3, 850));
        theBoard.addTile(makeProperty(5, 3, 750));
        theBoard.addTile(makeProperty(0, 4, 650));
        theBoard.addTile(makeProperty(1, 4, 700));
        theBoard.addTile(makeProperty(5, 4, 700));
        theBoard.addTile(makeProperty(6, 4, 650));
        theBoard.addTile(makeProperty(0, 5, 600));
        theBoard.addTile(makeProperty(6, 5, 600));
        theBoard.addTile(makeProperty(0, 6, 500));
        theBoard.addTile(makeProperty(6, 6, 500));
        theBoard.addTile(makeProperty(1, 7, 480));
        theBoard.addTile(makeProperty(5, 7, 300));
        theBoard.addTile(makeProperty(1, 8, 360));
        theBoard.addTile(makeProperty(2, 8, 240));
        theBoard.addTile(makeProperty(4, 8, 300));
        theBoard.addTile(makeProperty(5, 8, 300));
    }

}

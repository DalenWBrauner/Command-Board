package model.tile;

import model.Board;
import model.command.AddFundsCommand;
import model.command.Command;
import model.command.GiveRandomCardCommand;
import model.command.MacroCommand;
import model.command.MarkCheckpointCommand;
import model.command.PrintCommand;
import model.command.SubtractFundsCommand;
import model.command.UpgradeAnyTileCommand;
import model.command.UpgradeTileCommand;
import shared.WatchTower;
import shared.enums.CheckpointColor;
import shared.enums.TileType;

public class CheckpointTile extends Tile {
    private static final long serialVersionUID = 858586158601682615L;

    private CheckpointColor myColor;

    public CheckpointTile(int x, int y, CheckpointColor color, WatchTower currentTower, Board theBoard) {
        super(x, y);
        myColor = color;
        
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
        setOnPassCommand(onPass);
        setOnLandCommand(onLand);
    }

    @Override
    public TileType getTileType() {
        return TileType.CHECKPOINT;
    }

    public CheckpointColor getColor() {
        return myColor;
    }

}

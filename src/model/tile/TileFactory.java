package model.tile;

import java.util.HashMap;

import model.Board;
import model.Player;
import model.command.AddFundsCommand;
import model.command.Command;
import model.command.CompleteLapCommand;
import model.command.FillHandRandomlyCommand;
import model.command.GiveRandomCardCommand;
import model.command.MacroCommand;
import model.command.MarkCheckpointCommand;
import model.command.NullCommand;
import model.command.PrintCommand;
import shared.WatchTower;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;

public class TileFactory {

    private WatchTower currentTower;
    private Board theBoard;
    private HashMap<PlayerID, Player> currentPlayers;

    public void setWatchTower(WatchTower tower) {
        currentTower = tower;
    }

    public void setPlayerMap(HashMap<PlayerID, Player> playerMap) {
        currentPlayers = playerMap;
    }

    public void setBoard(Board board) {
        theBoard = board;
    }

    public StartTile createStart(int x, int y) {
        StartTile tile = new StartTile(x, y);

        // Create a macro for:
        // When a player passes the start after passing all 4 checkpoints
        Command[] completeLapMacro = new Command[2];
        completeLapMacro[0] = new AddFundsCommand(3000);
        completeLapMacro[1] = new FillHandRandomlyCommand();
        completeLapMacro[0].addObserver(currentTower);
        completeLapMacro[1].addObserver(currentTower);
        Command onLapCompletion = new MacroCommand(completeLapMacro);

        // Create the onPass Command
        Command onPass = new CompleteLapCommand(onLapCompletion);
        onPass.addObserver(currentTower);

        // Create the onLand Command
        Command onLand = new PrintCommand("You landed on the Start!");
        onLand.addObserver(currentTower);

        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public CheckpointTile makeCheckpoint(int x, int y, CheckpointColor color) {
        CheckpointTile tile = new CheckpointTile(x, y, color);

        // Create a macro for:
        // First time a player passes a checkpoint in a given lap
        Command[] ifNotYetPassedMacro = new Command[3];
        ifNotYetPassedMacro[0] = new GiveRandomCardCommand();
        ifNotYetPassedMacro[1] = new AddFundsCommand(500);
        ifNotYetPassedMacro[2] = new PrintCommand("First time this lap!");
        ifNotYetPassedMacro[0].addObserver(currentTower);
        ifNotYetPassedMacro[1].addObserver(currentTower);
        Command ifNotYetPassed = new MacroCommand(ifNotYetPassedMacro);

        // Create the onPass Command
        Command[] onPassMacro = new Command[2];
        onPassMacro[0] = new PrintCommand("You passed the "+color+" Checkpoint!");
        onPassMacro[1] = new MarkCheckpointCommand(color, ifNotYetPassed);
        onPassMacro[0].addObserver(currentTower);
        onPassMacro[1].addObserver(currentTower);
        Command onPass = new MacroCommand(onPassMacro);

        // Create the onLand Command
        Command onLand = new PrintCommand("You landed on the "+color+" Checkpoint!");
        onLand.addObserver(currentTower);

        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public PropertyTile makeProperty(int x, int y, int propertyValue) {
        PropertyTile tile = new PropertyTile(x, y, propertyValue);

        // Create the onPass Command
        Command onPass = new NullCommand();//PrintCommand("You passed a Property!");
        onPass.addObserver(currentTower);

        // Create the onLand Command
        Command onLand = new PrintCommand("You landed on a Property Tile!");
        onLand.addObserver(currentTower);

        // Finish
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }
}

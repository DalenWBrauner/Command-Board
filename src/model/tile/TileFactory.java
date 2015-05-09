package model.tile;

import model.command.Command;
import model.command.PrintCommand;
import shared.WatchTower;
import shared.enums.CheckpointColor;

public class TileFactory {

    private WatchTower currentTower;

    public void setWatchTower(WatchTower tower) {
        currentTower = tower;
    }

    public StartTile createStart(int x, int y) {
        StartTile tile = new StartTile(x, y);
        Command onPass = new PrintCommand("You passed the Start!");
        Command onLand = new PrintCommand("You landed on the Start!");
        onPass.addObserver(currentTower);
        onLand.addObserver(currentTower);
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public CheckpointTile makeCheckpoint(int x, int y, CheckpointColor color) {
        CheckpointTile tile = new CheckpointTile(x, y, color);
        Command onPass = new PrintCommand("You passed the "+color+" Checkpoint!");
        Command onLand = new PrintCommand("You landed on the "+color+" Checkpoint!");
        onPass.addObserver(currentTower);
        onLand.addObserver(currentTower);
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

    public PropertyTile makeProperty(int x, int y, int propertyValue) {
        PropertyTile tile = new PropertyTile(x, y, propertyValue);
        Command onPass = new PrintCommand("You passed a Property!");
        Command onLand = new PrintCommand("You landed on a Property Tile!");
        onPass.addObserver(currentTower);
        onLand.addObserver(currentTower);
        tile.setOnPassCommand(onPass);
        tile.setOnLandCommand(onLand);
        return tile;
    }

}

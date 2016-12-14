package model.tile;

import model.Board;
import model.command.AddFundsCommand;
import model.command.Command;
import model.command.CompleteLapCommand;
import model.command.FillHandRandomlyCommand;
import model.command.MacroCommand;
import model.command.SubtractFundsCommand;
import model.command.UpgradeAnyTileCommand;
import model.command.UpgradeTileCommand;
import shared.WatchTower;
import shared.enums.TileType;

public class StartTile extends Tile {
    private static final long serialVersionUID = -4081897235836158925L;

    public StartTile(int x, int y, WatchTower currentTower, Board theBoard) {
        super(x, y);
        
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
        setOnPassCommand(onPass);
        setOnLandCommand(onLand);
    }

    @Override
    public TileType getTileType() {
        return TileType.START;
    }

}

package model;

import model.tile.CheckpointTile;
import model.tile.PropertyTile;
import model.tile.StartTile;
import shared.enums.CheckpointColor;

public class BoardFactory {

    public Board getBoard(String boardName) throws IllegalArgumentException {
        System.out.println("BoardFactory.getBoard("+boardName+"); START");
        Board theBoard = new Board();
        theBoard.setName(boardName);

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

    private void createRingsBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(new StartTile(3, 2));

        // Add the Checkpoint Tiles
        theBoard.addTile(new CheckpointTile(5, 0, CheckpointColor.RED));
        theBoard.addTile(new CheckpointTile(0, 2, CheckpointColor.BLU));
        theBoard.addTile(new CheckpointTile(3, 5, CheckpointColor.GRN));
        theBoard.addTile(new CheckpointTile(5, 7, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(new PropertyTile(0, 3, 600));
        theBoard.addTile(new PropertyTile(0, 4, 800));
        theBoard.addTile(new PropertyTile(0, 5, 1000));
        theBoard.addTile(new PropertyTile(1, 2, 600));
        theBoard.addTile(new PropertyTile(1, 5, 800));
        theBoard.addTile(new PropertyTile(2, 2, 400));
        theBoard.addTile(new PropertyTile(2, 5, 600));
        theBoard.addTile(new PropertyTile(2, 6, 550));
        theBoard.addTile(new PropertyTile(2, 7, 500));
        theBoard.addTile(new PropertyTile(3, 0, 220));
        theBoard.addTile(new PropertyTile(3, 1, 200));
        theBoard.addTile(new PropertyTile(3, 3, 400));
        theBoard.addTile(new PropertyTile(3, 4, 600));
        theBoard.addTile(new PropertyTile(3, 7, 450));
        theBoard.addTile(new PropertyTile(4, 0, 250));
        theBoard.addTile(new PropertyTile(4, 2, 200));
        theBoard.addTile(new PropertyTile(4, 4, 550));
        theBoard.addTile(new PropertyTile(4, 7, 350));
        theBoard.addTile(new PropertyTile(5, 1, 250));
        theBoard.addTile(new PropertyTile(5, 2, 220));
        theBoard.addTile(new PropertyTile(5, 4, 500));
        theBoard.addTile(new PropertyTile(5, 5, 450));
        theBoard.addTile(new PropertyTile(5, 6, 350));
    }

    private void createKeybladeBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(new StartTile(3, 2));

        // Add the Checkpoint Tiles
        theBoard.addTile(new CheckpointTile(3, 0, CheckpointColor.RED));
        theBoard.addTile(new CheckpointTile(0, 2, CheckpointColor.BLU));
        theBoard.addTile(new CheckpointTile(3, 4, CheckpointColor.GRN));
        theBoard.addTile(new CheckpointTile(9, 2, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(new PropertyTile(0, 0, 480));
        theBoard.addTile(new PropertyTile(0, 1, 600));
        theBoard.addTile(new PropertyTile(0, 3, 600));
        theBoard.addTile(new PropertyTile(0, 4, 480));
        theBoard.addTile(new PropertyTile(1, 0, 360));
        theBoard.addTile(new PropertyTile(1, 4, 360));
        theBoard.addTile(new PropertyTile(2, 0, 240));
        theBoard.addTile(new PropertyTile(2, 4, 240));
        theBoard.addTile(new PropertyTile(3, 1, 120));
        theBoard.addTile(new PropertyTile(3, 3, 120));
        theBoard.addTile(new PropertyTile(4, 2, 240));
        theBoard.addTile(new PropertyTile(5, 2, 360));
        theBoard.addTile(new PropertyTile(6, 2, 480));
        theBoard.addTile(new PropertyTile(7, 2, 600));
        theBoard.addTile(new PropertyTile(7, 3, 900));
        theBoard.addTile(new PropertyTile(7, 4, 1200));
        theBoard.addTile(new PropertyTile(8, 2, 720));
        theBoard.addTile(new PropertyTile(8, 4, 800));
        theBoard.addTile(new PropertyTile(9, 3, 600));
        theBoard.addTile(new PropertyTile(9, 4, 700));
    }

    private void createSnailshellBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(new StartTile(4, 3));

        // Add the Checkpoint Tiles
        theBoard.addTile(new CheckpointTile(0, 3, CheckpointColor.RED));
        theBoard.addTile(new CheckpointTile(8, 7, CheckpointColor.BLU));
        theBoard.addTile(new CheckpointTile(8, 0, CheckpointColor.GRN));
        theBoard.addTile(new CheckpointTile(6, 5, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(new PropertyTile(3, 3, 400));
        theBoard.addTile(new PropertyTile(4, 2, 550));
        theBoard.addTile(new PropertyTile(5, 2, 700));
        theBoard.addTile(new PropertyTile(6, 2, 700));
        theBoard.addTile(new PropertyTile(6, 3, 700));
        theBoard.addTile(new PropertyTile(6, 4, 550));
        theBoard.addTile(new PropertyTile(5, 5, 550));
        theBoard.addTile(new PropertyTile(4, 5, 600));
        theBoard.addTile(new PropertyTile(3, 5, 600));
        theBoard.addTile(new PropertyTile(2, 5, 550));
        theBoard.addTile(new PropertyTile(2, 4, 400));
        theBoard.addTile(new PropertyTile(2, 3, 800));
        theBoard.addTile(new PropertyTile(2, 2, 400));
        theBoard.addTile(new PropertyTile(2, 1, 250));
        theBoard.addTile(new PropertyTile(2, 0, 100));
        theBoard.addTile(new PropertyTile(3, 0, 1000));
        theBoard.addTile(new PropertyTile(4, 0, 100));
        theBoard.addTile(new PropertyTile(5, 0, 150));
        theBoard.addTile(new PropertyTile(6, 0, 200));
        theBoard.addTile(new PropertyTile(7, 0, 250));
        theBoard.addTile(new PropertyTile(8, 1, 300));
        theBoard.addTile(new PropertyTile(8, 2, 600));
        theBoard.addTile(new PropertyTile(8, 3, 900));
        theBoard.addTile(new PropertyTile(8, 4, 900));
        theBoard.addTile(new PropertyTile(8, 5, 600));
        theBoard.addTile(new PropertyTile(8, 6, 300));
        theBoard.addTile(new PropertyTile(7, 7, 300));
        theBoard.addTile(new PropertyTile(6, 7, 240));
        theBoard.addTile(new PropertyTile(5, 7, 200));
        theBoard.addTile(new PropertyTile(4, 7, 160));
        theBoard.addTile(new PropertyTile(3, 7, 120));
        theBoard.addTile(new PropertyTile(2, 7, 12000));
        theBoard.addTile(new PropertyTile(1, 7, 120));
        theBoard.addTile(new PropertyTile(0, 7, 160));
        theBoard.addTile(new PropertyTile(0, 6, 200));
        theBoard.addTile(new PropertyTile(0, 5, 240));
        theBoard.addTile(new PropertyTile(0, 4, 300));
        theBoard.addTile(new PropertyTile(1, 3, 400));
    }

    private void createButterflyBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(new StartTile(7, 3));

        // Add the Checkpoint Tiles
        theBoard.addTile(new CheckpointTile(8, 0, CheckpointColor.RED));
        theBoard.addTile(new CheckpointTile(8, 6, CheckpointColor.BLU));
        theBoard.addTile(new CheckpointTile(1, 3, CheckpointColor.GRN));
        theBoard.addTile(new CheckpointTile(5, 2, CheckpointColor.YLW));

        // Add the Property Tiles
        // The ring in the center
        theBoard.addTile(new PropertyTile(4, 2, 100));
        theBoard.addTile(new PropertyTile(3, 2, 900));
        theBoard.addTile(new PropertyTile(3, 3, 100));
        theBoard.addTile(new PropertyTile(3, 4, 1200));
        theBoard.addTile(new PropertyTile(4, 4, 100));
        theBoard.addTile(new PropertyTile(5, 4, 900));
        theBoard.addTile(new PropertyTile(5, 3, 100));
        // West Wing
        theBoard.addTile(new PropertyTile(3, 1, 600));
        theBoard.addTile(new PropertyTile(3, 0, 400));
        theBoard.addTile(new PropertyTile(2, 0, 300));
        theBoard.addTile(new PropertyTile(1, 0, 200));
        theBoard.addTile(new PropertyTile(0, 0, 900));
        theBoard.addTile(new PropertyTile(0, 1, 750));
        theBoard.addTile(new PropertyTile(0, 2, 550));
        theBoard.addTile(new PropertyTile(1, 2, 300));
        theBoard.addTile(new PropertyTile(2, 3, 300));
        theBoard.addTile(new PropertyTile(1, 4, 300));
        theBoard.addTile(new PropertyTile(0, 4, 550));
        theBoard.addTile(new PropertyTile(0, 5, 750));
        theBoard.addTile(new PropertyTile(0, 6, 900));
        theBoard.addTile(new PropertyTile(1, 6, 200));
        theBoard.addTile(new PropertyTile(2, 6, 300));
        theBoard.addTile(new PropertyTile(3, 6, 400));
        theBoard.addTile(new PropertyTile(3, 5, 600));
        // East Wing
        theBoard.addTile(new PropertyTile(5, 1, 600));
        theBoard.addTile(new PropertyTile(5, 0, 777));
        theBoard.addTile(new PropertyTile(6, 0, 600));
        theBoard.addTile(new PropertyTile(7, 0, 480));
        theBoard.addTile(new PropertyTile(8, 1, 360));
        theBoard.addTile(new PropertyTile(8, 2, 240));
        theBoard.addTile(new PropertyTile(7, 2, 120));
        theBoard.addTile(new PropertyTile(6, 3, 300));
        theBoard.addTile(new PropertyTile(7, 4, 120));
        theBoard.addTile(new PropertyTile(8, 4, 240));
        theBoard.addTile(new PropertyTile(8, 5, 360));
        theBoard.addTile(new PropertyTile(7, 6, 480));
        theBoard.addTile(new PropertyTile(6, 6, 600));
        theBoard.addTile(new PropertyTile(5, 6, 777));
        theBoard.addTile(new PropertyTile(5, 5, 600));
    }

    private void createHoneypotBoard(Board theBoard) {
        // Add the Start Tile
        theBoard.addStartingTile(new StartTile(3, 8));

        // Add the Checkpoint Tiles
        theBoard.addTile(new CheckpointTile(6, 0, CheckpointColor.RED));
        theBoard.addTile(new CheckpointTile(1, 2, CheckpointColor.BLU));
        theBoard.addTile(new CheckpointTile(0, 7, CheckpointColor.GRN));
        theBoard.addTile(new CheckpointTile(6, 7, CheckpointColor.YLW));

        // Add the Property Tiles
        theBoard.addTile(new PropertyTile(0, 0, 650));
        theBoard.addTile(new PropertyTile(1, 0, 600));
        theBoard.addTile(new PropertyTile(2, 0, 550));
        theBoard.addTile(new PropertyTile(3, 0, 400));
        theBoard.addTile(new PropertyTile(4, 0, 550));
        theBoard.addTile(new PropertyTile(5, 0, 600));
        theBoard.addTile(new PropertyTile(0, 1, 700));
        theBoard.addTile(new PropertyTile(6, 1, 600));
        theBoard.addTile(new PropertyTile(0, 2, 750));
        theBoard.addTile(new PropertyTile(2, 2, 750));
        theBoard.addTile(new PropertyTile(3, 2, 1000));
        theBoard.addTile(new PropertyTile(5, 2, 700));
        theBoard.addTile(new PropertyTile(6, 2, 650));
        theBoard.addTile(new PropertyTile(1, 3, 750));
        theBoard.addTile(new PropertyTile(3, 3, 1000));
        theBoard.addTile(new PropertyTile(4, 3, 850));
        theBoard.addTile(new PropertyTile(5, 3, 750));
        theBoard.addTile(new PropertyTile(0, 4, 650));
        theBoard.addTile(new PropertyTile(1, 4, 700));
        theBoard.addTile(new PropertyTile(5, 4, 700));
        theBoard.addTile(new PropertyTile(6, 4, 650));
        theBoard.addTile(new PropertyTile(0, 5, 600));
        theBoard.addTile(new PropertyTile(6, 5, 600));
        theBoard.addTile(new PropertyTile(0, 6, 500));
        theBoard.addTile(new PropertyTile(6, 6, 500));
        theBoard.addTile(new PropertyTile(1, 7, 480));
        theBoard.addTile(new PropertyTile(5, 7, 300));
        theBoard.addTile(new PropertyTile(1, 8, 360));
        theBoard.addTile(new PropertyTile(2, 8, 240));
        theBoard.addTile(new PropertyTile(4, 8, 300));
        theBoard.addTile(new PropertyTile(5, 8, 300));
    }

}

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
        case "Default":
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
            break;

        default:
            throw new IllegalArgumentException("I don't know how to make a "+boardName+" Board!");
        }

        // Done editing the board
        theBoard.setFinished();
        System.out.println("BoardFactory.getBoard("+boardName+"); END");
        return theBoard;
    }

}

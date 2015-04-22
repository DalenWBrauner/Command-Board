package model;

import model.tile.CheckpointTile;
import model.tile.PropertyTile;
import model.tile.StartTile;

public class BoardFactory {

    public Board getBoard(String boardName) throws IllegalArgumentException {
        System.out.println("BoardFactory.getBoard("+boardName+"); START");
        Board theBoard = new Board();
        theBoard.setName(boardName);

        // Edit the board, add tiles, etc.
        if (boardName.equals("Default")) {

            // Add the Start Tile
            theBoard.addTile(new StartTile(3, 2));

            // Add the Checkpoint Tiles
            theBoard.addTile(new CheckpointTile(5, 0));
            theBoard.addTile(new CheckpointTile(0, 2));
            theBoard.addTile(new CheckpointTile(3, 5));
            theBoard.addTile(new CheckpointTile(5, 7));

            // Add the Property Tiles
            theBoard.addTile(new PropertyTile(0, 3));
            theBoard.addTile(new PropertyTile(0, 4));
            theBoard.addTile(new PropertyTile(0, 5));
            theBoard.addTile(new PropertyTile(1, 2));
            theBoard.addTile(new PropertyTile(1, 5));
            theBoard.addTile(new PropertyTile(2, 2));
            theBoard.addTile(new PropertyTile(2, 5));
            theBoard.addTile(new PropertyTile(2, 6));
            theBoard.addTile(new PropertyTile(2, 7));
            theBoard.addTile(new PropertyTile(3, 0));
            theBoard.addTile(new PropertyTile(3, 1));
            theBoard.addTile(new PropertyTile(3, 3));
            theBoard.addTile(new PropertyTile(3, 4));
            theBoard.addTile(new PropertyTile(3, 7));
            theBoard.addTile(new PropertyTile(4, 0));
            theBoard.addTile(new PropertyTile(4, 2));
            theBoard.addTile(new PropertyTile(4, 4));
            theBoard.addTile(new PropertyTile(4, 7));
            theBoard.addTile(new PropertyTile(5, 1));
            theBoard.addTile(new PropertyTile(5, 2));
            theBoard.addTile(new PropertyTile(5, 4));
            theBoard.addTile(new PropertyTile(5, 5));
            theBoard.addTile(new PropertyTile(5, 6));
            theBoard.addTile(new PropertyTile(5, 7));


        // Of course, if we've never heard of this board before...
        } else {
            throw new IllegalArgumentException("I don't know how to make a "+boardName+" Board!");
        }

        // Done editing the board
        theBoard.setFinished();
        System.out.println("BoardFactory.getBoard("+boardName+"); END");
        return theBoard;
    }

}

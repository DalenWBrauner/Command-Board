package model;

public class BoardFactory {

    public Board getBoard(String boardName) {
        System.out.println("BoardFactory.getBoard("+boardName+"); START");
        Board theBoard = new Board();

        // Edit the board
        // add tiles etc.

        // Done editing the board
        theBoard.setFinished();
        System.out.println("BoardFactory.getBoard("+boardName+"); END");
        return theBoard;
    }

}

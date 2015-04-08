package model;

import java.util.Collection;
import java.util.Observable;

public class Match extends Observable {

    private Board theBoard;
    private Collection<Player> players;

    public Match(Board requestedBoard, Collection<Player> playersInTurnOrder) {
        System.out.println("new Match();");
        theBoard = requestedBoard;
        players = playersInTurnOrder;
    }

    /** Starts the game */
    public void start() {
        System.out.println("Match.start()");
    }
}

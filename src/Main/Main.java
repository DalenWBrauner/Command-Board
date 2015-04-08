package Main;

import model.Match;
import model.MatchFactory;

public class Main {

    // Feel free to change these, they are arbitrary (for now)
    private final static int DEFAULT_NUMBER_OF_PLAYERS = 3;
    private final static int DEFAULT_CASH_GOAL = 9000;
    private final static String DEFAULT_BOARD = "Default";

    private final static MatchFactory theMatchFactory = new MatchFactory();

    public static void main(String[] args) {
        System.out.println("main(); START");

        // Start with the default behavior
        int numberOfPlayers = DEFAULT_NUMBER_OF_PLAYERS;
        int cashGoal = DEFAULT_CASH_GOAL;
        String whichBoard = DEFAULT_BOARD;

        // Command-line arguments override default behavior
        if (args.length >= 3) {
            numberOfPlayers = Integer.valueOf(args[0]);
            cashGoal = Integer.valueOf(args[1]);
            whichBoard = args[2];

        } else if (args.length == 2) {
            numberOfPlayers = Integer.valueOf(args[0]);
            cashGoal = Integer.valueOf(args[1]);

        } else if (args.length == 1) {
            numberOfPlayers = Integer.valueOf(args[0]);
        }

        Match theMatch = theMatchFactory.createMatch(numberOfPlayers, cashGoal, whichBoard);
        theMatch.start();
        System.out.println("main(); END");
    }
}

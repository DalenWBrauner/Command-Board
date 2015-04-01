package model;

public class MatchFactory {

    public Match createMatch(int numberOfPlayers, int cashGoal, String whichBoard, int[] turnOrder) {

        System.out.println("There are " + numberOfPlayers + " players.");
        for (int i = 0; i < turnOrder.length; i++) {
            System.out.println("Player "+turnOrder[i]+" goes "+i+"th.");
        }
        System.out.println("We're playing on the "+whichBoard+" Board!");
        System.out.println("First one back to the start with $"+cashGoal+" wins!");

        return new Match();
    }
}

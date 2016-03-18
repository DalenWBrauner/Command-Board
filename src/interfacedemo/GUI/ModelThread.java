package interfacedemo.GUI;

import interfacedemo.questions.GUIRep;
import model.Match;
import model.MatchFactory;
import model.player.Player;

public class ModelThread implements Runnable {
	
	private final MatchFactory mFactory = new MatchFactory();
	private Match theMatch;
	private GUIRep rep;
	private boolean currentlyRunning = false;
	
	public ModelThread(GUIRep rep, int numPlayers, int cashGoal, String whichBoard) {
		theMatch = mFactory.createMatch(numPlayers, cashGoal, whichBoard);
		this.rep = rep;
		rep.register(this);
		rep.register(theMatch);
	}
	
	public ModelThread(GUIRep rep, int numPlayers, int cashGoal, String whichBoard, long seed) {
		theMatch = mFactory.createMatch(numPlayers, cashGoal, whichBoard, seed);
		this.rep = rep;
		rep.register(this);
		rep.register(theMatch);
	}
	
	@Override
	public void run() {
		System.out.println("On your marks...");
		currentlyRunning = true;
		
		System.out.print("Get set... ");
        // Set all the players to use the GUI
        for (Player player : theMatch.getAllPlayers()) {
        	player.setRepresentative(rep);
        }

        // Start the game
        System.out.println("GO!");
        theMatch.run();
        
        // The game is over.
        System.out.println("GAME OVER!");
        currentlyRunning = false;
	}
	
	public synchronized boolean inProgress() { return currentlyRunning; }
}

package interfacedemo.questions;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Random;

import javafx.application.Platform;
import model.Hand;
import model.Match;
import model.player.Player;
import model.tile.PropertyTile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;

public class GUIRep implements PlayerRepresentative {
	private static final long serialVersionUID = 8803249617193948461L;
	private static final Random r = new Random();
	
	private Runnable gameThread;
	
	private WhichOption		popupBool; // Returns a boolean
	private WhichCard		popupCard; // Returns a CardShape
	private WhichDirection	popupDirection; // Returns a CardinalDirection
	private WhichOption 	popupOption; // Returns a String
	private WhichPlayer		popupPlayer; // Returns a PlayerID
	private WhichSpell		popupSpell; // Returns a SpellID
	private WhichTile		popupTile; // Returns a Tile Position (int[x, y])
	
	private Match theMatch;
	
	/*
	* Methods called by the GUI Thread
	*/
	
	/* Register the GUI objects */
	@SuppressWarnings("rawtypes")
	private void register(PlayerQuestion p) { p.setRepresentative(this); }
	// The only difference between these is which private variable stores the value.
	public void registerBool(WhichOption p)			{ register(p); popupBool = p; }
	public void registerCard(WhichCard p)			{ register(p); popupCard = p; }
	public void registerDirection(WhichDirection p)	{ register(p); popupDirection = p; }
	public void registerOption(WhichOption p)		{ register(p); popupOption = p; }
	public void registerPlayer(WhichPlayer p)		{ register(p); popupPlayer = p; }
	public void registerSpell(WhichSpell p)			{ register(p); popupSpell = p; }
	public void registerTile(WhichTile p)			{ register(p); popupTile = p; }
	
	/** Inform the game thread that a question was answered. */
	public void iveAnswered() {
		synchronized(gameThread) { gameThread.notify(); }
	}
	
	/*
	* Methods called by the GameThread
	*/
	
	public void register(Runnable parentThread) { gameThread = parentThread; }
	
	public void register(Match match) { theMatch = match; }
	
	/** Wait for a response from an element of the GUI thread. */ 
	private void waitForUserToAnswer() {
		synchronized(gameThread) {
			try {
				gameThread.wait();
			} catch (InterruptedException e) {
				System.out.println(
					"ERR: GameThread Interrupted while calling wait();");
			}
		}
	}

	/** Tell the GUI to ask the player a specific question */
	public synchronized String askQuestion(String question, String[] options) {
		
		// Present the question
		Platform.runLater(() -> {
			popupOption.setQuestion(question);
			popupOption.resetAnswer();
			popupOption.setOptions(options);
			popupOption.show();
		});

		waitForUserToAnswer();
		
		// Return the answer
		String answer = popupOption.getAnswer();
		System.out.println(answer);
		return answer;
	}

	@Override
	public synchronized SpellID getSpellCast(SpellID[] availableSpells)
			throws RemoteException {
		
		// Present the question
		Platform.runLater(() -> {
			popupSpell.resetAnswer();
			popupSpell.setQuestion("Which spell would you like to cast?");
			popupSpell.setOptions(availableSpells);
			popupSpell.show();
		});
		
		waitForUserToAnswer();
		
		// Return the answer
		SpellID answer = popupSpell.getAnswer();
		System.out.println(answer.toString());
		return answer;
	}

	@Override
	public synchronized int getUsersRoll()
			throws RemoteException {
		
		// Setup the options
		String[] options = new String[] {
			Integer.toString( r.nextInt(6)+1 )
		};
		
		// Present the question
		Platform.runLater(() -> {
			popupOption.resetAnswer();
			popupOption.setQuestion("ROLL THE DICE!");
			popupOption.setOptions(options);
			popupOption.show();
		});
		
		waitForUserToAnswer();

		// Return the answer
		int answer = Integer.parseInt(popupOption.getAnswer());
		System.out.println(answer);
		return answer;
	}

	@Override
	public synchronized CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections)
			throws RemoteException {
		
		// Present the question
		Platform.runLater(() -> {
			popupDirection.resetAnswer();
			popupDirection.setQuestion("Which way are you going?");
			popupDirection.setOptions(availableDirections);
			popupDirection.show();
		});

		waitForUserToAnswer();

		// Return the answer
		CardinalDirection answer = popupDirection.getAnswer();
		System.out.println(answer.toString());
		return answer;
	}

	@Override
	public synchronized boolean buyThisTile(PropertyTile tileForPurchase)
			throws RemoteException {
		
		// Setup the options
		String[] options = new String[] {
				String.valueOf(true),
				String.valueOf(false)
		};
		
		// Setup the question
		String q = "Would you like to buy the tile at ";
		q += String.valueOf(tileForPurchase.getX());
		q += ", ";
		q += String.valueOf(tileForPurchase.getY());
		q += "?";
		final String question = q;
		
		// Present the question
		Platform.runLater(() -> {
			popupBool.resetAnswer();
			popupBool.setQuestion(question);
			popupBool.setOptions(options);
			popupBool.show();
		});
		
		waitForUserToAnswer();
		
		// Return the answer
		boolean answer = Boolean.parseBoolean(popupBool.getAnswer());
		System.out.println(answer);
		return answer;
	}

	@Override
	public synchronized CardShape placeWhichCard()
			throws RemoteException {
		
		// Get the options from the Match
//		CardShape[] options = theMatch.getPlayer(theMatch.getCurrentPlayerID()).getHand().getAllCards();
		System.out.println("Step 1...");
		PlayerID currentPlayerID = theMatch.getCurrentPlayerID();
		System.out.println("Step 2...");
		Player currentPlayer = theMatch.getPlayer(currentPlayerID);
		System.out.println("Step 3...");
		Hand theirHand = currentPlayer.getHand();
		System.out.println("Step 4...");
		CardShape[] options = theirHand.getAllCards();
		System.out.println("Step 5...");
		
		
		for (CardShape o : options) {
			System.out.println(o.toString());
		}
		
		// Present the question
		Platform.runLater(() -> {
			popupCard.resetAnswer();
			popupCard.setQuestion("Which card would you like to place?");
			popupCard.setOptions(options);
			popupCard.show();
		});

		waitForUserToAnswer();

		// Return the answer
		CardShape answer = popupCard.getAnswer();
		System.out.println(answer.toString());
		return answer;
	}

	@Override
	public synchronized CardShape swapCardOnThisTile(PropertyTile tileForSwapping)
			throws RemoteException {
		
		// Setup question
		String q = "What card would you like to swap for the ";
		q += tileForSwapping.getCard().toString();
		q += "?";
		final String question = q;
		
		// Get the options from the Match
		CardShape[] options = theMatch.getPlayer(theMatch.getCurrentPlayerID()).getHand().getAllCards();
		
		// Present the question
		Platform.runLater(() -> {
			popupCard.resetAnswer();
			popupCard.setQuestion(question);
			popupCard.setOptions(options);
			popupCard.show();
		});

		waitForUserToAnswer();

		// Return the answer
		CardShape answer = popupCard.getAnswer();
		System.out.println(answer.toString());
		return answer;
	}
	
	@Override
	public synchronized int[] swapCardOnWhichTile()
			throws RemoteException {
		
		// Setup question
		String question = "Which tile would you like to swap cards with?";
		
		// Get the options from the Match
		ArrayList<PropertyTile> swappableTiles =
				theMatch.getPlayer(theMatch.getCurrentPlayerID()).getTilesOwned();
		
		// Rephrase them as positions
		int[][] options = new int[swappableTiles.size()][2];
		for (int i = 0; i < options.length; i++) {
			options[i] = swappableTiles.get(i).getPos();
		}
		
//		// Setup options
//		int[][] options = new int[9][2];
//		int i = 0;
//		for (int j = 0; j < 3; j++) {
//			for (int k = 0; k < 3; k++) {
//				options[i] = new int[]{j, k}; 
//				i++;
//			}
//		}
		
		// Present the question
		Platform.runLater(() -> {
			popupTile.resetAnswer();
			popupTile.setQuestion(question);
			popupTile.setOptions(options);
			popupTile.show();
		});

		waitForUserToAnswer();
		
		// Return the answer
		int[] answer = popupTile.getAnswer();
		System.out.println(String.valueOf(answer[0]) + "," + String.valueOf(answer[1]));
		return answer;
	}

	@Override
	public synchronized int[] upgradeWhichTile(PropertyTile[] upgradeableTiles)
			throws RemoteException {
		
		// Setup question
		String question = "Which tile would you like to upgrade?";
		
		// Setup options
		int[][] options = new int[upgradeableTiles.length][2];
		for (int i = 0; i < options.length; i++) {
			options[i] = upgradeableTiles[i].getPos();
//			options[i] = new int[] { upgradeableTiles[i].getX(), upgradeableTiles[i].getY()};
		}
		
		// Present the question
		Platform.runLater(() -> {
			popupTile.resetAnswer();
			popupTile.setQuestion(question);
			popupTile.setOptions(options);
			popupTile.show();
		});

		waitForUserToAnswer();

		// Return the answer
		int[] answer = popupTile.getAnswer();
		System.out.println(String.valueOf(answer[0]) + "," + String.valueOf(answer[1]));
		return answer;
	}

	@Override
	public synchronized int upgradeToWhatLevel(PropertyTile upgradingTile)
			throws RemoteException {
		
		// Setup question
		String question = "What level would you like to upgrade this tile to?";
		
		String[] options;
		// Check if there are even any levels left to upgrade to
		int level = upgradingTile.getLevel();
		if (level >= 5) {
			options = new String[] { "5" };
		
		// Otherwise offer all remaining levels
		} else {
			options = new String[5 - level];
			for (int i = 0; i < 5 - level; i++) {
				options[i] = String.valueOf(i + 1 + level);
			}
		}
		
		// Present the question
		Platform.runLater(() -> {
			popupOption.resetAnswer();
			popupOption.setQuestion(question);
			popupOption.setOptions(options);
			popupOption.show();
		});

		waitForUserToAnswer();

		// Return the answer
		int answer = Integer.parseInt(popupOption.getAnswer());
		System.out.println(String.valueOf(answer));
		return answer;
	}

	@Override
	public synchronized int[] sellWhichTile(PlayerID sellingPlayer)
			throws RemoteException {
		
		// Setup question
		String question = "Which tile would you like to sell?";

		// Get the options from the Match
		ArrayList<PropertyTile> sellableTiles =
				theMatch.getPlayer(sellingPlayer).getTilesOwned();
		
		// Rephrase them as positions
		int[][] options = new int[sellableTiles.size()][2];
		for (int i = 0; i < options.length; i++) {
			options[i] = sellableTiles.get(i).getPos();
		}
		
//		// Setup options
//		int[][] options = new int[9][2];
//		int i = 0;
//		for (int j = 0; j < 3; j++) {
//			for (int k = 0; k < 3; k++) {
//				options[i] = new int[]{j, k}; 
//				i++;
//			}
//		}
		
		// Present the question
		Platform.runLater(() -> {
			popupTile.resetAnswer();
			popupTile.setQuestion(question);
			popupTile.setOptions(options);
			popupTile.show();
		});

		waitForUserToAnswer();

		// Return the answer
		int[] answer = popupTile.getAnswer();
		System.out.println(String.valueOf(answer[0]) + "," + String.valueOf(answer[1]));
		return answer;
	}

	@Override
	public synchronized PlayerID castOnPlayer(SpellID spellCast)
			throws RemoteException {
		
		// Setup question
		String q = "Who would you like to cast ";
		q += spellCast.toString();
		q += " on?";
		final String question = q;
		
		// Get the options from the Match
		ArrayList<Player> players = theMatch.getAllPlayers();
		
		// Rephrase them as PlayerIDs
		PlayerID[] options = new PlayerID[players.size()];
		for (int i = 0; i < options.length; i++) {
			options[i] = players.get(i).getID();
		}
		
		// Present the question
		Platform.runLater(() -> {
			popupPlayer.resetAnswer();
			popupPlayer.setQuestion(question);
			popupPlayer.setOptions(PlayerID.values());
			popupPlayer.show();
		});

		waitForUserToAnswer();

		// Return the answer
		PlayerID answer = popupPlayer.getAnswer();
		System.out.println(answer.toString());
		return answer;
	}

	/** Parse an int[] out of an identical-looking String. */
	private int[] parsePosition(String position) {
		int[] answer = new int[2];
		answer[0] = Integer.valueOf(position.split(",")[0]); // X value
		answer[1] = Integer.valueOf(position.split(",")[1]); // Y value
		return answer;
	}
}
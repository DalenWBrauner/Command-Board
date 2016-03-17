package interfacedemo.GUI;

import interfacedemo.questions.GUIRep;
import interfacedemo.questions.WhichCard;
import interfacedemo.questions.WhichDirection;
import interfacedemo.questions.WhichOption;
import interfacedemo.questions.WhichPlayer;
import interfacedemo.questions.WhichSpell;
import interfacedemo.questions.WhichTile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GUI extends Application {

	private double SCREEN_WIDTH = 800;
	private double SCREEN_HEIGHT = 600;
	
	private StackPane root = new StackPane();
	private StackPane popupStack = new StackPane();
	private GridPane overlay = new GridPane();
	private ModelThread gamethread;
	private GUIRep asker = new GUIRep();

	private Button makeDummyButton() {
		
		// Create the button and its effect 
		Button b = new Button("hey there");
		b.setOnAction(
				(event) -> {
					System.out.println("hey there");
				}
		);
		return b;
	}
	
	private Button makeQuitButton() {
		
		// Create the button and its effect 
		Button b = new Button("Quit");
		b.setOnAction(
				(event) -> {
					Platform.exit();
				}
		);
		return b;
	}

	@Override
	public void start(Stage theStage) {
		setupPopups();
		
		// Stick a dummy button in the top left corner
		overlay.add(makeDummyButton(), 0, 0);
		
		// Stick a quit button right next to it
		overlay.add(makeQuitButton(), 1, 0);
		
		// Make a space for all the popups right under them
		root.getChildren().addAll(overlay, popupStack);

		// We wanna be able to click everything
		Utility.giveChildrenClickTransparency(root);
		
		// Let's start a game
		gamethread = getAGame(asker);
		
		// Display the GUI
		Scene theScene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
		theStage.setScene(theScene);
		theStage.show();
		
		// Launch the thread
		Thread t = new Thread(gamethread);
		t.setDaemon(true);
		t.start();
	}
	
	private ModelThread getAGame(GUIRep iNeedThis) {
		// Let's choose some options here...
		return new ModelThread(iNeedThis, 2, 6000, "Keyblade");
	}
	
	private void setupPopups() {
		
		WhichOption		popupBool		= new WhichOption();
		WhichCard		popupCard		= new WhichCard();
		WhichDirection	popupDirection	= new WhichDirection();
		WhichOption		popupOption		= new WhichOption();
		WhichPlayer		popupPlayer		= new WhichPlayer();
		WhichSpell		popupSpell		= new WhichSpell();
		WhichTile		popupTile		= new WhichTile();
		
		asker.registerBool(popupBool);
		asker.registerCard(popupCard);
		asker.registerDirection(popupDirection);
		asker.registerOption(popupOption);
		asker.registerPlayer(popupPlayer);
		asker.registerSpell(popupSpell);
		asker.registerTile(popupTile);		
		
		popupStack.getChildren().addAll(
				popupBool, popupCard, popupDirection, popupOption,
				popupPlayer, popupSpell, popupTile);
	}

	public static void main(String[] args) { launch(args);}
}

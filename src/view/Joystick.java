package view;

import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import model.Match;
import model.Wallet;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;

/**
 * 
 * @author Shane Caldwell
 * 
 * This class allows the player to interact with the model in order to play the game.
 * It is comprised of several groups of buttons that either return information to the model or launch new stages which the user interacts with.
 * 
 * In order to stop the model thread, it is necessary to pause it and wait for user input. The methods used in this view are deprecated, but we've suffered no bugs with them.
 * 
 * The scheme is this: Our Heuristic for view/model interactions comes in the form of "questions". The model is running the rules of the game the best it can, but then runs into something it can't answer.
 * For example:
 * What did a player roll?
 * Did a player cast a spell?
 * In a fork in the road, which way do they travel?
 * 
 * The question takes the form of a method call in MatchView. The model passes useful information - the directions that are available, for instance and a call is made to Joystick in order to allow the user
 * to make a decision. This call can light up useful buttons. If the model needs a response in order to keep running and nothing can be done without user input, we pause the thread. Then, in the handle() method of each
 * of the buttons, we make sure that if you select a valid option, the model then resumes. 
 * 
 * Granted, our model thread is instantiated in MenuScreen, which was then made static.
 * So the call you'll see is
 * MenuScreenView.Model.resume()
 * 
 * In the future, we'd like to find a better way around this, but time constraints made us a little more hacky in how we found a solution. 
 *
 */
public class Joystick implements Observer  {

	
	//Different groups have different functions. 
	private Group directionGroup;
	private Group commandGroup;
	private Group wallet;
	private Group mainGroup;

	//Declares button as class variables in order to give them different handlers in methods.
	Button left;
	Button right;
	Button up;
	Button down;
	//Buttons for 
	Button select;
	Button spells;

	//Holds the money and net value to be dsplayed. 
	Label money;
	Label netVal;
	
	//Holds the view you'll select spells from
	spellView spell;
	
	//Holds data to give to the model.
	SpellID castedSpell = SpellID.NOSPELL;
	private int answer;
	CardinalDirection chosenDirection = CardinalDirection.NONE;

	//Holds the Match Object
	private Match myMatch;
	
	//Generates the group that allows you to see all of the user's wallets at the same time.
	private walletView groupWallet;
	
	//Generates the group that allows you to see all of the checkpoints a user has passed on their own turn.
	private checkpointView checks;

	
	/**
	 * @param None
	 * 
	 * Creates the entirety of the joystick, initializes all its pieces of the view.
	 */
	public Joystick(){
		initJoystick();
	}
	

	/**
	 * Holds the majority of the code to create the joystick, including hardcoded positioning of the different buttons and labels.
	 */
	public void initJoystick(){
		directionGroup = new Group();
		commandGroup = new Group();
		wallet = new Group();

		HBox dButtons = new HBox();
		dButtons.setSpacing(10);
		dButtons.setPadding(new Insets(0, 20, 10, 20));
		dButtons.setPrefWidth(80);
		dButtons.setPrefHeight(40);

		//Fill direction group with buttons
		left = new Button("LEFT");
		left.setPadding(new Insets(10));
		right = new Button("RIGHT");
		right.setPadding(new Insets(10));
		down = new Button("DOWN");
		down.setPadding(new Insets(10));
		up = new Button("UP");
		up.setPadding(new Insets(10));

		left.setMinWidth(dButtons.getPrefWidth());
		left.setMinHeight(dButtons.getPrefHeight());

		right.setMinWidth(dButtons.getPrefWidth());
		right.setMinHeight(dButtons.getPrefHeight());

		down.setMinWidth(dButtons.getPrefWidth());
		down.setMinHeight(dButtons.getPrefHeight());

		up.setMinWidth(dButtons.getPrefWidth());
		up.setMinHeight(dButtons.getPrefHeight());



		dButtons.getChildren().addAll(left, right, up, down);
		dButtons.setAlignment(Pos.CENTER_LEFT);
		directionGroup.getChildren().add(dButtons);

		//Add buttons that will be used execute Commands
		HBox cButtons = new HBox();
		cButtons.setSpacing(10);
		dButtons.setPadding(new Insets(0, 20, 10, 20));
		cButtons.setPrefWidth(80);
		cButtons.setPrefHeight(40);

		select = new Button("SELECT");
		select.setPadding(new Insets(10));

		spells = new Button("SPELLS");
		spells.setPadding(new Insets(10));

		Button cancel = new Button("CANCEL");
		cancel.setPadding(new Insets(10));

		Button menu   = new Button("MENU");
		menu.setPadding(new Insets(10));


		select.setMinHeight(cButtons.getPrefHeight());
		select.setMinWidth(cButtons.getPrefWidth());

		spells.setMinHeight(cButtons.getPrefHeight());
		spells.setMinWidth(cButtons.getPrefWidth());

		cancel.setMinHeight(cButtons.getPrefHeight());
		cancel.setMinWidth(cButtons.getPrefWidth());

		menu.setMinHeight(cButtons.getPrefHeight());
		menu.setMinWidth(cButtons.getPrefWidth());



		cButtons.getChildren().addAll(select, spells, cancel, menu);

		commandGroup.getChildren().add(cButtons);

		//Add labels for On hand and and net Value
		money = new Label("ON HAND:              ");
		netVal = new Label("NET VALUE:           ");
		money.setAlignment(Pos.BOTTOM_RIGHT);
		VBox walletbox = new VBox();
		walletbox.setSpacing(20);
		walletbox.getChildren().addAll(money, netVal);

		wallet.getChildren().add(walletbox);

		mainGroup = new Group(directionGroup, commandGroup);

		//Interesting note: The x coordinates can be exactly the same for the dbuttons and select buttons and they won'tline up.
		mainGroup.getChildren().get(0).setLayoutX(10);
		mainGroup.getChildren().get(0).setLayoutY(10);

		//This
		mainGroup.getChildren().get(1).setLayoutX(30);
		mainGroup.getChildren().get(1).setLayoutY(60);
	
	}


/**
 * 
 * @return mainGroup
 * 
 * Returns the mainGroup, which is everything generated by the Joystick or its inner classes. This is what is passed to MatchView and displayed.
 */
	Group getMainGroup(){
		return mainGroup;
	}

	/**
	 * 
	 * @param availableDirections
	 * 
	 * When the model needs to know where a player wants to go at a fork in the road, this is the method called.
	 * 
	 * It doesn't directly return any information, but instead stores it and then returns it in a getter method, erasing the old data at each new call.
	 * 
	 * This was simpler to use our model start/resume paradigm for the model thread, but there are likely more elegant solutions. 
	 */
	void chooseDirection(CardinalDirection[] availableDirections) {
		DropShadow shadow = new DropShadow();

		//Shadow effects are added to buttons if they're usable. Helps guide the user through the game. 
		if(Arrays.asList(availableDirections).contains(CardinalDirection.WEST)){
				left.setEffect(shadow);
		}else{
				left.setEffect(null);
		}


		left.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED LEFT");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.WEST)){
				chosenDirection = CardinalDirection.WEST;
				MenuScreenView.modelThread.resume();
				}
			}
		});
		
		if(Arrays.asList(availableDirections).contains(CardinalDirection.EAST)){
				right.setEffect(shadow);
		}else{
				right.setEffect(null);
		}


		right.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED RIGHT");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.EAST)){
				chosenDirection = CardinalDirection.EAST;
				MenuScreenView.modelThread.resume();
				}
			}
		});

		if(Arrays.asList(availableDirections).contains(CardinalDirection.NORTH)){
			up.setEffect(shadow);
		}else{
			up.setEffect(null);
		}

		up.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED UP");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.NORTH)){
				chosenDirection = CardinalDirection.NORTH;
				MenuScreenView.modelThread.resume();
				}
			}
		});

		
		if(Arrays.asList(availableDirections).contains(CardinalDirection.SOUTH)){
			down.setEffect(shadow);
		}else{
			down.setEffect(null);
		}
		down.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED DOWN");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.SOUTH)){
				chosenDirection = CardinalDirection.SOUTH;
				MenuScreenView.modelThread.resume();
				}
			}
		});

	}
	
	void turnDirectionsOff(){
		//Without this method, the shadows would linger until the next turn, and it throws off the user.
		left.setEffect(null);
		right.setEffect(null);
		up.setEffect(null);
		down.setEffect(null);
		
	}


/**
 * 
 * @return chosenDirection
 * 
 * Returns the piece of data stored by ChooseDirection()
 */
	public CardinalDirection getDirection() {
		return chosenDirection;
	}
	
	/**
	 * Let's a user know whose turn it is with a prompt. The user must close the prompt before it will start over again.
	 */
	public void meetNGreet(){
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Main.Main.prim);
		VBox dialogVbox = new VBox(20);
		dialogVbox.getChildren().add(new Text(myMatch.getCurrentPlayerID() + "'s TURN IS STARTING"));
		Scene dialogScene = new Scene (dialogVbox, 200, 50);
		dialog.setScene(dialogScene);
		dialog.show();
		MenuScreenView.modelThread.resume();

		
	}
	
	/**
	 * Activates both the Select button to roll a dice, as well as the button to cast Spells.
	 */
	public void activateDiceRoll(){
			
		DropShadow shadow = new DropShadow();
		select.setEffect(shadow);
		select.setOnAction(new EventHandler<ActionEvent>(){
			@SuppressWarnings("deprecation")
			@Override public void handle(ActionEvent e){
				Random random = new Random();
				answer = random.nextInt(6) + 1;
				System.out.println("USER ROLLED A " + answer);
				final Stage rollResult = new Stage();
				rollResult.initModality(Modality.APPLICATION_MODAL);
				rollResult.initOwner(Main.Main.prim);
				VBox dialogVbox = new VBox(20);
				dialogVbox.getChildren().add(new Text(myMatch.getCurrentPlayerID() + " rolled a " + answer + "!"));
				Scene dialogScene = new Scene (dialogVbox, 100, 50);
				rollResult.setScene(dialogScene);
				rollResult.show();

				//they rolled the dice, now give the poor kid his control back
				MenuScreenView.modelThread.resume();
			}	
		});
	    spells.setEffect(shadow);
	    spells.setOnAction(new EventHandler<ActionEvent>(){

			@SuppressWarnings("deprecation")
			public void handle(ActionEvent arg0) {
				final Stage spellView = new Stage();
				spellView.initModality(Modality.APPLICATION_MODAL);
				Scene spellScene = new Scene (spell.getMainGroup(), 400, 400);
				spellView.setScene(spellScene);
				spellView.show();

			}
	    	
	    });
	}

	/**
	 * Turns off the buttons for the DiceRoll() call. Transition between turns then appears smoother to users. 
	 */
	public void turnStartOff(){
		select.setEffect(null);
		spells.setEffect(null);
	}
	

	/**
	 * 
	 * @return answer
	 * 
	 * This is the getter for the dice roll call.
	 */
	public int getRollResult(){
		return answer;
	}
	
	/**
	 * 
	 * @return castedSpell;
	 * 
	 * This is the getter for the spell call. 
	 */
	 SpellID castedSpell(){
			return castedSpell;
		}
	
	/**
	 * 
	 * @param m
	 * 
	 * This takes the match object so it can be used to get data from the model.
	 * 
	 * After being stored in the variable myMatch, we proceed to give it to all the objects that are going to need it.
	 * 
	 * Objects for the view that require the match object are also generated here and added to the mainGroup. This could probably be split up into multiple methods.
	 * I experimented with that, but ultimately found in debugging easier when I can easily see where objects are instantiated.
	 */
	public void registerMatch(Match m){
		myMatch = m;
		m.addObserver(this);
		groupWallet = new walletView(myMatch);
		mainGroup.getChildren().add(groupWallet.getWallGroup());
		mainGroup.getChildren().get(2).setLayoutX(500);
		mainGroup.getChildren().get(2).setLayoutY(90);
		spell = new spellView(myMatch);
		Label cashGoal = new Label("CASH GOAL: $" + myMatch.getCashGoal());
		cashGoal.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		mainGroup.getChildren().add(cashGoal);
		mainGroup.getChildren().get(3).setLayoutX(300);
		mainGroup.getChildren().get(3).setLayoutY(200);
		
		checks = new checkpointView(myMatch);
		mainGroup.getChildren().add(checks.getMainGroup());
		mainGroup.getChildren().get(4).setLayoutX(500);
		mainGroup.getChildren().get(4).setLayoutY(40);

	}

	/**
	 * updates the group wallet, everytime the update function is called from the model.
	 */
	public void setWalletText(){
		groupWallet.update();
	}
	
	/**
	 * Updates the checkpoint of the player whose turn it is. The update function is called from the model and then calls this.
	 */
	public void setCheckpointEffect(){
		checks.redraw();
	
	}
	
	
	/**
	 * Platform.runlater() is from the JavaFX concurrency package. It is used in order to make calls from the model that must act on the JavaFX thread.
	 * If you leave this out, another thread will attempt to use JavaFX and the program breaks.
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
		Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			setWalletText();    			
    		}
    	});
		
		Platform.runLater(new Runnable(){
			@Override
			public void run(){
				setCheckpointEffect();
			}
		});
		
	}
	
}
		



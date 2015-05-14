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
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;


public class Joystick implements Observer  {

	private Group directionGroup;
	private Group commandGroup;
	private Group wallet;

	private Group mainGroup;

	Button left;
	Button right;
	Button up;
	Button down;
	
	Button select;
	Button spells;

	
	Label money;
	Label netVal;
	
	spellView spell;
	
	private int answer;

	private Match myMatch;
	
	CardinalDirection chosenDirection = CardinalDirection.NONE;

	public Joystick(){
		initJoystick();
	}

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


		//Bind label to property value, so you can change that and have the label update

		mainGroup = new Group(directionGroup, commandGroup, wallet);
		mainGroup.getChildren().get(2).setLayoutX(500);
		mainGroup.getChildren().get(2).setLayoutY(90);

		//Interesting note: The x coordinates can be exactly the same for the dbuttons and select buttons and they won'tline up.
		mainGroup.getChildren().get(0).setLayoutX(10);
		mainGroup.getChildren().get(0).setLayoutY(10);

		//This
		mainGroup.getChildren().get(1).setLayoutX(30);
		mainGroup.getChildren().get(1).setLayoutY(60);
		
		spell = new spellView();


	}



	Group getMainGroup(){
		return mainGroup;
	}

	void chooseDirection(CardinalDirection[] availableDirections) {
		DropShadow shadow = new DropShadow();

		//Shadow effects.
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
		
		//Shadow effects.
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

		//Shadow effects.
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
		//Turns directions off so they dont stay highlighted.
		left.setEffect(null);
		right.setEffect(null);
		up.setEffect(null);
		down.setEffect(null);
		
	}



	public CardinalDirection getDirection() {
		return chosenDirection;
	}
	
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

	
	public void turnStartOff(){
		select.setEffect(null);
		spells.setEffect(null);
	}

	public int getRollResult(){
		return answer;
	}
	
	public void registerMatch(Match m){
		myMatch = m;
		m.addObserver(this);
	}

	public void setWalletText(){
		PlayerID id = myMatch.getCurrentPlayerID();
		Wallet currentWallet = myMatch.getPlayer(id).getWallet();
		currentWallet.getNetValue();
		currentWallet.getCashOnHand();
		money.setText("ON HAND: $" + currentWallet.getCashOnHand());
		netVal.setText("NET VALUE: $" + currentWallet.getNetValue());
	}
	
	
	@Override
	public void update(Observable arg0, Object arg1) {
		Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			setWalletText();
    		}
    	});
		
	}
}
		



package view;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

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
import model.Match;
import shared.enums.CardinalDirection;
import shared.enums.SpellID;


public class Joystick implements Observer  {
    
    //TODO: Replace this with Guava's
    // EvictingQueue.
    private class LimitedQueue<E> extends LinkedList<E> {
        private int limit;

        public LimitedQueue(int limit) {
            this.limit = limit;
        }

        @Override
        public boolean add(E o) {
            super.add(o);
            while (size() > limit) { super.remove(); }
            return true;
        }
    }

    private LimitedQueue<String> outputQ;
    private Label outputLabel;
    
	private Group directionGroup;
	private Group commandGroup;
	private Group wallet;

	private Group mainGroup;
	
	boolean notInstantiated = true;
	Button left;
	Button right;
	Button up;
	Button down;

	Stage spellView;
	Scene spellScene; 
	
	Button select;
	Button spells;
	
	Button cancel;
	Button menu;


	Label money;
	Label netVal;

	public spellView spell;

	private int answer;

	private Match myMatch;

	private walletView groupWallet;

	private checkpointView checks;

	SpellID castedSpell = SpellID.NOSPELL;

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

		cancel = new Button("CANCEL");
		cancel.setPadding(new Insets(10));

		menu   = new Button("MENU");
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
//		money = new Label("ON HAND:              ");
//		netVal = new Label("NET VALUE:           ");
//		money.setAlignment(Pos.BOTTOM_RIGHT);
//		VBox walletbox = new VBox();
//		walletbox.setSpacing(20);
//		walletbox.getChildren().addAll(money, netVal);
//
//		wallet.getChildren().add(walletbox);


		mainGroup = new Group(directionGroup, commandGroup);

		//Interesting note: The x coordinates can be exactly the same for the dbuttons and select buttons and they won'tline up.
		directionGroup.setLayoutX(10);
		directionGroup.setLayoutY(10);

		//This
		commandGroup.setLayoutX(30);
		commandGroup.setLayoutY(60);

	}



	Group getMainGroup(){
		return mainGroup;
	}

	void chooseDirection(CardinalDirection[] availableDirectionsArr) {
		DropShadow shadow = new DropShadow();

		List<CardinalDirection> availableDirections = Arrays.asList(availableDirectionsArr);
		
		if (availableDirections.contains(CardinalDirection.WEST)) {
		    
		    //Shadow effects.
	        left.setEffect(shadow);
	        
	        // Event handler.
	        left.setOnAction(new EventHandler<ActionEvent>(){
	            @Override public void handle(ActionEvent e) {
	                //addToOutput("CLICKED LEFT");
	                chosenDirection = CardinalDirection.WEST;
                    MenuScreenView.modelThread.resume();
	            }
	        });
		} else {
		    left.setEffect(null);
		}
		

		//Shadow effects.
		if (availableDirections.contains(CardinalDirection.EAST)) {
				right.setEffect(shadow);
				
				right.setOnAction(new EventHandler<ActionEvent>() {
		            @Override public void handle(ActionEvent e) {
		                //addToOutput("CLICKED RIGHT");
		                chosenDirection = CardinalDirection.EAST;
		                MenuScreenView.modelThread.resume();
		            }
		        });
		} else{
				right.setEffect(null);
		}

		if (availableDirections.contains(CardinalDirection.NORTH)) {
			up.setEffect(shadow);
			
			up.setOnAction(new EventHandler<ActionEvent>(){
	            @Override public void handle(ActionEvent e) {
	                //addToOutput("CLICKED UP");
	                chosenDirection = CardinalDirection.NORTH;
	                MenuScreenView.modelThread.resume();
	            }
	        });
		} else {
			up.setEffect(null);
		}

		if (availableDirections.contains(CardinalDirection.SOUTH)) {
			down.setEffect(shadow);
			
			down.setOnAction(new EventHandler<ActionEvent>(){
	            @Override public void handle(ActionEvent e) {
	                //addToOutput("CLICKED DOWN");
	                chosenDirection = CardinalDirection.SOUTH;
	                MenuScreenView.modelThread.resume();
	            }
	        });
		} else {
			down.setEffect(null);
		}
		
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

	public void meetNGreet() {
//		final Stage dialog = new Stage();
//		dialog.initModality(Modality.APPLICATION_MODAL);
//		dialog.initOwner(Main.Main.prim);
//		VBox dialogVbox = new VBox(20);
//		dialogVbox.getChildren().add(new Text(myMatch.getCurrentPlayerID() + "'s TURN IS STARTING"));
//		Scene dialogScene = new Scene (dialogVbox, 200, 50);
//		dialog.setScene(dialogScene);
//		dialog.show();
//		MenuScreenView.modelThread.resume();
	    addToOutput(myMatch.getCurrentPlayerID() + "'s TURN IS STARTING");
	    addToOutput("CHOOSE A SPELL?");
	    MenuScreenView.modelThread.resume();
	}
	
	public void activateDiceRoll(){

	    addToOutput("PRESS SELECT TO ROLL THE DICE");
		DropShadow shadow = new DropShadow();
		select.setEffect(shadow);
		select.setOnAction(new EventHandler<ActionEvent>(){
			@SuppressWarnings("deprecation")
			@Override public void handle(ActionEvent e){
				Random random = new Random();
				answer = random.nextInt(6) + 1;
				//addToOutput("USER ROLLED A " + answer);
//				final Stage rollResult = new Stage();
//				rollResult.initModality(Modality.APPLICATION_MODAL);
//				rollResult.initOwner(Main.Main.prim);
//				VBox dialogVbox = new VBox(20);
//				dialogVbox.getChildren().add(new Text(myMatch.getCurrentPlayerID() + " rolled a " + answer + "!"));
//				Scene dialogScene = new Scene (dialogVbox, 100, 50);
//				rollResult.setScene(dialogScene);
//				rollResult.show();
				addToOutput(myMatch.getCurrentPlayerID() + " rolled a " + answer + "!");

				//they rolled the dice, now give the poor kid his control back
				MenuScreenView.modelThread.resume();
			}
		});
	}
	
	public void activateSpellPhase(){
		DropShadow shadow = new DropShadow();

		if (notInstantiated){
			spells.setEffect(shadow);
			spells.setOnAction(new EventHandler<ActionEvent>(){
			@Override
            @SuppressWarnings("deprecation")
			public void handle(ActionEvent arg0) {
				spellView = new Stage();
				spellView.initModality(Modality.APPLICATION_MODAL);
				spell.drawCards();
				Scene spellScene = new Scene (spell.getMainGroup(), 400, 400);
				spellView.setScene(spellScene);
				spellView.show();
				notInstantiated = false;
			}
	    });
		}else{
			spells.setEffect(shadow);
			spells.setOnAction(new EventHandler<ActionEvent>(){
				@Override
				public void handle(ActionEvent arg0) {
					spellView.show();
					
				}
				
			});
			
		}
	    
	    cancel.setEffect(shadow);
	    cancel.setOnAction(new EventHandler<ActionEvent>(){

			@Override
			public void handle(ActionEvent arg0) {
				castedSpell = SpellID.NOSPELL;
				MenuScreenView.modelThread.resume();
			}
	    });
	    	

	}


	public void turnStartOff(){
		select.setEffect(null);
		spells.setEffect(null);
	}
	
	public void turnSpellOff(){
		cancel.setEffect(null);
		spells.setEffect(null);
	}

	public int getRollResult(){
		return answer;
	}
	
	public void spellReset(){
		castedSpell = SpellID.NOSPELL;
	}

	public void registerMatch(Match m){
		myMatch = m;
		m.addObserver(this);
		
		groupWallet = new walletView(myMatch);
		setWalletText();
		mainGroup.getChildren().add(groupWallet.getWallGroup());
		mainGroup.getChildren().get(2).setLayoutX(500);
		mainGroup.getChildren().get(2).setLayoutY(90);
		
		spell = new spellView(myMatch, this);
		Label cashGoal = new Label("CASH GOAL: $" + myMatch.getCashGoal());
		cashGoal.setFont(Font.font("Verdana", FontPosture.ITALIC, 20));
		mainGroup.getChildren().add(cashGoal);
		mainGroup.getChildren().get(3).setLayoutX(300);
		mainGroup.getChildren().get(3).setLayoutY(200);

		checks = new checkpointView(myMatch);
		mainGroup.getChildren().add(checks.getMainGroup());
		mainGroup.getChildren().get(4).setLayoutX(500);
		mainGroup.getChildren().get(4).setLayoutY(40);
		
		outputLabel = new Label();
		outputQ = new LimitedQueue<>(8);
		outputLabel.setLayoutX(1100);
		outputLabel.setLayoutY(30);
		mainGroup.getChildren().add(outputLabel);

	}
	
	public void addToOutput(String s) {
	    outputQ.add(s);
	    redrawOutput();
	}
	
	private void redrawOutput() {
	    String text = "";
	    for (String s : outputQ) {
	        text += s + "\n";
	    }
	    outputLabel.setText(text);
	}

	public void setWalletText(){
		groupWallet.update();
	}

	public void setCheckpointEffect(){
		checks.redraw();

	}
	
	public void updateSpellView(){
		spell.drawCards();
	}


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

	public SpellID getCastedSpell(){
		return castedSpell;
	}
}




package view;

import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import shared.enums.CardinalDirection;


public class Joystick  {

	private Group directionGroup;
	private Group commandGroup;
	private Group wallet;

	private Group mainGroup;

	Button left;
	Button right;
	Button up;
	Button down;

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

		Button select = new Button("SELECT");
		select.setPadding(new Insets(10));

		Button spells = new Button("SPELLS");
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
		Label money = new Label("ON HAND:              ");
		Label netVal = new Label("NET VALUE:           ");
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

	}



	Group getMainGroup(){
		return mainGroup;
	}

	void chooseDirection(CardinalDirection[] availableDirections) {


		left.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED LEFT");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.WEST)){
				chosenDirection = CardinalDirection.WEST;
				MenuScreenView.modelThread.resume();
				}
			}
		});

		right.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED RIGHT");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.EAST)){
				chosenDirection = CardinalDirection.EAST;
				MenuScreenView.modelThread.resume();
				}
			}
		});

		up.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle(ActionEvent e) {
				System.out.println("CLICKED UP");
				if(Arrays.asList(availableDirections).contains(CardinalDirection.NORTH)){
				chosenDirection = CardinalDirection.NORTH;
				MenuScreenView.modelThread.resume();
				}
			}
		});


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



	public CardinalDirection getDirection() {
		return chosenDirection;
	}

}

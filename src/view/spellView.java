package view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Hand;
import model.Match;
import model.SpellCaster;
import model.player.Player;
import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.enums.SpellID;

public class spellView {

	private Group mainGroup;

	ListView<String> spellList;

	Button cast;
	Button cancel;

	Label Cost;

	Label Description;

	Match myMatch;

	Joystick myStick;

	SpellID castedSpell = SpellID.NOSPELL;
	public spellView(Joystick myStick){
		this.myStick = myStick;
		initSpellView();
	}

	public void initSpellView(){
		DropShadow shadow = new DropShadow();
		spellList = new ListView<String>();
		// ugh
		Set<SpellID> spellSet = SpellCaster.getSpellList();
		spellSet.remove(SpellID.NOSPELL);
		ArrayList<String> spellStrings = new ArrayList<>();
		for (SpellID id : spellSet) {
		    spellStrings.add(id.toString());
		}
		ObservableList<String> spells = FXCollections.observableArrayList(spellStrings);
		// okay
		spellList.setItems(spells);

		spellList.setPrefWidth(400);
		spellList.setPrefHeight(140);

		mainGroup = new Group(spellList);

		//Buttons in use
		HBox buttons = new HBox();

		buttons.setSpacing(10);
		buttons.setPadding(new Insets(0, 20, 10, 20));
		buttons.setPrefWidth(80);
		buttons.setPrefHeight(40);

		cast = new Button("Cast");
		cast.setPadding(new Insets(10));

		cancel = new Button("Cancel");
		cancel.setPadding(new Insets(10));

		cast.setMinWidth(buttons.getPrefWidth());
		cast.setMinHeight(buttons.getPrefHeight());

		cancel.setMinWidth(buttons.getPrefWidth());
		cancel.setMinHeight(buttons.getPrefHeight());

		buttons.getChildren().addAll(cast, cancel);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);

		Group buttonGroup = new Group(buttons);
		mainGroup.getChildren().add(buttonGroup);

		mainGroup.getChildren().get(1).setLayoutX(100);
		mainGroup.getChildren().get(1).setLayoutY(350);

		Cost = new Label("");

		Description = new Label("");

		spellList.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				String val = spellList.getSelectionModel().getSelectedItem();
				SpellID valof = SpellID.fromString(val);
				Cost.setText("COST:  " + calculateCost(valof));

				switch (valof) {
				case SPELL1:
                    Description.setText("Allows you to move backwards!");
                    break;
				case SPELL2:
				    Description.setText("Force a {random} opponent to sell a {random} tile they own!");
				    break;
				case SPELL3:
                    Description.setText("Upgrade a {random} tile {to the next level}!");
                    break;
				case SPELL4:
                    Description.setText("Swap a {random} card in your hand with the card on a"
                            + "\n{random} tile you own!");
                    break;
				case SPELL5:
                    Description.setText("Swap your position on the board with that of a {random} opponent's!");
                    break;
				case SPELL6:
                    Description.setText("Collect cash for every tile your opponent has on the board!");
                    break;
				default:
				    Description.setText(spellList.getSelectionModel().getSelectedItem() + " filler text.");
				    break;
				}


				if (gotTheDough(valof)) {
					cast.setEffect(shadow);
				} else {
					cast.setEffect(null);
				}
			}
		});

		cast.setOnMouseClicked(new EventHandler<MouseEvent>(){
			@Override
			public void handle(MouseEvent arg0) {
				String val = spellList.getSelectionModel().getSelectedItem();
				SpellID valof = SpellID.fromString(val);
				if(gotTheDough(valof)){
					myStick.castedSpell = valof;
					myStick.addToOutput("You chose to cast " + val + "!");
					Stage stage = (Stage) cast.getScene().getWindow();
					stage.close();
					MenuScreenView.modelThread.resume();
				}
			}
		});

		cancel.setOnMouseClicked(new EventHandler<MouseEvent>(){
		    @Override
            public void handle(MouseEvent arg0) {
		        Stage stage = (Stage) cancel.getScene().getWindow();
                stage.close();
            }
		});

		VBox spellStuff = new VBox();
		spellStuff.setSpacing(20);
		spellStuff.getChildren().addAll(Cost, Description);
		mainGroup.getChildren().add(spellStuff);

		mainGroup.getChildren().get(2).setLayoutX(50);
		mainGroup.getChildren().get(2).setLayoutY(200);

//		PlayerID IDcurrentPlayer = myMatch.getCurrentPlayerID();
//		Player currentPlayer = myMatch.getPlayer(IDcurrentPlayer);
//
//		Hand currentHand = currentPlayer.getHand();
//		CardShape[] currentCards = currentHand.getAllCards();
//		HBox cardHolder = new HBox();
//		cardHolder.setSpacing(20);
//		for(int i=0; i > currentCards.length; i++){
//			cardArtist card = new cardArtist(currentCards[i]);
//			cardHolder.getChildren().add(card.getShape());
//		}
//
//		mainGroup.getChildren().addAll(cardHolder);
//		mainGroup.getChildren().get(3).setLayoutY(150);
	}

	public void registerMatch(Match m) {
	    this.myMatch = m;
	}

	public void drawCards(){
//		//Get rid of old cards
//
//		mainGroup.getChildren().remove(3);
//		PlayerID IDcurrentPlayer = myMatch.getCurrentPlayerID();
//		Player currentPlayer = myMatch.getPlayer(IDcurrentPlayer);
//
//		if (!(currentPlayer.getHand() == null)){
//		Hand currentHand = currentPlayer.getHand();
//		CardShape[] currentCards = currentHand.getAllCards();
//		HBox cardHolder = new HBox();
//		cardHolder.setSpacing(20);
//		for(int i=0; i > currentCards.length; i++){
//			cardArtist card = new cardArtist(currentCards[i]);
//			cardHolder.getChildren().add(card.getShape());
//		}
//
//		mainGroup.getChildren().addAll(cardHolder);
//		mainGroup.getChildren().get(3).setLayoutY(150);
	}

	public boolean gotTheDough(SpellID spell){
		HashMap<CardShape, Integer> costs = SpellCaster.getCost(spell);

		int numCircle = costs.get(CardShape.SHAPE1);
		int numSquare = costs.get(CardShape.SHAPE2);
		int numTriangle = costs.get(CardShape.SHAPE3);

		PlayerID IDcurrentPlayer = myMatch.getCurrentPlayerID();
		Player currentPlayer = myMatch.getPlayer(IDcurrentPlayer);
		Hand currentHand = currentPlayer.getHand();
		int yourNumCircle = currentHand.getNumberOfCards(CardShape.SHAPE1);
		int yourNumSquare = currentHand.getNumberOfCards(CardShape.SHAPE2);
		int yourNumTriangle = currentHand.getNumberOfCards(CardShape.SHAPE3);

		if (yourNumTriangle >= numTriangle &&
		    yourNumSquare >= numSquare &&
		    yourNumCircle >= numCircle) {
			return true;
		}
		return false;
	}

	public String calculateCost(SpellID spell){
		String answer = spell.toString() + " costs: ";
		 HashMap<CardShape, Integer> costs = SpellCaster.getCost(spell);
		 int numCircle = costs.get(CardShape.SHAPE1);
		 answer += numCircle + " " + CardShape.SHAPE1.toString() + "s,  ";
		 int numSquare = costs.get(CardShape.SHAPE2);
		 answer += numSquare + " " + CardShape.SHAPE2.toString() + "s,  and ";
		 int numTriangle = costs.get(CardShape.SHAPE3);
		 answer +=  numTriangle + " " + CardShape.SHAPE3.toString() + "s.";
		 return answer;
	}

	public Group getMainGroup(){
		return mainGroup;
	}
}

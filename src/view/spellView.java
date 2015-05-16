package view;

import java.util.HashMap;

import shared.enums.CardShape;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import model.Hand;
import model.Match;
import model.Player;
import model.SpellCaster;
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
	public spellView(Match myMatch, Joystick myStick){
		this.myMatch = myMatch;
		this.myStick = myStick;
		initSpellView();
		
	}
	
	public void initSpellView(){
		DropShadow shadow = new DropShadow();
		spellList = new ListView<String>();
		ObservableList<String> spells = FXCollections.observableArrayList(SpellID.SPELL1.toString(), SpellID.SPELL2.toString(), SpellID.SPELL3.toString(), SpellID.SPELL4.toString());
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
				
				Description.setText(spellList.getSelectionModel().getSelectedItem() + " filler text.");
				
				if(gotTheDough(valof)){
					cast.setEffect(shadow);
				}else{
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
					Stage stage = (Stage) cast.getScene().getWindow();
					MenuScreenView.modelThread.resume();
					
				}
				
			}	
		});
		
		VBox spellStuff = new VBox();
		spellStuff.setSpacing(20);
		spellStuff.getChildren().addAll(Cost, Description);
		mainGroup.getChildren().add(spellStuff);
		
		
		mainGroup.getChildren().get(2).setLayoutX(50);
		mainGroup.getChildren().get(2).setLayoutY(200);
		
		PlayerID IDcurrentPlayer = myMatch.getCurrentPlayerID();
		Player currentPlayer = myMatch.getPlayer(IDcurrentPlayer);
		
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
//		}

		
		
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
		
		if(yourNumTriangle >= numTriangle && yourNumSquare >= numSquare && yourNumCircle >= numCircle){
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

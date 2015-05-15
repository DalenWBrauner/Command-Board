package view;

import model.Match;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * 
 * @author Shane Caldwell
 * 
 * This class is what the user interacts with in order to cast spells and see their hand. 
 *
 */
public class spellView {
	
	//Group returned to joystick.
	private Group mainGroup;
	
	//Made up of the models eviews/enums. 
	ListView<String> spellList;
	
	//Buttons the user interacts with. 
	Button cast;
	Button cancel;
	
	//Label that displays the cost of a given spell.
	Label Cost;
	
	//Label holds the Descriptions. 
	Label Description;
	
	//Holds the match. 
	Match myMatch;
	
	/**
	 * 
	 * @param myMatch
	 * Needs the match object to display user information accurately.
	 * 
	 * Then the init function takes it away.
	 */
	public spellView(Match myMatch){
		this.myMatch = myMatch;
		initSpellView();
		
	}
	
	/**
	 * initSpellView
	 * 
	 * Builds the the group the user will interact with in the scene Launched by the Joystick's "Spell" button.
	 */
	public void initSpellView(){
		spellList = new ListView<String>();
		ObservableList<String> spells = FXCollections.observableArrayList("Spell1", "Spell2", "Spell3", "Spell4", "Spell5", "Spell6", "Spell7");
		spellList.setItems(spells);
		
		spellList.setPrefWidth(400);
		spellList.setPrefHeight(140);
		
		mainGroup = new Group(spellList);
		
		
		//The following is instantiating and positioning buttons.
		
		//Hbox locks buttons in Horizontal to each other.
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
		
		//Instantiate them blank. You don't want them to say anything unless the user requests it.
		Cost = new Label("");
		Description = new Label("");
		
		spellList.setOnMouseClicked(new EventHandler<MouseEvent>(){

			@Override
			public void handle(MouseEvent arg0) {
				Cost.setText("THIS IS THE COST OF: " + spellList.getSelectionModel().getSelectedItem());
				
				Description.setText(spellList.getSelectionModel().getSelectedItem() + " filler text.");
			}
			
		});
		
		VBox spellStuff = new VBox();
		spellStuff.setSpacing(20);
		spellStuff.getChildren().addAll(Cost, Description);
		mainGroup.getChildren().add(spellStuff);
		
		mainGroup.getChildren().get(2).setLayoutX(100);
		mainGroup.getChildren().get(2).setLayoutY(200);
		
		
	}
	
	//Upates to take into account most recent information. 
	public void Update(){
		
	}
	
	public Group getMainGroup(){
		return mainGroup;
	}

}

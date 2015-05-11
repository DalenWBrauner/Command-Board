package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

public class spellView {
	
	private Group mainGroup;
	
	ListView<String> spellList;
	
	Button cast;
	Button cancel;

	
	public spellView(){
		initSpellView();
		
	}
	
	public void initSpellView(){
		spellList = new ListView<String>();
		ObservableList<String> spells = FXCollections.observableArrayList("Spell1", "Spell2", "Spell3");
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




		
	}
	
	public Group getMainGroup(){
		return mainGroup;
	}

}

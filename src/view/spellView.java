package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.control.ListView;

public class spellView {
	
	private Group mainGroup;
	
	ListView<String> spellList;

	
	public spellView(){
		initSpellView();
		
	}
	
	public void initSpellView(){
		spellList = new ListView<String>();
		ObservableList<String> spells = FXCollections.observableArrayList("Spell1", "Spell2", "Spell3");
		spellList.setItems(spells);
		
		spellList.setPrefWidth(200);
		spellList.setPrefHeight(140);
		
		mainGroup = new Group(spellList);
		
	}
	
	public Group getMainGroup(){
		return mainGroup;
	}

}

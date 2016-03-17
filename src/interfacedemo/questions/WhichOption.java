package interfacedemo.questions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import shared.interfaces.PlayerRepresentative;

public class WhichOption extends StackPane implements PlayerQuestion<String> {
	
	// We'll need to notify this later
	private GUIRep asker;
	
	// Menu
	private VBox questionnaire = new VBox(10);
	private HBox optionHolder = new HBox(10);
	
	// Options
	private Label playerQuestion = new Label();
	private String[] playerOptions;
	private int playerSelection;
	
	public WhichOption() {
		
		// Setup the questionnaire
		questionnaire.setAlignment(Pos.CENTER);
		questionnaire.setPadding(new Insets(10,10,10,10));
		questionnaire.getChildren().addAll(playerQuestion,optionHolder);
		
		// Setup the options
		optionHolder.setAlignment(Pos.CENTER);
		optionHolder.setPadding(new Insets(10,10,10,10));
		
		this.getChildren().add(questionnaire);
		this.hide();
	}
	
	@Override
	public synchronized void setQuestion(String q) {
		playerQuestion.setText(q);
	}
	
	@Override
	public synchronized void setOptions(String[] options) {
		playerOptions = options;
		setupButtons();
	}
	
	@Override
	public synchronized void reset() {
		playerQuestion.setText(null);
		playerOptions = null;
		optionHolder.getChildren().clear();
		resetAnswer();
	}
	
	@Override
	public synchronized void resetAnswer() {
		playerSelection = -1;
	}
	
	@Override
	public String getAnswer() {
		return playerOptions[playerSelection];
	}
	
	public int getAnswerIndex() {
		return playerSelection;
	}
	
	@Override
	public synchronized void show() {
		this.setVisible(true);
	}
	
	@Override
	public synchronized void hide() {
		this.setVisible(false);
	}
	
	@Override
	public synchronized void toggleVisibility() {
		this.setVisible(!this.isVisible());
	}
	
	@Override
	public void setRepresentative(PlayerRepresentative rep) {
		asker = (GUIRep) rep;
	}
	
	private synchronized void setupButtons() {
		optionHolder.getChildren().clear();
		
		// For each option,
		int numOptions = playerOptions.length; 
		for (int i = 0; i < numOptions; i++) {
			final int choice = i;
			
			// Create a button
			final Button option = new Button(playerOptions[i]);
			option.setOnAction((event) -> { makeSelection(choice); }
			);
			optionHolder.getChildren().add(option);
		}
	}
	
	private void makeSelection(int selection) {
		hide(); // They've made their choice, we don't need to present it any longer
		playerSelection = selection;
//		System.out.println(playerSelection);
		
		// Let the game thread know it's been made
		asker.iveAnswered();
	}
}

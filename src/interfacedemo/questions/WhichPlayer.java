package interfacedemo.questions;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import shared.enums.PlayerID;
import shared.interfaces.PlayerRepresentative;

public class WhichPlayer extends StackPane implements PlayerQuestion<PlayerID> {
	
	// We'll need to notify this later
	private GUIRep asker;
	
	// Menu
	private VBox questionnaire = new VBox(10);
	private HBox optionHolder = new HBox(10);
	
	// Options
	private Label playerQuestion = new Label();
	private PlayerID[] playerOptions;
	private int playerSelection;
	
	public WhichPlayer() {
		
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
	public void setOptions(PlayerID[] options) {
		playerOptions = options;
		setupButtons();
	}

	@Override
	public void reset() {
		playerOptions = null;
		resetAnswer();
	}

	@Override
	public void setRepresentative(PlayerRepresentative rep) {
		asker = (GUIRep) rep;
	}

	@Override
	public void show() {
		this.setVisible(true);
	}

	@Override
	public void hide() {
		this.setVisible(false);
	}

	@Override
	public void toggleVisibility() {
		this.setVisible(!this.isVisible());
	}

	@Override
	public void resetAnswer() {
		playerSelection = -1;
	}

	@Override
	public PlayerID getAnswer() {
		return playerOptions[playerSelection];
	}
	
	public int getAnswerIndex() {
		return playerSelection;
	}
	
	private synchronized void setupButtons() {
		optionHolder.getChildren().clear();
		
		// For each option,
		int numOptions = playerOptions.length; 
		for (int i = 0; i < numOptions; i++) {
			final int choice = i;
			
			// Create a button
			final Button option = new Button(playerOptions[i].toString());
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
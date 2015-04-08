package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * 
 * @author Shane Caldwell
 * Will create the buttons, which will be able to be pressed by a player in order to be used.
 *
 */
public class GameControls{
	
		
	GameControls(final Game game){
		getStyleClass().add("game-controls");
		
		//So these labels kick in when a game ends and not before. 
		//visibleProperty().bind(game.gameOverProperty());
		
		Label playAgainLabel = new Label("Play Again?");
		playAgainLabel.getStyleClass().add("info");
		
		Button playAgainButton = new Button("Yes!");
		playAgainButton.getStyleClass().add("play-again");
		playAgainButton.setDefaultButton(true);
		playAgainButton.setOnAction(new EventHandler<ActionEvent>(){
			@Override public void handle (ActionEvent actionEvent){
				//Method here to create new game.
	}
});

  Button exitButton = new Button("No");
  playAgainButton.getStyleClass().add("exit");
  exitButton.setCancelButton(true);
	exitButton.setOnAction(new EventHandler<ActionEvent>(){
		@Override
		public void handle(ActionEvent actionEvent){
			//Method here to exit application and close whole thing
		}
	});
	
	getChildren().setAll(playAgainLabel,PlayAgainButton,exitButton);
	}
	}


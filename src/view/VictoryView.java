package view;

import Main.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import shared.enums.PlayerID;
import view.interfaces.ControlledScreen;
import model.Match;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import controller.ScreenSwitcher;

public class VictoryView implements ControlledScreen {

    Group mainGroup;
    Match match;
    ScreenSwitcher myController;
    String nextScreenName;
    
    public VictoryView(String nextScreenName) {
        this.nextScreenName = nextScreenName;
        mainGroup = new Group();
    }
    
    /**
     * Can do more with info from match object in the future.
     * Right now just displays the winner's name.
     * @param m Match object from match that just ended.
     */
    public void loadMatch(Match m) {
        match = m;
        
        // Create our grid to place forms.
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // Basic text showing the winner.
        Label victoryText = new Label();
        PlayerID winner = match.whoWon();
        
        if (winner == PlayerID.NOPLAYER) {
            victoryText.setText("DRAW!");
        } else {
            victoryText.setText(winner.toString() + " won!");
        }
        grid.add(victoryText, 0, 0);
        
        Label playAgain = new Label();
        playAgain.setText("Play Again?");
        grid.add(playAgain, 0, 2);
        
        Button playAgainBtn = new Button();
        playAgainBtn.setText("Yes");
        playAgainBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (myController != null) {
                    
                    // Take the user back to the menu screen.
                    myController.setActiveScreen(nextScreenName);
                    
                    // Now clear whatever victory screen stuff we had,
                    // to free up memory? maybe?
                    mainGroup.getChildren().clear();
                }
            }
        });
//        HBox hbBtn = new HBox(10);
//        //hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(playAgainBtn);
        grid.add(playAgainBtn, 0, 3);

        Button quitBtn = new Button();
        quitBtn.setText("No");
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // End the application
                Platform.exit();
            }
        });
        grid.add(quitBtn, 1, 3);
        
        mainGroup.getChildren().add(grid);
    }
    
    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        // TODO Auto-generated method stub
        myController = scSw;
    }

    @Override
    public Parent getRoot() {
        // TODO Auto-generated method stub
        return mainGroup;
    }

    
}

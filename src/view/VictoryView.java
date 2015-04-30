package view;

import Main.Main;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import shared.enums.PlayerID;
import model.Match;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
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
    
    public void loadMatch(Match m) {
        match = m;
        // TODO: set up the view based on who won.
        
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
        
        Button playAgainBtn = new Button();
        playAgainBtn.setText("Play Again?");
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
        HBox hbBtn = new HBox(10);
        //hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(playAgainBtn);
        grid.add(hbBtn, 0, 2);

        mainGroup.getChildren().add(grid);
    }
    
    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        // TODO Auto-generated method stub
        myController = scSw;
    }

    @Override
    public Group getMainGroup() {
        // TODO Auto-generated method stub
        return mainGroup;
    }

    
}
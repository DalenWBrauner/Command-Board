package view;

import view.interfaces.ControlledScreen;
import model.Match;
import controller.ScreenSwitcher;
import Main.Main;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class MenuScreenView implements ControlledScreen {

    Group mainGroup;
    ScreenSwitcher myController;
    
    /*public static final Image BACKGROUND_IMAGE = 
            new Image(MenuScreenView.class.getResource(
                    "/images/Menu Screen Background.jpg").toString());*/

    public MenuScreenView(MatchView gameScreen, VictoryView victoryScreen) {
        mainGroup = new Group();
        
        // Our background image.
        //ImageView backGround = new ImageView(BACKGROUND_IMAGE);
        //mainGroup.getChildren().add(backGround);

        // Create our grid to place forms.
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        // A little text label at the top.
        Text scenetitle = new Text("C-C-C-Command Board!");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);
        
        // Make the form for the user to select the number
        // of players.
        
        // First the label.
        Label numPlayerLabel = new Label();
        numPlayerLabel.setText("Number of players");
        grid.add(numPlayerLabel, 0, 1);
        
        // Then the drop-down choicebox.
        ObservableList numPlayerChoices = FXCollections.
                observableArrayList();
        for (int i=2; i <= Main.MAX_NUMBER_OF_PLAYERS; i++) {
            numPlayerChoices.add(i);
        }
        ChoiceBox numPlayerCB = new ChoiceBox(numPlayerChoices);
        numPlayerCB.setTooltip(new Tooltip("Boop!"));
        
        // Set the first choice to be selected by default
        // (two players).
        numPlayerCB.getSelectionModel().select(0);
        
        /*
        // Handle when the choice is changed.
        numPlayerCB.getSelectionModel().selectedIndexProperty().
        addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> 
            observableValue, Number number, Number number2) {
                System.out.println(numPlayerCB.getItems().get((Integer) number2));
            }
        }); */
        
        grid.add(numPlayerCB, 1, 1);
        
        
        // Make the form for the user to select the cash goal.
        
        // First the label.
        Label cashGoalLabel = new Label();
        cashGoalLabel.setText("Cash Goal");
        grid.add(cashGoalLabel, 0, 2);
        
        // Then the drop-down choicebox.
        ObservableList cashGoalChoices = FXCollections.
                observableArrayList();
        cashGoalChoices.addAll(Main.CASH_GOAL_OPTIONS.keySet());
        ChoiceBox cashGoalCB = new ChoiceBox(cashGoalChoices);
        cashGoalCB.setTooltip(new Tooltip("Boop2!"));
        
        // Set the second choice to be selected by default.
        // Right now, since we only have 3 choices, this will
        // choose the middle option (medium cash goal).
        cashGoalCB.getSelectionModel().select(1);
        
        grid.add(cashGoalCB, 1, 2);
        
        
        // Make the form for the user to select the board.
        
        // First the label.
        Label boardLabel = new Label();
        boardLabel.setText("Board");
        grid.add(boardLabel, 0, 3);
        
        // Then the drop-down choicebox. Right now there
        // is only one board option.
        ObservableList boardChoices = FXCollections.
                observableArrayList();
        boardChoices.addAll(Main.PLAYABLE_BOARDS);
        ChoiceBox boardCB = new ChoiceBox(boardChoices);
        boardCB.setTooltip(new Tooltip("Boop3!"));
        
        // Set the first choice to be selected by default.
        boardCB.getSelectionModel().select(0);
        
        grid.add(boardCB, 1, 3);
        
        // Now create the button to press, to submit all
        // of these options, generate the Match, and move
        // to the scene which will let the players play
        // the match.
        Button playBtn = new Button();
        playBtn.setText("Start!");
        playBtn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (myController != null) {
                    int numberOfPlayers = (int)numPlayerCB.getValue();
                    int cashGoal = Main.CASH_GOAL_OPTIONS.get((String)cashGoalCB.
                                                getValue());
                    String whichBoard = (String)boardCB.getValue();

                    // Create the match object with our parameters.
                    Match theMatch = Main.theMatchFactory.createMatch(numberOfPlayers, cashGoal, whichBoard);
                    
                    // Pass our match object so that our game screen can load
                    gameScreen.loadMatch(theMatch);
     
                    myController.setActiveScreen(Main.GAME_SCREEN);
                    theMatch.start();
             
                    // Show the victory screen, showing who won.
//                    victoryScreen.loadMatch(theMatch);
//                    myController.setActiveScreen(Main.VICTORY_SCREEN);
                }
            }
        });
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(playBtn);
        grid.add(hbBtn, 1, 4);

        mainGroup.getChildren().add(grid);

    }

    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    /**
     * Returns the Group this map belongs to.
     * 
     * @return Group
     */
    public Parent getRoot() {
        return mainGroup;
    }
    
    
}

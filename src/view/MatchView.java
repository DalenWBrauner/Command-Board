package view;

import java.util.Random;

import shared.enums.CardinalDirection;
import model.Match;
import javafx.scene.Group;
import javafx.scene.control.Label;
import controller.ScreenSwitcher;

public class MatchView implements ControlledScreen {

    Group mainGroup;
    ScreenSwitcher myController;
    Match match;
    
    public MatchView() {
        mainGroup = new Group();
        // TODO: Create scrollpane with grid of tiles
        // within.
        
        // TODO: Create joystick UI.
        //Joystick joystick = new Joystick();
        //mainGroup.getChildren().add(joystick.getMainGroup());
    }
    
    public void loadMatch(Match m) {
        match = m;
        // A temporary label.
        Label temp = new Label();
        temp.setText("LOOK AT THEM GRAPHICS!");
        mainGroup.getChildren().add(temp);
        // ... do setup stuff (activate tiles)
    }

    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    @Override
    public Group getMainGroup() {
        return mainGroup;
    }
    
    //Skeleton functions to fill in for Dalen
    
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections){
    	return availableDirections[0];
    }
    
    //Random roll between 1 and 6
    public int getUsersRoll(){
    	Random rand = new Random();
    	return rand.nextInt(6) + 1;
    }
    
    //Loads joystick
    public Group getJoystick(){
    	Group joystick = new Group();
    	return joystick;
    }
    
    
    
}

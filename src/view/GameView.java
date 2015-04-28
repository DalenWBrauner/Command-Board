package view;

import model.Match;
import javafx.scene.Group;
import controller.ScreenSwitcher;

public class GameView implements ControlledScreen {

    Group mainGroup;
    ScreenSwitcher myController;
    Match match;
    
    public GameView() {
        mainGroup = new Group();
        // TODO: Create scrollpane with grid of tiles
        // within.
        
        // TODO: Create joystick UI.
    }
    
    public void loadMatch(Match m) {
        match = m;
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
    
    
}

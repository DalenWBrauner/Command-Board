package view;

import controller.ScreenSwitcher;
import javafx.scene.Group;

public interface ControlledScreen {

    //This method will allow the injection of the Parent ScreenPane 
    public void setScreenParent(ScreenSwitcher scSw);
    
    public Group getMainGroup();
}

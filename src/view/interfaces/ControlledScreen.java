package view.interfaces;

import controller.ScreenSwitcher;
import javafx.scene.Parent;

public interface ControlledScreen {

    //This method will allow the injection of the Parent ScreenPane 
    public void setScreenParent(ScreenSwitcher scSw);
    
    public Parent getRoot();
}

package view.interfaces;

import javafx.scene.Parent;
import controller.ScreenSwitcher;

public interface ControlledScreen {

    /** Allows the injection of the Parent ScreenPane */
    public void setScreenParent(ScreenSwitcher scSw);

    public Parent getRoot();
}

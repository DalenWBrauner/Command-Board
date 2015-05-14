package controller;

import java.util.HashMap;

import view.interfaces.ControlledScreen;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScreenSwitcher extends StackPane {

    private HashMap<String, Node> registeredScreens = new HashMap();
    private double stageWidth;
    private double stageHeight;
    
    public ScreenSwitcher(double stageWidth, double stageHeight) {
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
    }
    
    public double getStageWidth() {
        return stageWidth;
    }
    public double getStageHeight() {
        return stageHeight;
    }
    
    public void registerScreen(String screenName, ControlledScreen screen) {
        registeredScreens.put(screenName, screen.getRoot());
        screen.setScreenParent(this);
    }
    
    public void unregisterScreen(String screenName) {
        registeredScreens.remove(screenName);
    }
    
    public void setActiveScreen(String screenName) {
        Node screenToSet = registeredScreens.get(screenName);
        if (screenToSet != null) {
            final DoubleProperty opacity = opacityProperty(); 

            // Is there is currently a screen already.
            if(!getChildren().isEmpty()){
                Timeline fade = new Timeline( 
                        new KeyFrame(Duration.ZERO, 
                                new KeyValue(opacity,1.0)), 
                        new KeyFrame(new Duration(1000), 

                                new EventHandler<ActionEvent>() { 

                                @Override 
                                public void handle(ActionEvent t) { 
                                    //remove displayed screen 
                                    getChildren().remove(0); 
                                    //add new screen 
                                    getChildren().add(0, screenToSet); 
                                    Timeline fadeIn = new Timeline( 
                                            new KeyFrame(Duration.ZERO, 
                                                    new KeyValue(opacity, 0.0)), 
                                            new KeyFrame(new Duration(800), 
                                                    new KeyValue(opacity, 1.0))); 
                                    fadeIn.play(); 
                                } 
                        }, new KeyValue(opacity, 0.0))); 
              fade.play(); 
            } else { 
              // no screen has been displayed, then just show 
              setOpacity(0.0); 
              getChildren().add(screenToSet); 
              Timeline fadeIn = new Timeline( 
                  new KeyFrame(Duration.ZERO, 
                               new KeyValue(opacity, 0.0)), 
                  new KeyFrame(new Duration(2500), 
                               new KeyValue(opacity, 1.0))); 
              fadeIn.play(); 
            }
        }
    }
    
    
}

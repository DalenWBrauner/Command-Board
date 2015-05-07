package controller;

import view.TileView;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public class TileEventHandler implements EventHandler<MouseEvent> {

    @Override
    public void handle(MouseEvent me) {
        TileView currentClickedSquare = (TileView) me.getSource();
        //TODO: Display financial info.
//        int currentxPos = currentClickedSquare.getxPos();
//        int currentyPos = currentClickedSquare.getyPos();
    }

    
}

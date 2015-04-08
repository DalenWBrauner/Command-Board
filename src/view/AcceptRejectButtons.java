package view;

import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.*;
import javafx.event.*;

public class AcceptRejectButtons {
	
	final Button acceptButton = new Button("Select");
	final Button rejectButton = new Button("Cancel");
	
	DropShadow shadow = new DropShadow();
	//Add shadow when cursor is selected
	acceptButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
			new EventHandler<MouseEvent>(){
		@Override public void handle(MouseEvent e){
			acceptButton.setEffect(shadow);
		}
	});
	
	//Remove shadow when mouse cursor is off
	acceptButton.addEventHandler(MouseEvent.MOUSE_EXITED,
			new EventHandler<MouseEvent>(){
		@Override public void handle(MouseEvent e){
			acceptButton.setEffect(null);
		}
	});
			
	

}

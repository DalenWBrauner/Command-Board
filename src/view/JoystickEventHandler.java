package view;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

public class JoystickEventHandler implements EventHandler<MouseEvent> {

	boolean needDirection = false; 
	boolean needSpell = false;
	
	public void handle(MouseEvent me) {
		System.out.println("You clicked a thing!");
				
	}

}

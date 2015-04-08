package view;

import javafx.event.EventHandler;

/**
 * 
 * @author Shane Caldwell
 * This will create the buttons that the users uses to move left, right, up or down
 *
 */
public interface ArrowButtonInterface {
	
	
	public static final int RIGHT = 1;
	public static final int LEFT  = 2;
	public static final int UP    = 3;
	public static final int DOWN  = 4;
	
	//We may want text on the button?
	public void setText(String text);
	
	//Certainly want our button to do something!
	@SuppressWarnings("rawtypes")
	public void setOnMouseClicked(EventHandler eh);
	
	//Up, down, left, or right
	public void setDirection();

	void setDirection(int direction);

}

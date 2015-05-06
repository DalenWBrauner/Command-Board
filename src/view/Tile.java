package view;

import java.awt.Color;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

class Tile{
	enum State { RED, BLUE, YELLOW, GREEN, NEUTRAL}
	
	//We need to create a label that will hold the information we desire and can be easily updated
	private final Label tileLabel = new Label("\nCost to Step On: \nCost to Buy");
	
	public Rectangle tile;
	public int xcor;
	public int ycor;

	
		
	public Tile(int xcor, int ycor){
		tile = new Rectangle(10 * xcor, 10 * ycor, 50, 50);
		this.xcor = xcor;
		this.ycor = ycor;
	}
	
	public Rectangle getTile(){
		if (tile == null){
			System.out.println("What?");
		}
		return tile;

	}
	
	public int relativeX(){
		return xcor;
	}
	
	public int relativeY(){
		return ycor;
	}
	
	public int absoluteX(){
		return xcor * 10;
	}
	
	public int absoluteY(){
		return ycor * 10;
	}
	
	public void setColor(String color){
		Paint col = Paint.valueOf(color);
		tile.setFill(col);
	}
	

    public void pressed() {
    	//Decide if clicking a tile brings up info on it or some such
    }
	
}

	
	
	
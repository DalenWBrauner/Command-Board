package view;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Node;
import javafx.scene.control.Label;

class Tile{
	enum State { RED, BLUE, YELLOW, GREEN, NEUTRAL}
	
	//We need to create a label that will hold the information we desire and can be easily updated
	private final Label tileLabel = new Label("\nCost to Step On: \nCost to Buy");
	
	

	
	private final TileSkin skin;
	
	private ReadOnlyObjectWrapper<State> state = new ReadOnlyObjectWrapper<>(State.NEUTRAL);
		
	private final Game game;
	
	public Tile(Game game){
		this.game = game;
		
		skin = new TileSkin(this);
	}
	

    public void pressed() {
    	//Decide if clicking a tile brings up info on it or some such
    }
	
	public Node getSkin(){
		return skin;
	}
}

	
	
	
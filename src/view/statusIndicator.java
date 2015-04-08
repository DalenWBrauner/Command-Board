package view;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;


/**
 * 
 * @author Shane Caldwell
 * Goal of this class is to show off the status of a given player which will be displayed, and then changed out for each player. 
 * i.e. If it's player ones turn it shows their net-value/wallet etc. 
 *
 */
class statusIndicator extends HBox{
	
	private final ImageView playerToken = new ImageView();
	//Must get net value from model
	private final Label playerLabel = new Label("Current Player : \nIn wallet: \nNet Value");
	
	public statusIndicator(Game game){
		getStyleClass().add("status-indicator");
		getChildren().addAll(playerLabel, playerToken);
}


					
					
   }
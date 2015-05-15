package view;

import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Match;
import model.Player;

public class checkpointView {
	
	Match myMatch;
	Label title;
	Label checkpoint1;
	Label checkpoint2;
	Label checkpoint3;
	Label checkpoint4;
	
	Rectangle red;
	Rectangle blue;
	Rectangle green;
	Rectangle yellow;
	
	Label x;
	
	Group mainGroup;
	
	public checkpointView(Match m){
		myMatch = m;
		initCheckpointView();
	}
	
	public void initCheckpointView(){
		Rectangle passed = new Rectangle();
		passed.setWidth(10);
		passed.setHeight(10);
		passed.setFill(Color.BLACK);
		
		title = new Label("Checkpoints:");
		
	    red = new Rectangle();
		red.setWidth(10);
		red.setHeight(10);
		red.setFill(Color.RED);
		
		blue = new Rectangle();
		blue.setWidth(10);
		blue.setHeight(10);
		blue.setFill(Color.BLUE);
		
		green = new Rectangle();
		green.setWidth(10);
		green.setHeight(10);
		green.setFill(Color.GREEN);
		
		yellow = new Rectangle();
		yellow.setWidth(10);
		yellow.setHeight(10);
		yellow.setFill(Color.YELLOW);
		
		HBox checkpoints = new HBox();
		checkpoints.setSpacing(20);
		checkpoints.getChildren().addAll(red, blue, green, yellow);
		
		VBox stack = new VBox();
		stack.setSpacing(15);
		stack.getChildren().addAll(title, checkpoints);
		
		mainGroup = new Group(stack);
		
	}
	
	public Group getMainGroup(){
		return mainGroup;
	}
	
	public void redraw(){
		System.out.println("Called");
		PlayerID currentPlayerID = myMatch.getCurrentPlayerID();
		Player currentPlayer = myMatch.getPlayer(currentPlayerID);
		
		if(currentPlayer.hasPassed(CheckpointColor.RED)){
			red.setFill(Color.BLACK);
		}else{
			red.setFill(Color.RED);
		}
		
		if(currentPlayer.hasPassed(CheckpointColor.BLU)){
			blue.setFill(Color.BLACK);
		}else{
			blue.setFill(Color.BLUE);
		}
		
		if(currentPlayer.hasPassed(CheckpointColor.GRN)){
			green.setFill(Color.BLACK);
		}else{
			green.setFill(Color.GREEN);
		}
		
		if(currentPlayer.hasPassed(CheckpointColor.YLW)){
			yellow.setFill(Color.BLACK);
		}else{
			yellow.setFill(Color.YELLOW);
		}
		
	}
	
	

}

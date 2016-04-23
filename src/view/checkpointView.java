package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Match;
import model.Player;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;

public class checkpointView {

	Match myMatch;
	Label title1;
	Label title2;
	Label title3;
	Label title4;

	Rectangle red1;
	Rectangle red2;
	Rectangle red3;
	Rectangle red4;

	Rectangle blue1;
	Rectangle blue2;
	Rectangle blue3;
	Rectangle blue4;

	Rectangle green1;
	Rectangle green2;
	Rectangle green3;
	Rectangle green4;

	Rectangle yellow1;
	Rectangle yellow2;
	Rectangle yellow3;
	Rectangle yellow4;

	int numPlayers;

	Group mainGroup;

	public checkpointView(Match m){
		myMatch = m;
		initCheckpointView();
	}

	public void initCheckpointView(){
		numPlayers = myMatch.getTurnOrder().size();

		title1 = new Label("Player1");
		title2 = new Label("Player2");
		title3 = new Label("Player3");
		title4 = new Label("Player4");

	    red1 = new Rectangle();
		red1.setWidth(10);
		red1.setHeight(10);
		red1.setFill(Color.BLACK);

	    red2 = new Rectangle();
		red2.setWidth(10);
		red2.setHeight(10);
		red2.setFill(Color.BLACK);

	    red3 = new Rectangle();
		red3.setWidth(10);
		red3.setHeight(10);
		red3.setFill(Color.BLACK);

	    red4 = new Rectangle();
		red4.setWidth(10);
		red4.setHeight(10);
		red4.setFill(Color.BLACK);


		blue1 = new Rectangle();
		blue1.setWidth(10);
		blue1.setHeight(10);
		blue1.setFill(Color.BLACK);

		blue2 = new Rectangle();
		blue2.setWidth(10);
		blue2.setHeight(10);
		blue2.setFill(Color.BLACK);

		blue3 = new Rectangle();
		blue3.setWidth(10);
		blue3.setHeight(10);
		blue3.setFill(Color.BLACK);

		blue4 = new Rectangle();
		blue4.setWidth(10);
		blue4.setHeight(10);
		blue4.setFill(Color.BLACK);

		green1 = new Rectangle();
		green1.setWidth(10);
		green1.setHeight(10);
		green1.setFill(Color.BLACK);

		green2 = new Rectangle();
		green2.setWidth(10);
		green2.setHeight(10);
		green2.setFill(Color.BLACK);

		green3 = new Rectangle();
		green3.setWidth(10);
		green3.setHeight(10);
		green3.setFill(Color.BLACK);

		green4 = new Rectangle();
		green4.setWidth(10);
		green4.setHeight(10);
		green4.setFill(Color.BLACK);

		yellow1 = new Rectangle();
		yellow1.setWidth(10);
		yellow1.setHeight(10);
		yellow1.setFill(Color.BLACK);

		yellow2 = new Rectangle();
		yellow2.setWidth(10);
		yellow2.setHeight(10);
		yellow2.setFill(Color.BLACK);

		yellow3 = new Rectangle();
		yellow3.setWidth(10);
		yellow3.setHeight(10);
		yellow3.setFill(Color.BLACK);

		yellow4 = new Rectangle();
		yellow4.setWidth(10);
		yellow4.setHeight(10);
		yellow4.setFill(Color.BLACK);

		HBox checkpoints1 = new HBox();
		checkpoints1.setSpacing(20);
		checkpoints1.getChildren().addAll(red1, blue1, green1, yellow1);

		HBox checkpoints2 = new HBox();
		checkpoints2.setSpacing(20);
		checkpoints2.getChildren().addAll(red2, blue2, green2, yellow2);

		HBox checkpoints3 = new HBox();
		checkpoints3.setSpacing(20);
		checkpoints3.getChildren().addAll(red3, blue3, green3, yellow3);

		HBox checkpoints4 = new HBox();
		checkpoints4.setSpacing(20);
		checkpoints4.getChildren().addAll(red4, blue4, green4, yellow4);

		VBox stack1 = new VBox();
		stack1.setSpacing(15);
		stack1.getChildren().addAll(title1, checkpoints1);

		VBox stack2 = new VBox();
		stack2.setSpacing(15);
		stack2.getChildren().addAll(title2, checkpoints2);

		VBox stack3 = new VBox();
		stack3.setSpacing(15);
		stack3.getChildren().addAll(title3, checkpoints3);

		VBox stack4 = new VBox();
		stack4.setSpacing(15);
		stack4.getChildren().addAll(title4, checkpoints4);

		if (numPlayers == 4) {
    		HBox endBox = new HBox();
    		endBox.setSpacing(40);
    		endBox.getChildren().addAll(stack1, stack2, stack3, stack4);
    		mainGroup = new Group(endBox);
		}

		if (numPlayers == 3){
			HBox endBox = new HBox();
			endBox.setSpacing(40);
			endBox.getChildren().addAll(stack1, stack2, stack3);
			mainGroup = new Group(endBox);
		}

		if (numPlayers == 2){
			HBox endBox = new HBox();
			endBox.setSpacing(40);
			endBox.getChildren().addAll(stack1, stack2);
			mainGroup = new Group(endBox);
		}
	}

	public Group getMainGroup(){
		return mainGroup;
	}

	public void redraw(){

		//How many players must we redraw?
		Player player1 = myMatch.getPlayer(PlayerID.PLAYER1);

		if (player1.hasPassed(CheckpointColor.RED)) {
			red1.setFill(Color.RED);
		} else {
			red1.setFill(Color.BLACK);
		}

		if (player1.hasPassed(CheckpointColor.BLU)) {
			blue1.setFill(Color.BLUE);
		} else {
			blue1.setFill(Color.BLACK);
		}

		if (player1.hasPassed(CheckpointColor.GRN)) {
			green1.setFill(Color.GREEN);
		} else {
			green1.setFill(Color.BLACK);
		}

		if (player1.hasPassed(CheckpointColor.YLW)) {
			yellow1.setFill(Color.YELLOW);
		} else {
			yellow1.setFill(Color.BLACK);
		}

		Player player2 = myMatch.getPlayer(PlayerID.PLAYER2);

		if (player2.hasPassed(CheckpointColor.RED)) {
			red2.setFill(Color.RED);
		} else {
			red2.setFill(Color.BLACK);
		}

		if (player2.hasPassed(CheckpointColor.BLU)) {
			blue2.setFill(Color.BLUE);
		} else {
			blue2.setFill(Color.BLACK);
		}

		if (player2.hasPassed(CheckpointColor.GRN)) {
			green2.setFill(Color.GREEN);
		} else {
			green2.setFill(Color.BLACK);
		}

		if (player2.hasPassed(CheckpointColor.YLW)) {
			yellow2.setFill(Color.YELLOW);
		} else {
			yellow2.setFill(Color.BLACK);
		}

		if (numPlayers > 2) {
			Player player3 = myMatch.getPlayer(PlayerID.PLAYER3);

			if (player3.hasPassed(CheckpointColor.RED)) {
				red3.setFill(Color.RED);
			} else {
				red3.setFill(Color.BLACK);
			}

			if (player3.hasPassed(CheckpointColor.BLU)) {
				blue3.setFill(Color.BLUE);
			} else {
				blue3.setFill(Color.BLACK);
			}

			if (player3.hasPassed(CheckpointColor.GRN)) {
				green3.setFill(Color.GREEN);
			} else {
				green3.setFill(Color.BLACK);
			}

			if (player3.hasPassed(CheckpointColor.YLW)) {
				yellow3.setFill(Color.YELLOW);
			} else {
				yellow3.setFill(Color.BLACK);
			}
		}

		if (numPlayers > 3) {
			Player player4 = myMatch.getPlayer(PlayerID.PLAYER4);

			if (player4.hasPassed(CheckpointColor.RED)) {
				red4.setFill(Color.RED);
			} else {
				red4.setFill(Color.BLACK);
			}

			if (player4.hasPassed(CheckpointColor.BLU)) {
				blue4.setFill(Color.BLUE);
			} else {
				blue4.setFill(Color.BLACK);
			}

			if (player4.hasPassed(CheckpointColor.GRN)) {
				green4.setFill(Color.GREEN);
			} else {
				green4.setFill(Color.BLACK);
			}

			if (player4.hasPassed(CheckpointColor.YLW)) {
				yellow4.setFill(Color.YELLOW);
			} else {
				yellow4.setFill(Color.BLACK);
			}
		}
	}
}

package view;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Match;
import model.Wallet;
import shared.enums.PlayerID;

public class walletView {

    private static Color P1COLOR = Color.RED;
    private static Color P2COLOR = Color.PURPLE;
    private static Color P3COLOR = Color.GREEN;
    private static Color P4COLOR = Color.BROWN;

	Label P1money;
	Label P1netVal;

	Label P2money;
	Label P2netVal;

	Label P3money;
	Label P3netVal;

	Label P4money;
	Label P4netVal;

	VBox P1box = new VBox();
	VBox P2box = new VBox();
	VBox P3box = new VBox();
	VBox P4box = new VBox();

	Group wallGroup;

	Match m;

	int numPlays;

	public walletView(Match m) {

	    // Store our model Match object.
    	this.m = m;

    	// Get how many players are playing.
    	numPlays = m.getAllPlayers().size();

    	if (numPlays == 4){ //You have four players

    		P1money = new Label("P1 ON HAND: $0             ");
    		P1money.setTextFill(P1COLOR);
    		P1netVal = new Label("P1 NET VALUE: $0        ");
    		P1netVal.setTextFill(P1COLOR);

    		P2money = new Label("P2 ON HAND: $0              ");
    		P2money.setTextFill(P2COLOR);
    		P2netVal = new Label("P2 NET VALUE: $0           ");
    		P2netVal.setTextFill(P2COLOR);

    		P3money = new Label("P3 ON HAND: $0 ");
    		P3money.setTextFill(P3COLOR);
    		P3netVal = new Label("P3 NET VALUE: $0 ");
    		P3netVal.setTextFill(P3COLOR);

    		P4money = new Label("P4 ON HAND: $0 ");
    		P4money.setTextFill(P4COLOR);
    		P4netVal = new Label("P4 NET VALUE: $0 ");
    		P4netVal.setTextFill(P4COLOR);

    		P4box.setSpacing(20);
    		P4box.getChildren().addAll(P4money, P4netVal);

    		P3box.setSpacing(20);
    		P3box.getChildren().addAll(P3money, P3netVal);

    		P2box.setSpacing(20);
    		P2box.getChildren().addAll(P2money, P2netVal);

    		P1box.setSpacing(20);
    		P1box.getChildren().addAll(P1money, P1netVal);

    		VBox comBox = new VBox();
    		comBox.setSpacing(20);
    		comBox.getChildren().addAll(P1box, P3box);

    		VBox comBox2 = new VBox();
    		comBox2.setSpacing(20);
    		comBox2.getChildren().addAll(P2box, P4box);

    		HBox finalBox = new HBox();
    		finalBox.setSpacing(100);
    		finalBox.getChildren().addAll(comBox, comBox2);
    		wallGroup = new Group(finalBox);

    	} else if (numPlays == 3) { //You have three players

    		P1money = new Label("P1 ON HAND: $0             ");
    		P1netVal = new Label("P1 NET VALUE: $0          ");
    		P1money.setTextFill(P1COLOR);
    		P1netVal.setTextFill(P1COLOR);

    		P2money = new Label("P2 ON HAND: $0             ");
    		P2netVal = new Label("P2 NET VALUE: $0           ");
    		P2money.setTextFill(P2COLOR);
    		P2netVal.setTextFill(P2COLOR);

    		P3money = new Label("P3 ON HAND: $0");
    		P3netVal = new Label("P3 NET VALUE: $0");
    		P3money.setTextFill(P3COLOR);
    		P3netVal.setTextFill(P3COLOR);

    		P3box.setSpacing(20);
    		P3box.getChildren().addAll(P3money, P3netVal);

    		P2box.setSpacing(20);
    		P2box.getChildren().addAll(P2money, P2netVal);

    		P1box.setSpacing(20);
    		P1box.getChildren().addAll(P1money, P1netVal);

    		VBox comBox = new VBox();
    		comBox.setSpacing(20);
    		comBox.getChildren().addAll(P1box, P3box);

    		VBox comBox2 = new VBox();
    		comBox2.setSpacing(20);
    		comBox2.getChildren().add(P2box);

    		HBox finalBox = new HBox();
    		finalBox.setSpacing(100);
    		finalBox.getChildren().addAll(comBox, comBox2);

    		wallGroup = new Group(finalBox);

    	} else { //You have two players

    		P1money = new Label("P1 ON HAND: $0              ");
    		P1netVal = new Label("P1 NET VALUE: $0           ");
    		P1money.setTextFill(P1COLOR);
    		P1netVal.setTextFill(P1COLOR);

    		P2money = new Label("P2 ON HAND: $0              ");
    		P2netVal = new Label("P2 NET VALUE: $0           ");
    		P2money.setTextFill(P2COLOR);
    		P2netVal.setTextFill(P2COLOR);

    		P2box.setSpacing(20);
    		P2box.getChildren().addAll(P2money, P2netVal);

    		P1box.setSpacing(20);
    		P1box.getChildren().addAll(P1money, P1netVal);

    		HBox box = new HBox();
    		box.setSpacing(40);
    		box.getChildren().addAll(P1box, P2box);

    		wallGroup = new Group(box);
    	}
	}

	void update(){
	    PlayerID[] pIDs = PlayerID.getNPlayers(numPlays);
	    Label[][] labels = new Label[][] { new Label[] {P1money, P1netVal},
	                                    new Label[] {P2money, P2netVal},
	                                    new Label[] {P3money, P3netVal},
	                                    new Label[] {P4money, P4netVal} };

	    for (int i=0; i < pIDs.length; i++) {
	        PlayerID pID = pIDs[i];
	        Wallet w = m.getPlayer(pID).getWallet();
	        Label money = labels[i][0];
	        Label netValue = labels[i][1];

	        String shortName = PlayerID.getShortName(pID);
	        money.setText(String.format("%s ON HAND: $",
	                shortName) + w.getCashOnHand());
	        netValue.setText(String.format("%s NET VALUE: $",
	                shortName) + w.getNetValue());
	    }
	}

	Group getWallGroup() {
	    return wallGroup;
	}
}

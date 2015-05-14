package view;

import java.util.ArrayList;

import shared.enums.PlayerID;
import model.Match;
import model.Player;
import model.Wallet;
import javafx.scene.control.Label;

public class walletView {
	
	Label P1money;
	Label P1netVal;


	Label P2money;
	Label P2netVal;
	
	Label P3money;
	Label P3netVal;
	
	Label P4money;
	Label P4netVal;
	
	Match m;
	
	int numPlays;
	
	walletView(Match m){
		
	this.m = m;	
	ArrayList<Player> players =	m.getAllPlayers();
	int numPlays = players.size();
	
	if(numPlays == 4){
		//You have four players
		P1money = new Label("P1 ON HAND:              ");
		P1netVal = new Label("P1 NET VALUE:           ");

		P2money = new Label("P2 ON HAND:              ");
		P2netVal = new Label("P2 NET VALUE:           ");
		
		P3money = new Label("P3 ON HAND: ");
		P3netVal = new Label("P3 NET VALUE: ");
		
		P4money = new Label("P4 ON HAND: ");
		P4netVal = new Label("P4 NET VALUE");
		
		

		
	}else if(numPlays ==3){
		//You have three players
		P1money = new Label("P1 ON HAND:              ");
		P1netVal = new Label("P1 NET VALUE:           ");

		P2money = new Label("P2 ON HAND:              ");
		P2netVal = new Label("P2 NET VALUE:           ");
		
		P3money = new Label("P3 ON HAND: ");
		P3netVal = new Label("P3 NET VALUE: ");

		
	}else{
		//You have two players
		P1money = new Label("P1 ON HAND:              ");
		P1netVal = new Label("P1 NET VALUE:           ");

		P2money = new Label("P2 ON HAND:              ");
		P2netVal = new Label("P2 NET VALUE:           ");

	}
	
	}
	
	void update(){
		PlayerID id1 = m.getAllPlayerIDs().get(0);
		Wallet P1Wallet = m.getPlayer(id1).getWallet();
		P1money.setText("ON HAND: $" + P1Wallet.getCashOnHand());
		P1netVal.setText("NET VALUE: $" + P1Wallet.getNetValue());
		
		PlayerID id2 = m.getAllPlayerIDs().get(1);
		Wallet P2Wallet = m.getPlayer(id2).getWallet();
		P2money.setText("ON HAND: $" + P2Wallet.getCashOnHand());
		P2netVal.setText("NET VALUE: $" + P2Wallet.getNetValue());
		
		if (numPlays > 2){
			PlayerID id3 = m.getAllPlayerIDs().get(2);
			Wallet P3Wallet = m.getPlayer(id3).getWallet();
			P3money.setText("ON HAND: $" + P3Wallet.getCashOnHand());
			P3netVal.setText("NET VALUE: $" + P3Wallet.getNetValue());
			}
		if (numPlays > 3){
			PlayerID id4 = m.getAllPlayerIDs().get(3);
			Wallet P4Wallet = m.getPlayer(id4).getWallet();
			P4money.setText("ON HAND: $" + P4Wallet.getCashOnHand());
			P4netVal.setText("NET VALUE $:" + P4Wallet.getCashOnHand());
			
		}

		
		

	}
}

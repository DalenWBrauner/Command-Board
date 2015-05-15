package view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import shared.enums.CardShape;

public class cardArtist {
	
	Group card;
	
	CardShape myshape;
	
	Rectangle blankCard;
	
	cardArtist(CardShape shape){
		drawCard();
	}
	
	public void drawCard(){
		
		if (myshape.equals(CardShape.NOCARD)){
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(30);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(20);
			blankCard.setArcWidth(20);
			
			card = new Group(blankCard);
		}
		//Circle
		//Square
		//Triangle
		
		if(myshape.equals(CardShape.SHAPE1)){
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(30);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(20);
			blankCard.setArcWidth(20);
			
			Circle circle = new Circle();
			circle.setRadius(20);
			circle.setFill(Color.BLACK);
			
			
			card = new Group(blankCard, circle);
		}
		
		if(myshape.equals(CardShape.SHAPE2)){
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(30);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(20);
			blankCard.setArcWidth(20);

			Rectangle square = new Rectangle();
			square.setWidth(20);
			square.setHeight(20);
			
			square.setFill(Color.BLANCHEDALMOND);
			
			card = new Group(blankCard, square);
		}
		
		if(myshape.equals(CardShape.SHAPE2)){
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(30);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(20);
			blankCard.setArcWidth(20);
			
			Polygon triangle = new Polygon();
			triangle.getPoints().addAll(new Double[]{
					0.0, 0.0,
					20.0, 10.0,
					10.0, 20.0});
			
			card = new Group(blankCard, triangle);
			}
		}
		
		
	public Group getShape(){
		return card;
	}
	}


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
		myshape = shape;
		drawCard();
	}

	public void drawCard(){

		if (myshape == CardShape.NOCARD) {
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(40);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(10);
			blankCard.setArcWidth(10);

			card = new Group(blankCard);

		} else if (myshape == CardShape.SHAPE1) {
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(40);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(20);
			blankCard.setArcWidth(20);

			Circle circle = new Circle();
			circle.setRadius(20);
			circle.setFill(Color.BLACK);

			card = new Group(blankCard, circle);
		} else if (myshape == CardShape.SHAPE2) {
			blankCard = new Rectangle();
			blankCard.setHeight(80);
			blankCard.setWidth(40);
			blankCard.setFill(Color.GRAY);
			blankCard.setArcHeight(10);
			blankCard.setArcWidth(10);

			Rectangle square = new Rectangle();
			square.setWidth(20);
			square.setHeight(20);
			square.setFill(Color.BLANCHEDALMOND);

			card = new Group(blankCard, square);
		} else if(myshape == CardShape.SHAPE3) {
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
			} else {
				System.out.println("NOT A VALID ENUM");
			}
		}

	public Group getShape(){
		return card;
	}
}

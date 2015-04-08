package view;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Skin;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;
import javafx.scene.shape.VLineTo;

public class ArrowButtonSkin implements Skin<ArrowButton>, ArrowButtonInterface{

	static final double ARROW_TIP_WIDTH = 5;
	
	ArrowButton control;
	String text = "";
	
	Group rootNode = new Group();
	Label lbl = null;
	int direction = ArrowButtonInterface.RIGHT;
	@SuppressWarnings("rawtypes")
	EventHandler clientEH = null;
	
	public ArrowButtonSkin(ArrowButton control){
		this.control = control;
		draw();
	}
	
	public ArrowButton getControl(){
		return control;
	}
	
	public Node getNode(){
		return rootNode;
	}
	
	public void dispose(){
		
	}
	
	public void draw(){
		if (lbl == null){
			lbl = new Label(text);
		}
		double labelWidth = lbl.getBoundsInLocal().getWidth();
		double labelHeight = lbl.getHeight();
		lbl.setTranslateX(2);
		lbl.setTranslateY(2);
		
		//Create arrow button line path elements
		Path path = new Path();
		MoveTo startPoint = new MoveTo();
		double x = 0.0f;
		double y = 0.0f;
		double controlX;
		double controlY;
		double height = labelHeight;
		startPoint.setX(x);
		startPoint.setY(y);
		
		HLineTo topLine = new HLineTo();
		x += labelWidth;
		topLine.setX(x);

		//Top Curve
		controlX = x + ARROW_TIP_WIDTH;
		controlY = y;
		x+= 10;
		y = height / 2;
		QuadCurveTo quadCurveTop = new QuadCurveTo();
		quadCurveTop.setX(x);
		quadCurveTop.setY(y);
		quadCurveTop.setControlX(controlX);
		quadCurveTop.setControlY(controlY);
		
		//Bottom Curve
		controlX = x - ARROW_TIP_WIDTH;
		x -= 10;
		y = height;
		controlY = y;
		QuadCurveTo quadCurveBott = new QuadCurveTo();
		quadCurveBott.setX(x);
		quadCurveBott.setY(y);
		quadCurveBott.setControlX(controlX);
		quadCurveBott.setControlX(controlY);
		
		HLineTo bottomLine = new HLineTo();
		x -= labelWidth;
		bottomLine.setX(x);
		
		VLineTo endLine = new VLineTo();
		endLine.setY(0);
		
		path.getElements().add(startPoint);
		path.getElements().add(topLine);
		path.getElements().add(quadCurveBott);
		path.getElements().add(quadCurveBott);
		path.getElements().add(bottomLine);
		path.getElements().add(endLine);
		
		//Create and set a gradient for the insie of the button
		Stop[] stops = new Stop[]{
				new Stop(0.0, Color.LIGHTGREY),
				new Stop(1.0, Color.SLATEGRAY)
		};
		LinearGradient lg = 
				new LinearGradient(0, 0,0, 1, true, CycleMethod.NO_CYCLE, stops);
		path.setFill(lg);
		
		rootNode.getChildren().setAll(path, lbl);
	}
	
	@Override
	public void setText(String text) {
		this.text = text;
		lbl.setText(text);
		
		//update button
		draw();
	}

	@Override
	public void setOnMouseClicked(EventHandler eh) {
		clientEH = eh;
	}

	@Override
	public void setDirection(int direction) {
		this.direction = direction;
		draw();
	}
	

	@Override
	public ArrowButton getSkinnable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDirection() {
		// TODO Auto-generated method stub
		
	}
	

}

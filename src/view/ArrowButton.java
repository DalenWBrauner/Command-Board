package view;

import javafx.event.EventHandler;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;

/**
 * 
 * TODO:
 * ADD CODE THAT HANDLES LAYOUT
 *
 */

public class ArrowButton extends Control implements ArrowButtonInterface {
	
	private String title = "";
	
	public ArrowButton(){
		this.storeSkin( new ArrowButtonSkin(this));
	}
	
	public ArrowButton(String title){
		this();
		this.title = tile;
		ArrowButtonSkin skin = (ArrowButtonSkin)this.getSkin();
		skin.setText(title);
	}
	
	public void setOnMouseClicked(EventHandler eh){
		getSkin(getSkin()).setOnMouseClicked(eh);
	}
	
	public void setDirection(int direction){
		getSkin(getSkin()).setDirection(direction);
	}
	
	private ArrowButtonSkin getSkin(Skin skin){
		return (ArrowButtonSkin)skin;
	}

}

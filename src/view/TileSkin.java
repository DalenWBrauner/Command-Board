package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;

public class TileSkin extends StackPane{
	/**
	
	5 kinds of squares right now: one neutral image, and 4 (one for each color)

	*/
	
    private final ImageView imageView = new ImageView();

	static final Image neutralImage = new Image(
	TileSkin.class.getResource("").toExternalForm()+ "../../src/packagename/iconname.jpg");
	
	static final Image redImage = new Image(
	TileSkin.class.getResource("").toExternalForm()+"../../src/packagename/iconname.jpg");
	
	static final Image blueImage = new Image(
	TileSkin.class.getResource("").toExternalForm()+ "../../src/packagename/iconname.jpg");
	
	static final Image greenImage = new Image(
	TileSkin.class.getResource("").toExternalForm()+"../../src/packagename/iconname.jpg");

	static final Image yellowImage = new Image(
	TileSkin.class.getResource("").toExternalForm()+"../../src/packagename/iconname.jpg");

	
	
	TileSkin(final Tile tile){
	getStyleClass().add("tile");
	
	imageView.setMouseTransparent(true);
	
	getChildren().setAll(imageView);
	
	setPrefSize(neutralImage.getHeight() + 20, neutralImage.getHeight() + 20);
	
	setOnMousePressed(new EventHandler<MouseEvent>(){
		@Override public void handle(MouseEvent mouseEvent){
			tile.pressed();
		}
	});
	
	tile.stateProperty().addListener(new ChangeListener<Tile.State>(){
		@Override public void changed(ObservableValue<? extends Square.State> observableValue, Tile.State oldState, Tile.State state){
			switch(state){
				case NEUTRAL: imageView.setImage(neutralImage); break;
				case RED:     imageView.setImage(redImage);     break;
				case BLUE:    imageView.setImage(blueImage);    break;
				case: YELLOW: imageView.setImage(yellowImage);  break;
				case: GREEN:  imageView.setImage(greenImage);   break;
				
			}
		}
	});
	
	}
}	
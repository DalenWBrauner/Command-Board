package view;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;

public class Bro extends Application {
	
	final ScrollBar sc = new ScrollBar();
	final VBox vb = new VBox();
	final Rectangle wut = new Tile(1,1).getTile();
	
	public void start(Stage primaryStage) throws Exception {
		
		Group root = new Group();
		
		
		
		Rectangle tile2 = new Rectangle(10, 60, 50, 50);
		tile2.setFill(Color.BLUE);
		tile2.setStroke(Color.TRANSPARENT);
		
		Rectangle tile3 = new Rectangle(10, 110, 50, 50);
		tile3.setFill(Color.WHITE);
		
		Rectangle tile4 = new Rectangle(10000, 110, 50, 50);
		tile4.setFill(Color.AQUAMARINE);
		
		ScrollPane s1 = new ScrollPane();
		s1.setPrefSize(40, 40);
		
		root.getChildren().addAll(wut, tile2, tile3, tile4, s1);
		Scene scene = new Scene(root, 500, 500, Color.BLACK);
		
		primaryStage.setTitle("BRO");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public static void main(String[] args){		
		launch(args);
	}
	
	
	

}

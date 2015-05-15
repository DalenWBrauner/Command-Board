package view;

/**
 * @author Shane Caldwell
 * 
 * Used in order to test the SpellView class before it was placed inside the joystick. 
 */
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class spellViewTest extends Application {
	
	public Scene initStickScene(){
		Joystick joystick = new Joystick();
		Scene scene = new Scene(joystick.getMainGroup(), 800, 200);
		return scene;
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Joystick");
		primaryStage.setScene(initStickScene());
		primaryStage.show();
		
	}
	
	public static void main(String[] args){
		launch(args);
	}

}

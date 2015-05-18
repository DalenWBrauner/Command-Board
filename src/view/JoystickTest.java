package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JoystickTest extends Application {

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

package Main;

//import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.MatchFactory;
import view.MatchView;
import view.MenuScreenView;
import view.VictoryView;
import view.interfaces.ControlledScreen;
import controller.ScreenSwitcher;

public class Main extends Application {

    /** Default settings for Match creation
     * Feel free to change these, they are arbitrary (for now).
     */
    public final static int MAX_NUMBER_OF_PLAYERS = 4;
    private final static int DEFAULT_CASH_GOAL_LOW    = 3000;
    private final static int DEFAULT_CASH_GOAL_MEDIUM = 5000;
    private final static int DEFAULT_CASH_GOAL_HIGH   = 7000;
    public final static Map<String, Integer> CASH_GOAL_OPTIONS = new
            HashMap<String, Integer>();
    public final static String MENU_SCREEN = "menu";
    public final static String GAME_SCREEN = "command board";
    public final static String VICTORY_SCREEN = "victory";
    public final static String[] PLAYABLE_BOARDS = {
        "Rings","Keyblade","Snailshell","Butterfly","Honeypot"};


    //private static Logger logger =  Logger.getLogger(PegSolitaire.class);

    public final static MatchFactory theMatchFactory = new MatchFactory();

    public final static int MAIN_WINDOW_HEIGHT = 600;
    public final static int MAIN_WINDOW_WIDTH = 800;
    public static Stage prim;

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Initializes a new JavaFx-Scene with a game map
     *
     * @return Scene
     */
    public Scene initGameScene(double width, double height){

        // Create our container which has a stack of screens.
        ScreenSwitcher mainContainer = new ScreenSwitcher(width, height);

        // Add our screens to the stack.
        ControlledScreen victoryScreen = new VictoryView(MENU_SCREEN);
        mainContainer.registerScreen(VICTORY_SCREEN, victoryScreen);
        
        ControlledScreen gameMap = new MatchView((VictoryView) victoryScreen);
        mainContainer.registerScreen(GAME_SCREEN, gameMap);

        ControlledScreen menuScreen = new MenuScreenView(
                (MatchView) gameMap);
        mainContainer.registerScreen(MENU_SCREEN, menuScreen);





        // The game is just starting, so let's turn on the Menu screen.
        mainContainer.setActiveScreen(MENU_SCREEN);

        Scene scene = new Scene(mainContainer,
                MAIN_WINDOW_HEIGHT, MAIN_WINDOW_WIDTH);

        return scene;
    }

    /**
     * Anchor for JavaFx to start the application
     *
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
    	prim = primaryStage;
    	
    	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
    	       @Override
    	       public void handle(WindowEvent e) {
    	           // TODO: Make this safer.
    	          MenuScreenView.modelThread.interrupt();
    	          Platform.exit();
    	          System.exit(0);
    	       }
    	    });

        CASH_GOAL_OPTIONS.put(("Low ("    + DEFAULT_CASH_GOAL_LOW    + ")"), DEFAULT_CASH_GOAL_LOW);
        CASH_GOAL_OPTIONS.put(("Medium (" + DEFAULT_CASH_GOAL_MEDIUM + ")"), DEFAULT_CASH_GOAL_MEDIUM);
        CASH_GOAL_OPTIONS.put(("High ("   + DEFAULT_CASH_GOAL_HIGH   + ")"), DEFAULT_CASH_GOAL_HIGH);

        //set Stage boundaries to visible bounds of the main screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());

        double screenWidth = primaryScreenBounds.getWidth();
        double screenHeight = primaryScreenBounds.getHeight();
        primaryStage.setWidth(screenWidth);
        primaryStage.setHeight(screenHeight);

        primaryStage.setTitle("Command Board");
        primaryStage.setScene(initGameScene(screenWidth,screenHeight));
        primaryStage.show();
        //logger.info("JavaFx game started");
    }
}

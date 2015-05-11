package Main;

//import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.MatchFactory;
import view.interfaces.ControlledScreen;
import view.MatchView;
import view.MenuScreenView;
import view.VictoryView;
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
    //public final int STAGE_HEIGHT;
    //public final int STAGE_WIDTH;


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
    public Scene initGameScene(){

        // Create our container which has a stack of screens.
        ScreenSwitcher mainContainer = new ScreenSwitcher();

        // Add our screens to the stack.
        ControlledScreen gameMap = new MatchView();
        mainContainer.registerScreen(GAME_SCREEN, gameMap);

        ControlledScreen victoryScreen = new VictoryView(MENU_SCREEN);
        mainContainer.registerScreen(VICTORY_SCREEN, victoryScreen);

        ControlledScreen menuScreen = new MenuScreenView(
                (MatchView) gameMap, (VictoryView) victoryScreen);
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
        CASH_GOAL_OPTIONS.put(("Low ("    + DEFAULT_CASH_GOAL_LOW    + ")"), DEFAULT_CASH_GOAL_LOW);
        CASH_GOAL_OPTIONS.put(("Medium (" + DEFAULT_CASH_GOAL_MEDIUM + ")"), DEFAULT_CASH_GOAL_MEDIUM);
        CASH_GOAL_OPTIONS.put(("High ("   + DEFAULT_CASH_GOAL_HIGH   + ")"), DEFAULT_CASH_GOAL_HIGH);

        //set Stage boundaries to visible bounds of the main screen
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        primaryStage.setX(primaryScreenBounds.getMinX());
        primaryStage.setY(primaryScreenBounds.getMinY());
        primaryStage.setWidth(primaryScreenBounds.getWidth());
        primaryStage.setHeight(primaryScreenBounds.getHeight());

        primaryStage.setTitle("Command Board");
        primaryStage.setScene(initGameScene());
        primaryStage.show();
        //logger.info("JavaFx game started");
    }
}

package Main;

//import org.apache.log4j.Logger;

import java.util.Map;
import java.util.TreeMap;

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
    public static final int MAX_NUMBER_OF_PLAYERS = 4;
    private static final int DEFAULT_CASH_GOAL_LOW       = 3000;
    private static final int DEFAULT_CASH_GOAL_MEDIUM    = 6000;
    private static final int DEFAULT_CASH_GOAL_HIGH      = 8500;
    private static final int DEFAULT_CASH_GOAL_VERY_HIGH = 15000;
    public static final Map<String, Integer> CASH_GOAL_OPTIONS = new
            TreeMap<String, Integer>();
    public static final String MENU_SCREEN = "menu";
    public static final String GAME_SCREEN = "command board";
    public static final String VICTORY_SCREEN = "victory";
    public static final String[] PLAYABLE_BOARDS = {
        "Keyblade","Honeypot","Butterfly","Snailshell","Rings"};


    //private static Logger logger =  Logger.getLogger(PegSolitaire.class);

    public static final MatchFactory theMatchFactory = new MatchFactory();

    public static final int MAIN_WINDOW_HEIGHT = 600;
    public static final int MAIN_WINDOW_WIDTH = 800;
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
    	           if (MenuScreenView.modelThread != null) {
    	               MenuScreenView.modelThread.interrupt();
    	           }
    	           Platform.exit();
    	           System.exit(0);
    	       }
    	    });

        CASH_GOAL_OPTIONS.put((" $" + DEFAULT_CASH_GOAL_LOW       + "\t(Quick Game)"),
                                      DEFAULT_CASH_GOAL_LOW);
        CASH_GOAL_OPTIONS.put((" $" + DEFAULT_CASH_GOAL_MEDIUM    + "\t(Normal Game)"),
                                      DEFAULT_CASH_GOAL_MEDIUM);
        CASH_GOAL_OPTIONS.put((" $" + DEFAULT_CASH_GOAL_HIGH      + "\t(Long Game)"),
                                      DEFAULT_CASH_GOAL_HIGH);
        CASH_GOAL_OPTIONS.put(("$"  + DEFAULT_CASH_GOAL_VERY_HIGH + "\t(Very Long Game)"),
                                      DEFAULT_CASH_GOAL_VERY_HIGH);

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

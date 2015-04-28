package Main;

//import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import view.ControlledScreen;
import view.MatchView;
import view.MenuScreenView;
import controller.ScreenSwitcher;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Match;
import model.MatchFactory;

public class Main extends Application {

    /** Default settings for Match creation
     * Feel free to change these, they are arbitrary (for now).
     */
    public final static int MAX_NUMBER_OF_PLAYERS = 4;
    public final static Map<String, Integer> CASH_GOAL_OPTIONS = new
                                HashMap<String, Integer>();
    public final static String[] BOARD_STRING_OPTIONS = {"Default"};
    public final static String MENU_SCREEN = "menu";
    public final static String GAME_SCREEN = "command board";
    
    //private static Logger logger =  Logger.getLogger(PegSolitaire.class);

    public final static MatchFactory theMatchFactory = new MatchFactory();
    
    public final static int MAIN_WINDOW_HEIGHT = 600;
    public final static int MAIN_WINDOW_WIDTH = 800;

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
        MatchView gameMap = new MatchView();
        mainContainer.registerScreen(GAME_SCREEN, gameMap);
        ControlledScreen menuScreen = new MenuScreenView(gameMap);
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
        CASH_GOAL_OPTIONS.put("Low", 3000);
        CASH_GOAL_OPTIONS.put("Medium", 5000);
        CASH_GOAL_OPTIONS.put("High", 7000);
        
        primaryStage.setTitle("Command Board");
        primaryStage.setScene(initGameScene());
        primaryStage.show();        
        //logger.info("JavaFx game started");
    }
}

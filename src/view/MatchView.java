package view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import model.Match;
import model.Player;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;
import view.interfaces.ControlledScreen;
import controller.ScreenSwitcher;

/* TODO: Watch this class like a hawk!
 * Pay attention to which methods need to be synchronized and which do not!
 *
 * HINT: (Maybe this will help!)
 * Let's pretend that this class had a "main" function, something that runs for
 * a long period of time and calls other functions inside this class.
 * The goal then is to NOT synchronize the main function itself,
 * but any methods it calls instead. (Or if it's just a lot of chunks of code,
 * break up those chunks into separate synchronized(this) blocks!)
 * The idea is that we don't want to interrupt the subroutines that modify
 * values, but if we have a subroutine that modifies values X, Y and Z,
 * and then we have a subroutine right after that modifies values A, B and C,
 * then it's probably okay to have our threads interrupt in-between those
 * subroutines. So our first subroutine would be synchronized and so would our
 * second, leaving a gap in-between.
 */
public class MatchView implements ControlledScreen,
        PlayerRepresentative, Observer {

    private final static int GRID_SIZE = 15;
    private Group mainGroup;
    private ScreenSwitcher myController;
    private TileView[][] tileViews;
    private Joystick joystick;


    //for testing
    Scanner scan = new Scanner(System.in);

    //private Match match;

    private final static Image BACKGROUND_IMAGE = new Image(
            new File("images/gameBackground.jpg").toURI().toString(), true);
//            MatchView.class.getResource("/images/gameBackground.jpg")
//            .toString());

    public MatchView() {
        mainGroup = new Group();
        // TODO: Create scrollpane with grid of tiles
        // within.

        Group tileGroup = new Group();
        tileViews = new TileView[GRID_SIZE][GRID_SIZE];

        /* Create tile grid */
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                tileViews[x][y] = new TileView(x, y);
            }
        }

        /* Set the position of all the tiles and add all the tiles to a group */
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                if (tileViews[x][y] != null) {
                    tileViews[x][y].setTranslateX(x * TileView.TILE_PIX_WIDTH);
                    tileViews[x][y].setTranslateY(y * TileView.TILE_PIX_HEIGHT);
                    tileGroup.getChildren().add(tileViews[x][y]);
                }
            }
        }

        // Put this grid inside our scrollpane.
        ScrollPane sp = new ScrollPane();
        //sp.setVmax(arg0);
        //sp.setPrefSize(arg0, arg1);
        sp.setContent(tileGroup);

        // Add our scrollpane.
        mainGroup.getChildren().add(sp);

        // TODO: Create joystick UI.
        joystick = new Joystick();
        Group stick = joystick.getMainGroup();
        mainGroup.getChildren().add(stick);

        mainGroup.getChildren().get(1).setLayoutY(300);
        mainGroup.getChildren().get(1).setLayoutX(0);
    }

    // TODO: Decide if this should be synchronized
    public void loadMatch(Match m) {
    	joystick.registerMatch(m);
        // Set this view as the "player representative" for
        // all of the players.
        for (Player eachPlayer : m.getAllPlayers()) {
            eachPlayer.setRepresentative(this);
            //eachPlayer.setRepresentative(new AIEasy(eachPlayer));
        }

        // Set the background. This could change based on the map.
//        ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
//        mainGroup.getChildren().add(backgroundView);

        // Set the states of each tile in our grid based on the
        // map.
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                Tile t = m.getTile(x, y);
                tileViews[x][y].setCurrentState(t.getTileType());
            }
        }

        // A temporary label.
        Label temp = new Label();
        temp.setText("LOOK AT THEM GRAPHICS!");
        mainGroup.getChildren().add(temp);
    }

    @Override
    public synchronized void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    // TODO: Decide if this should be synchronized
    @Override
    public Parent getRoot() {
        return mainGroup;
    }

    //Skeleton functions to fill in for Dalen

    @Override
    @SuppressWarnings("deprecation")
	public synchronized CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections) {
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.chooseDirection(availableDirections);
    		}
    	});
    	MenuScreenView.modelThread.suspend();
    	
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    				joystick.turnDirectionsOff();
    			}
    		});
    	return joystick.getDirection();
    }

    //Random roll between 1 and 6
    @SuppressWarnings("deprecation")
	@Override
    public synchronized int getUsersRoll(){
    	//Change color
    	//there's css style for this sort of thing. 
    
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.meetNGreet();
    		}
    	});
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.activateDiceRoll();
    		}
    	});
    	System.out.println("\nHIT SELECT TO ROLL DICE\n");
    	MenuScreenView.modelThread.suspend();
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.turnStartOff();
    		}
    	});
    	return joystick.getRollResult();
    }

    // TODO: Decide if this should be synchronized
    //Loads joystick
    public Group getJoystick(){
    	Group joystick = new Group();
    	return joystick;
    }

    @Override
    public synchronized SpellID getSpellCast(SpellID[] availableSpells) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized boolean buyThisTile(PropertyTile tileForPurchase) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public synchronized CardShape placeWhichCard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized CardShape swapCardOnThisTile(PropertyTile tileForSwapping) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized Tile swapCardOnWhichTile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized int upgradeToWhatLevel(PropertyTile upgradingTile) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public synchronized PropertyTile sellWhichTile(PlayerID sellingPlayer) {
        // TODO Auto-generated method stub
        return null;
    }

    // Arg will be null.
    @Override
    public synchronized void update(Observable o, Object arg) {
        // TODO Auto-generated method stub

    }

}

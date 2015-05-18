package view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import model.Match;
import model.Player;
import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;
import view.interfaces.ControlledScreen;
import Main.Main;
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


    private VBox root;
    private BoardView board;
    private Joystick joystick;
    private ScreenSwitcher myController;
    private VictoryView victoryScreen;
    private Match m;


    //for testing
    //Scanner scan = new Scanner(System.in);

    //private Match match;

    private final static Image BOARD_BACKGROUND_IMAGE = new Image(
            new File("images/boardBackground.jpg").toURI().toString(), true);
//            MatchView.class.getResource("/images/gameBackground.jpg")
//            .toString());

    public MatchView(VictoryView victoryScreen) {
        root = new VBox();
        this.victoryScreen = victoryScreen;
    }

    public void loadMatch(Match m) {
        this.m = m;

        // Add ourself as an observer of the match.
        m.addObserver(this);

        double stageWidth = myController.getStageWidth();
        double stageHeight = myController.getStageHeight();

        root.setPrefSize(stageWidth, stageHeight);
        root.setPadding(new Insets(10)); // margin padding
        root.setSpacing(40); // spacing between our two nodes

        // Construct the board, which has its own
        // background, the tiles of the board, and
        // the player sprites on it.
        board = new BoardView(m);
        board.setPrefSize(stageWidth, stageHeight * 2 /3);
        //board.setAlignment(Pos.CENTER);

        // can remove below if match object ever contains the background to use.
        // right now we manually set it though.
        board.setBackground(BOARD_BACKGROUND_IMAGE);


        // Put our board into a scrollpane.
        ScrollPane sp = new ScrollPane();
        //sp.setVmax(arg0);
        sp.setPannable(true);
        sp.setPrefSize(stageWidth,
                stageHeight*2/3);
        sp.setContent(board);

        // Add our scrollpane to the root.
        root.getChildren().add(sp);


        // TODO: Create joystick UI.
        joystick = new Joystick();
        joystick.registerMatch(m);
        Group stick = joystick.getMainGroup();

//      stick.setLayoutY(stageHeight * 1/3);
//      stick.setLayoutX(0);

        root.getChildren().add(stick);

        // Set this view as the "player representative" for
        // all of the players.
        for (Player eachPlayer : m.getAllPlayers()) {
            eachPlayer.setRepresentative(this);
            //eachPlayer.setRepresentative(new AIEasy(eachPlayer));
        }
    }

    @Override
    public synchronized void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    // TODO: Decide if this should be synchronized
    @Override
    public Parent getRoot() {
        return root;
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
    			joystick.activateDiceRoll();
    		}
    	});
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
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.meetNGreet();
    		}
    	});
    	MenuScreenView.modelThread.suspend();
    	Platform.runLater(new Runnable() {

			@Override
			public void run() {
			   joystick.activateSpellPhase();
			}

    	});
    	MenuScreenView.modelThread.suspend();
    	Platform.runLater(new Runnable(){
    		@Override
    		public void run(){
    			joystick.turnSpellOff();
    		}
    	});
    	SpellID castedSpell = joystick.getCastedSpell();
    	joystick.spellReset();
    	return castedSpell;
        }

    @Override
    public synchronized boolean buyThisTile(PropertyTile tileForPurchase) {

//        Platform.runLater(new Runnable() {
//            @Override
//            public void run() {
//               joystick.buyTile();
//            }
//
//        });
//      MenuScreenView.modelThread.suspend();
        return true;
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized CardShape placeWhichCard() {
        // Places the first card
        return m.getPlayer(m.getCurrentPlayerID()).getHand().getAllCards()[0];
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized CardShape swapCardOnThisTile(PropertyTile tileForSwapping) {
        // Don't bother to swap cards
        return CardShape.NOCARD;
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized Tile swapCardOnWhichTile() {
        // Don't bother to swap cards
        return new NullTile();
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) {
        // Don't bother to upgrade tiles
        return new NullTile();
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized int upgradeToWhatLevel(PropertyTile upgradingTile) {
        // Don't bother to upgrade tiles
        return 1;
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public synchronized PropertyTile sellWhichTile(PlayerID sellingPlayer) {
        // Sell the first tile
        return m.getPlayer(sellingPlayer).getTilesOwned().get(0);
        // Alert! TODO Use the GUI to ask the users!
    }

    @Override
    public PlayerID castOnPlayer(SpellID spellCast) {
        // Cast spells on yourself
        return m.getCurrentPlayerID();
        // Alert! TODO Use the GUI to ask the users!
    }

    // Arg will be null.
    @Override
    public synchronized void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        boolean matchOver = m.isTheMatchOver();
        if (matchOver) {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                   goToNextScreen();
                }
            });
            MenuScreenView.modelThread.suspend();
        }
    }
    
    private void goToNextScreen() {
        victoryScreen.loadMatch(m);
        myController.setActiveScreen(Main.VICTORY_SCREEN);
        MenuScreenView.modelThread.resume();
    }
}

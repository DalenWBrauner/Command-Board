package view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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

public class MatchView implements ControlledScreen,
        PlayerRepresentative, Observer {

    
    private VBox root;
    private BoardView board;
    private ScreenSwitcher myController;
    
    //private Match match;

    private final static Image BOARD_BACKGROUND_IMAGE = new Image(
            new File("images/boardBackgroundGag.jpg").toURI().toString(), true);
//            MatchView.class.getResource("/images/gameBackground.jpg")
//            .toString());

    public MatchView() {
        root = new VBox();
    }

    public void loadMatch(Match m) {

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

        // Construct the joystick, which is
        // the user interface separate from the
        // board. This goes below the root.
        Joystick joystick = new Joystick();
        Group stick = joystick.getMainGroup();
//        stick.setLayoutY(stageHeight * 1/3);
//        stick.setLayoutX(0);
        root.getChildren().add(stick);

        
        // Set this view as the "player representative" for
        // all of the players.
        for (Player eachPlayer : m.getAllPlayers()) {
            eachPlayer.setRepresentative(this);
            //eachPlayer.setRepresentative(new AIEasy(eachPlayer));
        }        
    }

    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    @Override
    public Parent getRoot() {
        return root;
    }

    //Skeleton functions to fill in for Dalen

    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections){
        Random rand = new Random();
        int randInt =  rand.nextInt(availableDirections.length);
    	return availableDirections[randInt];
    }

    //Random roll between 1 and 6
    public int getUsersRoll(){
    	Random rand = new Random();
    	return rand.nextInt(6) + 1;
    }

    //Loads joystick
    public Group getJoystick(){
    	Group joystick = new Group();
    	return joystick;
    }

    @Override
    public SpellID getSpellCast(SpellID[] availableSpells) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public CardShape placeWhichCard() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tile swapCardOnWhichTile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int upgradeToWhatLevel(PropertyTile upgradingTile) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public PropertyTile sellWhichTile(PlayerID sellingPlayer) {
        // TODO Auto-generated method stub
        return null;
    }

    // Arg will be null.
    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub

    }

}

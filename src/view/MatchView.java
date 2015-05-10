package view;

import java.io.File;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
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

public class MatchView implements ControlledScreen,
        PlayerRepresentative, Observer {

    private final static int GRID_SIZE = 15;
    private Group mainGroup;
    private ScreenSwitcher myController;
    private TileView[][] tileViews;
    private Joystick joystick;
	Scanner reader = new Scanner(System.in);

    //private Match match;

    int i = 0;
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
        Group joystickGroup = joystick.getMainGroup();
        mainGroup.getChildren().add(joystickGroup);

        mainGroup.getChildren().get(1).setLayoutY(300);
        mainGroup.getChildren().get(1).setLayoutX(0);
    }

    public void loadMatch(Match m) {

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
    public void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    @Override
    public Parent getRoot() {
        return mainGroup;
    }

    //Skeleton functions to fill in for Dalen

    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections){
    	//Activate buttons
    	joystick.chooseDirection(availableDirections);  
    	//Let it go when the click occurs.     	
    	return joystick.getDirection();
    	

    }

    //Random roll between 1 and 6
    public int getUsersRoll(){
    	Random rand = new Random();
    	return rand.nextInt(6) + 1;
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

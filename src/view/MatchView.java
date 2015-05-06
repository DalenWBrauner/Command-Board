package view;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;

import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;
import model.Match;
import model.tile.PropertyTile;
import model.tile.Tile;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import controller.ScreenSwitcher;

public class MatchView implements ControlledScreen,
        PlayerRepresentative, Observer {

    private Group mainGroup;
    private ScreenSwitcher myController;
    //private Match match;
    
    private final static Image BACKGROUND_IMAGE = new Image(
            MatchView.class.getResource("/images/gameBackground.png")
            .toString());
    
    public MatchView() {
        mainGroup = new Group();
        // TODO: Create scrollpane with grid of tiles
        // within.
        
        // TODO: Create joystick UI.
        //Joystick joystick = new Joystick();
        //mainGroup.getChildren().add(joystick.getMainGroup());
    }
    
    public void loadMatch(Match m) {
        //match = m;
        
        ImageView backgroundView = new ImageView(BACKGROUND_IMAGE);
        mainGroup.getChildren().add(backgroundView);
        
        // A temporary label.
        Label temp = new Label();
        temp.setText("LOOK AT THEM GRAPHICS!");
        mainGroup.getChildren().add(temp);
        // ... do setup stuff (activate tiles)
    }

    @Override
    public void setScreenParent(ScreenSwitcher scSw) {
        myController = scSw;
    }

    @Override
    public Group getMainGroup() {
        return mainGroup;
    }
    
    //Skeleton functions to fill in for Dalen
    
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections){
    	return availableDirections[0];
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

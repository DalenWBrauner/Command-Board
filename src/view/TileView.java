package view;

import java.io.File;

import controller.TileEventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import shared.enums.TileType;

public class TileView extends ImageView {
    
    public final static Image PROPERTY_TILE_IMAGE = new Image(
            new File("images/Property.png").toURI().toString());
    public final static Image CHECKPOINT_TILE_IMAGE = new Image(
            new File("images/Checkpoint.png").toURI().toString());
    public final static Image START_TILE_IMAGE = new Image(
            new File("images/Start.png").toURI().toString());
    
    public final static int TILE_PIX_HEIGHT = 20;
    public final static int TILE_PIX_WIDTH = 20;
    
    private int xPos, yPos;
    private TileType currentState;
    
    public TileView(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.setCurrentState(TileType.NONE);
    }
    
    public TileView(int xPos, int yPos, TileType currentState) {
        this.setCurrentState(currentState);
        this.xPos = xPos;
        this.yPos = yPos;
    }
    
    public void setCurrentState(TileType currentState) {
        this.currentState = currentState;
        switch(currentState) {
            case NONE: 
                this.setImage(null);
                setOnMousePressed(null);
                break;
            case PROPERTY:
                this.setImage(PROPERTY_TILE_IMAGE); 
                setOnMousePressed(null);//new TileEventHandler());
                break;
            case START: 
                this.setImage(START_TILE_IMAGE); 
                setOnMousePressed(null);
                break;
            case CHECKPOINT: 
                this.setImage(CHECKPOINT_TILE_IMAGE); 
                setOnMousePressed(null);
                break;
        }
    }

}

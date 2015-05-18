package view;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.tile.CheckpointTile;
import model.tile.Tile;
import shared.enums.TileType;
import controller.PropertyTileEventHandler;

public class TileView extends ImageView {

    public final static Image PROPERTY_TILE_IMAGE = new Image(
            new File("images/Property.png").toURI().toString());
    public final static Image CHECKPOINT_TILE_RED_IMAGE = new Image(
            new File("images/Checkpoint - Red.png").toURI().toString());
    public final static Image CHECKPOINT_TILE_BLUE_IMAGE = new Image(
            new File("images/Checkpoint - Blue.png").toURI().toString());
    public final static Image CHECKPOINT_TILE_GREEN_IMAGE = new Image(
            new File("images/Checkpoint - Green.png").toURI().toString());
    public final static Image CHECKPOINT_TILE_YELLOW_IMAGE = new Image(
            new File("images/Checkpoint - Yellow.png").toURI().toString());
    public final static Image START_TILE_IMAGE = new Image(
            new File("images/Start.png").toURI().toString());

    public final static int TILE_PIX_HEIGHT = (int) START_TILE_IMAGE.getHeight();
    public final static int TILE_PIX_WIDTH = (int) START_TILE_IMAGE.getWidth();

    private int xPos, yPos;
    private TileType currentState;

    public TileView(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        currentState = TileType.NONE;
    }

    public TileView(int xPos, int yPos, TileType currentState) {
        this.currentState = currentState;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public void setCurrentState(Tile t) {
        TileType nextState = t.getTileType();
        if (currentState != nextState) {
            this.currentState = t.getTileType();
            switch(currentState) {
                case NONE:
                    this.setImage(null);
                    setOnMousePressed(null);
                    break;
                case PROPERTY:
                    this.setImage(PROPERTY_TILE_IMAGE);
                    setOnMousePressed(new PropertyTileEventHandler());
                    break;
                case START:
                    this.setImage(START_TILE_IMAGE);
                    setOnMousePressed(null);
                    break;
                case CHECKPOINT:
                    CheckpointTile chkptT = (CheckpointTile) t;
                    switch(chkptT.getColor()) {
                    case BLU:
                        this.setImage(CHECKPOINT_TILE_BLUE_IMAGE);
                        break;
                    case GRN:
                        this.setImage(CHECKPOINT_TILE_GREEN_IMAGE);
                        break;
                    case RED:
                        this.setImage(CHECKPOINT_TILE_RED_IMAGE);
                        break;
                    case YLW:
                        this.setImage(CHECKPOINT_TILE_YELLOW_IMAGE);
                        break;
                    default:
                        this.setImage(null);
                        break;
                    }
                    setOnMousePressed(null);
                    break;
            }

        }
    }

    public TileType getCurrentState() {
        return currentState;
    }

}

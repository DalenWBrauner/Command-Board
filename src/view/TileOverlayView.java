package view;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileOverlayView extends ImageView {
    
    public final static Image PROPERTY_TILE_IMAGE = new Image(
            new File("images/TileGlow.png").toURI().toString());
    
    private int xPos;
    private int yPos;
    
    public TileOverlayView(TileView tv) {
        
        this.xPos = tv.getXPos();
        this.yPos = tv.getYPos();
        setImage(PROPERTY_TILE_IMAGE);
    }
    
    public void setOverlay(String overlayCmd) {
        switch (overlayCmd) {
        case "highlight":
            setImage(PROPERTY_TILE_IMAGE);
            break;
        default:
            setImage(null);
            break;
        }
    }
    
    public int getXPos() {
        return xPos;
    }
    public int getYPos() {
        return yPos;
    }

}

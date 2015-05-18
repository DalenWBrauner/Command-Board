package view;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileOverlayView extends ImageView {

    public final static Image HIGHLIGHT_TILE_IMAGE = new Image(
            new File("images/TileGlow.png").toURI().toString());

    private int xPos;
    private int yPos;

    public TileOverlayView(TileView tv) {

        this.xPos = tv.getXPos();
        this.yPos = tv.getYPos();
    }

    public void setOverlay(String overlayCmd) {
        switch (overlayCmd) {
        case "highlight":
            setImage(HIGHLIGHT_TILE_IMAGE);
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

package view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileView extends ImageView {
    
    public final static Image KNIGHT_IMAGE = new Image(TileView.class.getResource("/images/knight.png").toString());
    public final static Image EMPTY_HIGHTLIGHT_IMAGE = new Image(TileView.class.getResource("/images/empty_highlight.png").toString());

    public static final int INACCESSIBLE = 0;  
    public static final int OCCUPIED = 1;  
    public static final int EMPTY = 2;  
    public static final int EMPTY_HIGHLIGHT = 3;  
    
    private GameMap gameMap;
    private int xPos, yPos;
    private int currentState;

}

package view;

import java.io.File;

import shared.enums.PlayerID;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class OwnerView extends ImageView{

    private final static Image P1_IMAGE = new Image(
            new File("images/Sign - Player 1.png").toURI().toString());
    private final static Image P2_IMAGE = new Image(
            new File("images/Sign - Player 2.png").toURI().toString());
    private final static Image P3_IMAGE = new Image(
            new File("images/Sign - Player 3.png").toURI().toString());
    private final static Image P4_IMAGE = new Image(
            new File("images/Sign - Player 4.png").toURI().toString());
    
    private PlayerID owner;
    
    public OwnerView() {
        setOwner(PlayerID.NOPLAYER);
    }
    public OwnerView(PlayerID o) {
        setOwner(o);
    }
    
    public void setOwner(PlayerID o) {
        if (owner != o) {
            owner = o;
            
            switch (o) {
            case NOPLAYER:
                setImage(null);
                break;
            case PLAYER1:
                setImage(P1_IMAGE);
                break;
            case PLAYER2:
                setImage(P2_IMAGE);
                break;
            case PLAYER3:
                setImage(P3_IMAGE);
                break;
            case PLAYER4:
                setImage(P4_IMAGE);
                break;
            default:
                setImage(null);
                break;
            }
        }
    }
    
    public PlayerID getOwner() {
        return owner;
    }
}

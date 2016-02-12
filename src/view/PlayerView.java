package view;

import java.io.File;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.player.Player;

public class PlayerView extends ImageView {

    public final static Image PLAYER1_IMAGE = new Image(
            new File("images/Player 1.png").toURI().toString());
    public final static Image PLAYER2_IMAGE = new Image(
            new File("images/Player 2.png").toURI().toString());
    public final static Image PLAYER3_IMAGE = new Image(
            new File("images/Player 3.png").toURI().toString());
    public final static Image PLAYER4_IMAGE = new Image(
            new File("images/Player 4.png").toURI().toString());

    private Player p;

    public PlayerView(Player p) {
        this.p = p;
        switch (p.getID()) {
        case PLAYER1:
            setImage(PLAYER1_IMAGE);
            break;
        case PLAYER2:
            setImage(PLAYER2_IMAGE);
            break;
        case PLAYER3:
            setImage(PLAYER3_IMAGE);
            break;
        case PLAYER4:
            setImage(PLAYER4_IMAGE);
            break;
        case NOPLAYER: // Do nothing.
            break;
        }
    }

    public Player getPlayer() {
        return p;
    }
}

package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import model.Match;
import model.Player;
import model.tile.Tile;

public class BoardView extends StackPane implements Observer {

    private Node background;
    private Group tileGroup;
    private Group playerGroup;

    private TileView[][] tileViews;
    private PlayerView[] players;
    private int boardWidth;
    private int boardHeight;
    private Match m;

    public BoardView(Match m) {

        this.m = m;
        
        // TODO: Load background from match?
        // ex: setBackground(m.getBackground());

        // Create group of all the tiles on the board.
        boardWidth = m.getBoard().getWidth();
        boardHeight = m.getBoard().getHeight();

        tileGroup = new Group();
        tileViews = new TileView[boardWidth][boardHeight]; //TODO: Get grid size from match object.

        /* Create tile grid */
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                tileViews[x][y] = new TileView(x, y);
            }
        }

        /* Set the position of all the tiles and add all the tiles to a group */
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                if (tileViews[x][y] != null) {
                    tileViews[x][y].setTranslateX(x * TileView.TILE_PIX_WIDTH);
                    tileViews[x][y].setTranslateY(y * TileView.TILE_PIX_HEIGHT);
                    tileGroup.getChildren().add(tileViews[x][y]);
                }
            }
        }




        // Set the states of each tile in our grid based on the
        // map.
        for (int x = 0; x < boardWidth; x++) {
            for (int y = 0; y < boardHeight; y++) {
                tileViews[x][y].setCurrentState(m.getTile(x, y));
            }
        }

        // Create group of all the player sprites on the board.
        playerGroup = new Group();
        // TODO: Put players in the right spots from match info.
        List<Player> modelPlayers = m.getAllPlayers();
        int numPlayers = modelPlayers.size();
        System.out.println("LKDJSFLSKJFKLDSJFLKSDJKL " + numPlayers);
        players = new PlayerView[numPlayers];
        for (int i=0; i < numPlayers; i++) { 
            Player p = modelPlayers.get(i);
            players[i] = new PlayerView(p);
            System.out.println("Player: " + p.getID().toString() + " has this X: " +
                    p.getX() + " and this Y: " +
                    p.getY() + ".");
            players[i].setTranslateX(p.getX() * TileView.TILE_PIX_WIDTH);
            players[i].setTranslateY(p.getY() * TileView.TILE_PIX_HEIGHT);
            playerGroup.getChildren().add(players[i]);
        }

        // The order of our stackpane will go: background image,
        // tile images, and then player sprite images.
        redrawSelf();
    }



    public void setBackground(Image newBackgroundImage) {
        setBackground(new Background(new BackgroundImage(newBackgroundImage,
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, false))));
    }

    private void redrawSelf() {
        ObservableList<Node> children = getChildren();
        children.clear();
        if (background != null) {
            children.add(background);
        }
        if (tileGroup != null) { // it never should be null, in fact.
            children.add(tileGroup);
        }
        if (playerGroup != null) {
            children.add(playerGroup);
        }
    }



    @Override
    public void update(Observable o, Object arg) {
        // TODO Auto-generated method stub
        for (int x=0; x < boardWidth; x++) {
            for (int y=0; y < boardHeight; y++) {
                tileViews[x][y].setCurrentState(m.getBoard().getTile(x, y));
//                TileView t = tileViews[x][y];
//                Tile modelT = m.getBoard().getTile(x, y);
//                if (t.getCurrentState() != modelT.getTileType()) {
//                    t.setCurrentState(t);
//                }
            }
        }
        
        List<Player> modelPlayers = m.getAllPlayers();
        int numPlayers = modelPlayers.size();
        for (int i=0; i < numPlayers; i++) { 
            Player p = modelPlayers.get(i);
            players[i].setLayoutX(p.getX());
            players[i].setLayoutY(p.getY());
        }
            
    }

}

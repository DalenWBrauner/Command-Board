package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import shared.enums.TileType;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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

        setAlignment(Pos.TOP_LEFT);
        
        this.m = m;
        m.addObserver(this);
        
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
                tileViews[x][y].setLayoutX(x * TileView.TILE_PIX_WIDTH);
                tileViews[x][y].setLayoutY(y * TileView.TILE_PIX_HEIGHT);
                tileViews[x][y].setCurrentState(m.getTile(x, y));
                Tile t = m.getTile(x , y);
                if (t.getTileType() == TileType.START) {
                    System.out.println("start tile has coords: " +
                            t.getX() + ", " + t.getY());
                }
                tileGroup.getChildren().add(tileViews[x][y]);
            }
        }

        // Create group of all the player sprites on the board.
        playerGroup = new Group();
        playerGroup.setAutoSizeChildren(false);
        playerGroup.setManaged(false);
        // TODO: Put players in the right spots from match info.
        List<Player> modelPlayers = m.getAllPlayers();
        int numPlayers = modelPlayers.size();
        System.out.println("LKDJSFLSKJFKLDSJFLKSDJKL " + numPlayers);
        players = new PlayerView[numPlayers];
        for (int i=0; i < numPlayers; i++) { 
            Player p = modelPlayers.get(i);
            players[i] = new PlayerView(p);
            System.out.println("Player: " + p.getID().toString() + " has coords: " +
                    p.getX() + ", " + p.getY());
            players[i].setTranslateX(p.getX() * TileView.TILE_PIX_WIDTH);
            players[i].setTranslateY(p.getY() * TileView.TILE_PIX_HEIGHT);
            players[i].setManaged(false);
            //playerGroup.getChildren().add(players[i]);
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
            for (int i=0; i < players.length; i++) {
                children.add(players[i]);
            }
            //children.add(playerGroup);
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
            players[i].setTranslateX(p.getX());
            players[i].setTranslateY(p.getY());
            players[i].setManaged(false);
            System.out.println(p.getID().toString() + " moved to " + p.getX() + ", " + p.getY());
        }
            
    }

}

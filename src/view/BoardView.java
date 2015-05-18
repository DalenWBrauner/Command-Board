package view;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javafx.application.Platform;
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
import shared.enums.TileType;

public class BoardView extends StackPane implements Observer {

    private Node background;
    private Group tileGroup;
    private Group tileDecorationsGroup;
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
        tileViews = new TileView[boardWidth][boardHeight];

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

        // Create empty group. We will fill this in the future with overlays
        // on top of our tiles.
        tileDecorationsGroup = new Group();
        tileDecorationsGroup.setManaged(false);

        // Create group of all the player sprites on the board.
        playerGroup = new Group();
//        playerGroup.setAutoSizeChildren(false);
        playerGroup.setManaged(false); // <-- VERY IMPORTANT WE DO THIS SO STACKPANE ISN'T RUDE
        List<Player> modelPlayers = m.getAllPlayers();
        int numPlayers = modelPlayers.size();
        players = new PlayerView[numPlayers];
        for (int i=0; i < numPlayers; i++) {
            players[i] = new PlayerView(modelPlayers.get(i));
            //players[i].setManaged(false);
            playerGroup.getChildren().add(players[i]);
        }

        // Place the players into their correct starting positions.
        placePlayers();

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
        if (tileDecorationsGroup != null) { // it never should be null, in fact.
            children.add(tileDecorationsGroup);
        }
        if (playerGroup != null) {
            children.add(playerGroup);
        }
    }

    public void highlightTile(int x, int y) {
        TileOverlayView t = new TileOverlayView(tileViews[x][y]);
        t.setOverlay("highlight");
        t.setTranslateX(x * TileView.TILE_PIX_WIDTH - 12);
        t.setTranslateY(y * TileView.TILE_PIX_WIDTH - 12);
        tileDecorationsGroup.getChildren().add(t);
    }

    public void unhighlightTile(int x, int y) {
        Node nodeToRemove = null;
        for (Node n : tileDecorationsGroup.getChildren()) {
            if (n instanceof TileOverlayView) {
                TileOverlayView t = (TileOverlayView)n;
                if (t.getXPos() == x && t.getYPos() == y) {
                    nodeToRemove = n;
                    break;
                }
            }
        }
        if (nodeToRemove != null) {
            tileDecorationsGroup.getChildren().remove(nodeToRemove);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
             // Update tile states.
                updateTiles();

                // Update player positions.
                placePlayers();

                // Wait half a second.
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                MenuScreenView.modelThread.resume();
            }
        });
        MenuScreenView.modelThread.suspend();
    }

    private void updateTiles() {
        // Update tile states
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
    }

    private void placePlayers() {
        List<Player> modelPlayers = m.getAllPlayers();
        int numPlayers = modelPlayers.size();

        boolean[] sharingSameSpot = new boolean[numPlayers];
        for (int i=0; i < numPlayers; i++) {
            for (int j=i+1; j < numPlayers; j++) {
                Player p1 = modelPlayers.get(i);
                Player p2 = modelPlayers.get(j);
                if (p1.getX() == p2.getX() &&
                    p1.getY() == p2.getY() ) {
                        sharingSameSpot[i] = true;
                        sharingSameSpot[j] = true;
                }
            }
        }

        for (int i=0; i < numPlayers; i++) {

            Player p = modelPlayers.get(i);
            if (sharingSameSpot[i]) {
                Double xCoords;
                Double yCoords;
                switch(i) {
                case 0:
                    xCoords = (double) (p.getX() * TileView.TILE_PIX_WIDTH);
                    yCoords = (double) (p.getY() * TileView.TILE_PIX_HEIGHT);
                    break;
                case 1:
                    xCoords = (p.getX() + 0.5) * TileView.TILE_PIX_WIDTH;
                    yCoords = (double) (p.getY() * TileView.TILE_PIX_HEIGHT);
                    break;
                case 2:
                    xCoords = (double) (p.getX() * TileView.TILE_PIX_WIDTH);
                    yCoords = (p.getY() + 0.5) * TileView.TILE_PIX_HEIGHT;
                    break;
                case 3:
                    xCoords = (p.getX() + 0.5) * TileView.TILE_PIX_WIDTH;
                    yCoords = (p.getY() + 0.5) * TileView.TILE_PIX_HEIGHT;
                    break;
                default:
                    xCoords = (double) (p.getX() * TileView.TILE_PIX_WIDTH);
                    yCoords = (double) (p.getY() * TileView.TILE_PIX_HEIGHT);
                }
                players[i].setTranslateX(xCoords);
                players[i].setTranslateY(yCoords);
            } else {
                players[i].setTranslateX((p.getX() + 0.25) * TileView.TILE_PIX_WIDTH);
                players[i].setTranslateY((p.getY() + 0.25) * TileView.TILE_PIX_HEIGHT);
            }
        }
    }
}

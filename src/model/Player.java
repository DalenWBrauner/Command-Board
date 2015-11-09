package model;

import java.util.ArrayList;

import model.tile.PropertyTile;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.interfaces.PlayerRepresentative;

public interface Player {
    public void setSeed(long seed);
    public int getX();
    public int getY();
    public void setX(int x);
    public void setY(int y);
    public void setPosition(int x, int y);
    public int getLastX();
    public int getLastY();
    public void setLastPosition(int x, int y);
    public PlayerID getID();
    public Hand getHand();
    public Wallet getWallet();
    public ArrayList<PropertyTile> getTilesOwned();
    public void gainTile(PropertyTile newTile);
    public void loseTile(PropertyTile oldTile);
    public void setPassed(CheckpointColor color, boolean passedOrNot);
    public boolean hasPassed(CheckpointColor color);
    public void setRepresentative(PlayerRepresentative myRep);
    public PlayerRepresentative getRepresentative();
}

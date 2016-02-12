package model.player;

import java.util.ArrayList;

import model.Hand;
import model.Wallet;
import model.tile.PropertyTile;
import shared.enums.CheckpointColor;
import shared.enums.PlayerID;
import shared.interfaces.NullRepresentative;
import shared.interfaces.PlayerRepresentative;

public class NullPlayer implements Player {
	private static final long serialVersionUID = 2380614319582208511L;

	@Override
    public void setSeed(long seed) { }

    @Override
    public int getX() { return 0; }

    @Override
    public int getY() { return 0; }

    @Override
    public void setX(int x) {}

    @Override
    public void setY(int y) {}

    @Override
    public void setPosition(int x, int y) {}

    @Override
    public Hand getHand() { return null; }

    @Override
    public Wallet getWallet() { return null; }

    @Override
    public ArrayList<PropertyTile> getTilesOwned() { return null; }

    @Override
    public PlayerID getID() { return PlayerID.NOPLAYER; }

    @Override
    public void gainTile(PropertyTile newTile) { newTile.reset(); }

    @Override
    public void loseTile(PropertyTile oldTile) {}

    @Override
    public int getLastX() { return 0; }

    @Override
    public int getLastY() { return 0; }

    @Override
    public void setLastPosition(int x, int y) { }

    @Override
    public void setPassed(CheckpointColor color, boolean passedOrNot) { }

    @Override
    public boolean hasPassed(CheckpointColor color) { return false; }

    @Override
    public void setRepresentative(PlayerRepresentative myRep) { }

    @Override
    public PlayerRepresentative getRepresentative() {
        return new NullRepresentative(this);
    }
}
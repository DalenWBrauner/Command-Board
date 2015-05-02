package shared.interfaces;

import java.util.Random;

import model.Match;
import model.Player;
import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;

public class NullRepresentative implements PlayerRepresentative {

    private Match theMatch;
    private Player thePlayer;
    private static Random r = new Random();

    public NullRepresentative(Match match, PlayerID myPlayersID) {
        theMatch = match;
        thePlayer = theMatch.getPlayer(myPlayersID);
    }

    @Override
    public SpellID getSpellCast(SpellID[] availableSpells) { return SpellID.NOSPELL; }

    @Override
    public int getUsersRoll() { return r.nextInt(6) + 1; }

    @Override
    public CardinalDirection forkInTheRoad( CardinalDirection[] availableDirections) { return availableDirections[0]; }

    @Override
    public Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) { return new NullTile(); }

    @Override
    public int upgradeToWhatLevel() { return 1; }

    @Override
    public PropertyTile sellWhichTile(PlayerID sellingPlayer) { return thePlayer.getTilesOwned().get(0); }

    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase) { return false; }

    @Override
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) { return CardShape.NOCARD; }

    @Override
    public Tile swapCardOnWhichTile() { return new NullTile(); }

}
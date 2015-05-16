package shared.interfaces;

import java.util.Random;

import model.Player;
import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;

public class NullRepresentative implements PlayerRepresentative {

    private Player thePlayer;
    private static Random r = new Random();

    public NullRepresentative(Player player) { thePlayer = player; }

    @Override
    public SpellID getSpellCast(SpellID[] availableSpells) { return SpellID.NOSPELL; }

    @Override
    public int getUsersRoll() { return r.nextInt(6) + 1; }

    @Override
    public CardinalDirection forkInTheRoad( CardinalDirection[] availableDirections) { return availableDirections[0]; }

    @Override
    public Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) { return new NullTile(); }

    @Override
    public int upgradeToWhatLevel(PropertyTile upgradingTile) { return 1; }

    @Override
    public PropertyTile sellWhichTile(PlayerID sellingPlayer) { return thePlayer.getTilesOwned().get(0); }

    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase) { return false; }

    @Override
    public CardShape placeWhichCard() { return thePlayer.getHand().getAllCards()[0]; }

    @Override
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) { return CardShape.NOCARD; }

    @Override
    public Tile swapCardOnWhichTile() { return new NullTile(); }

    @Override
    public PlayerID castOnPlayer(SpellID spellCast) { return thePlayer.getID(); }

    @Override
    public void gameOver() {
        // TODO Auto-generated method stub
        
    }

}

package shared.interfaces;

import java.util.Random;

import model.Player;
import model.tile.PropertyTile;
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
    public int[] upgradeWhichTile(PropertyTile[] upgradeableTiles) { return new int[] {}; }

    @Override
    public int upgradeToWhatLevel(PropertyTile upgradingTile) { return 1; }

    @Override
    public int[] sellWhichTile(PlayerID sellingPlayer) { return thePlayer.getTilesOwned().get(0).getPos(); }

    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase) { return false; }

    @Override
    public CardShape placeWhichCard() { return thePlayer.getHand().getAllCards()[0]; }

    @Override
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) { return CardShape.NOCARD; }

    @Override
    public int[] swapCardOnWhichTile() { return new int[] {}; }

    @Override
    public PlayerID castOnPlayer(SpellID spellCast) { return thePlayer.getID(); }
}

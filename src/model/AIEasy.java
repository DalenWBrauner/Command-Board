package model;

import java.util.ArrayList;
import java.util.Random;

import model.tile.NullTile;
import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;

public class AIEasy implements PlayerRepresentative {

    private Match theMatch;
    private Player thePlayer;
    private static Random r = new Random();

    public AIEasy(Match match, PlayerID myPlayersID) {
        theMatch = match;
        thePlayer = theMatch.getPlayer(myPlayersID);
    }

    @Override
    /** Easy AI doesn't cast spells */
    public SpellID getSpellCast(SpellID[] availableSpells) { return SpellID.NOSPELL; }

    @Override
    /** Easy AI doesn't cheat the dice roll */
    public int getUsersRoll() { return r.nextInt(6) + 1; }

    @Override
    /** Easy AI will move randomly (for now). */
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections) {
        return availableDirections[r.nextInt(availableDirections.length)];
    }

    @Override
    /** Easy AI will buy any Tile it can */
    public boolean buyThisTile(PropertyTile tileForPurchase) { return true; }

    @Override
    /** Easy AI won't bother to swap cards */
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) { return CardShape.NOCARD; }

    @Override
    /** Easy AI won't bother to swap cards */
    public Tile swapCardOnWhichTile() { return new NullTile(); }

    @Override
    /** Easy AI will upgrade a Tile at random */
    public Tile upgradeWhichTile(PropertyTile[] upgradeableTiles) {
        return upgradeableTiles[r.nextInt(upgradeableTiles.length)];
    }

    @Override
    /** Easy AI upgrades each Tile by 1 */
    public int upgradeToWhatLevel(PropertyTile upgradingTile) {
        return upgradingTile.getLevel() + 1;
    }

    @Override
    /** Easy AI sells a tile at random */
    public PropertyTile sellWhichTile(PlayerID sellingPlayer) {
        ArrayList<PropertyTile> tilesOwned = thePlayer.getTilesOwned();
        return tilesOwned.get(r.nextInt(tilesOwned.size()));
    }

}

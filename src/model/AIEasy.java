package model;

import java.util.ArrayList;
import java.util.Random;

import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;

public class AIEasy implements PlayerRepresentative {

    private Player thePlayer;
    private static Random r = new Random();

    public AIEasy(Player player) {
        thePlayer = player;
    }

    /** Easy AI doesn't cast spells. */
    @Override
    public SpellID getSpellCast(SpellID[] availableSpells) { return SpellID.NOSPELL; }

    /** Easy AI doesn't cheat the dice roll. */
    @Override
    public int getUsersRoll() { return r.nextInt(6) + 1; }

    /** Easy AI will move randomly (for now). */
    @Override
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections) {
        return availableDirections[r.nextInt(availableDirections.length)];
    }

    /** Easy AI will buy any Tile it can. */
    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase) { return true; }

    /** Easy AI will place a card at random. */
    @Override
    public CardShape placeWhichCard() {
        Hand playersHand = thePlayer.getHand();
        return playersHand.getAllCards()[r.nextInt(playersHand.size())];
    }

    /** Easy AI won't bother to swap cards. */
    @Override
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping) { return CardShape.NOCARD; }

    /** Easy AI won't bother to swap cards. */
    @Override
    public int[] swapCardOnWhichTile() { return new int[] {-1}; }

    /** Easy AI will upgrade a Tile at random. */
    @Override
    public int[] upgradeWhichTile(PropertyTile[] upgradeableTiles) {

        // TODO add .getPos() to tiles to simplify this
        int[] pos = new int[2];
        Tile chosenTile = upgradeableTiles[r.nextInt(upgradeableTiles.length)];
        pos[0] = chosenTile.getX();
        pos[1] = chosenTile.getY();
        return pos;
    }

    /** Easy AI upgrades each Tile by 1. */
    @Override
    public int upgradeToWhatLevel(PropertyTile upgradingTile) {
        return upgradingTile.getLevel() + 1;
    }

    /** Easy AI sells a tile at random. */
    @Override
    public int[] sellWhichTile(PlayerID sellingPlayer) {
        ArrayList<PropertyTile> tilesOwned = thePlayer.getTilesOwned();

        // TODO add .getPos() to tiles to simplify this
        int[] pos = new int[2];
        PropertyTile chosenTile = tilesOwned.get(r.nextInt(tilesOwned.size()));
        pos[0] = chosenTile.getX();
        pos[1] = chosenTile.getY();
        return pos;
    }

    /** Easy AI doesn't cast spells. */
    @Override
    public PlayerID castOnPlayer(SpellID spellCast) {
        return thePlayer.getID();
    }
}

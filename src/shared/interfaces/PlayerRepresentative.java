package shared.interfaces;

import model.tile.PropertyTile;
import model.tile.Tile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;

/** A PlayerRepresentative is an object that is designed to make
 * Player decisions. This object could be something that communicates to Users,
 * and asks them what they would like to do. This object could also be
 * some kind of AI, which makes decisions entirely on its own. Either way, the
 * PlayerRepresentative needs to be able to make decisions, and so long as it
 * can implement these functions, a PlayerRepresentative can effectively
 * play the game!
 *
 * @author Dalen W. Brauner
 *
 */
public interface PlayerRepresentative {

    /** The Model needs to know what spell is being cast!
     * The current player can be obtained from the Model.
     * @param availableSpells All spells the player has the cards to cast.
     * @return Which spell the Player chose to cast.
     */
    public SpellID getSpellCast(SpellID[] availableSpells);

    /** The Model needs to know the result of the Player's roll!
     *
     * Ultimately, it’s up to the View to provide the interactivity between the
     * Board game and its Users. Because actually rolling dice (or spinning a
     * roulette wheel, or picking one out of 6 cards) are all things that Users
     * could potentially want to do (and could be fun!), we think the View
     * should have the responsibility of determining the number, should any of
     * this interactivity be less than purely random. And of course, in the
     * event that it’s decided things should be purely random, the answer is a
     * simple call to random(1,6).
     *
     * @return The result of the roll.
     */
    public int getUsersRoll();

    /** The Model needs to know which way the Player wants to go!
     *
     * @param availableDirections All directions the Player is able to move in.
     * @return Which direction the Player chose to move in.
     */
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections);

    /** The Model needs to know whether the Player wants to buy a Tile!
     *
     * @param tileForPurchase The Tile the Player has the option of buying
     * (Note: this tile could belong to an opponent!)
     * @return Whether or not the Player would like to buy it.
     */
    public boolean buyThisTile(PropertyTile tileForPurchase);

    /** The Model needs to know whether the Player wants to swap out cards on this tile!
     *
     * @param tileForSwapping The PropertyTile the Player can swap cards with.
     * @return Which CardShape to place onto the Tile.
     * Return NOCARD if the Player does not wish to swap cards!
     */
    public CardShape swapCardOnThisTile(PropertyTile tileForSwapping);

    /** The Model needs to which Tile the Player wants to swap cards with!
     *
     * @return NullTile if the Player does not wish to swap cards with a Tile,
     *         or which PropertyTile the Player wishes to upgrade.
     */
    public Tile swapCardOnWhichTile();

    /** The Model needs to know which Tile the Player wants to upgrade!
     *
     * @param upgradeableTiles All possible Tiles the Player can upgrade.
     * @return NullTile if the Player does not wish to upgrade a Tile, or
     *         which PropertyTile the Player wishes to upgrade.
     */
    public Tile upgradeWhichTile(PropertyTile[] upgradeableTiles);

    /** The Model needs to know what Level the Tile is being upgraded to!
     *
     * @return The new Level the Player wants to upgrade their Tile to.
     */
    public int upgradeToWhatLevel(PropertyTile upgradingTile);

    /** The Model needs to know what Tile the Player has chosen to sell!
     *
     * @param sellingPlayer The ID of the Player currently forced to sell.
     * @return The PropertyTile the Player has chosen to sell.
     */
    public PropertyTile sellWhichTile(PlayerID sellingPlayer);

}

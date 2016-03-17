package shared.interfaces;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

import model.tile.PropertyTile;
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
public interface PlayerRepresentative extends Remote, Serializable {

    /** The Model needs to know what spell is being cast!
     * The current player can be obtained from the Model.
     * @param availableSpells All spells the player has the cards to cast.
     * @return Which spell the Player chose to cast.
     */
    public SpellID getSpellCast(SpellID[] availableSpells)
            throws RemoteException;

    /** The Model needs to know the result of the Player's roll!
     * Ultimately, this should be handled by a call to random.nextInt(1,6).
     * 
     * This is a method for a couple of different reasons.
     * For starters, providing this method allows us to easily provide the GUI
     * an opportunity to confirm that the user knows of the dice roll.
     * Additionally, this allows for the GUI to give some kind of relative
     * faux-interactivity to the roll, such as stopping a spinning roulette
     * wheel or picking one of 6 cards.
     * 
     * Perhaps more importantly is the networking case. The game itself is purely
     * deterministic sans the choices of other players and this single dice roll.
     * By requiring this information to be asked of a representative, we guarantee
     * it's sent over the wire for online games, meaning that all players will see
     * the same random dice roll.
     *
     * @return The result of the roll.
     */
    public int getUsersRoll()
            throws RemoteException;

    /** The Model needs to know which way the Player wants to go!
     * @param availableDirections All directions the Player is able to move in.
     * @return Which direction the Player chose to move in.
     */
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections)
            throws RemoteException;

    /** The Model needs to know whether the Player wants to buy a Tile!
     * @param tileForPurchase The Tile the Player has the option of buying.
     * @return Whether or not the Player would like to buy it.
     */
    public boolean buyThisTile(PropertyTile tileForPurchase)
            throws RemoteException;

    /** The Model needs to know which Card the Player wants to place on their Tile!
     * @param cardsOwned The cards the player can place on the tile.
     * @return A Card the Player is willing to give up to place on the Tile.
     */
    public CardShape placeWhichCard(CardShape[] cardsOwned)
            throws RemoteException;

    /** The Model needs to know whether the Player wants to swap out cards on this tile!
     * @param tileForSwapping The PropertyTile the Player can swap cards with.
     * @param cardsOwned The cards the player can place on the tile.
     * @return Which CardShape to place onto the Tile.
     * [return NOCARD if the Player does not wish to swap cards!]
     */
    public CardShape swapCardOnThisTile(CardShape[] cardsOwned, PropertyTile tileForSwapping)
            throws RemoteException;

    /** The Model needs to know which Tile the Player wants to swap cards with!
     * @param swappableTiles The tiles the player can swap cards with.
     * @return [x, y] coordinates of the PropertyTile the Player wishes to upgrade.
     * [return int[].size < 2 if the Player does not wish to swap cards with a Tile]
     */
    public int[] swapCardOnWhichTile(PropertyTile[] swappableTiles)
            throws RemoteException;

    /** The Model needs to know which Tile the Player wants to upgrade!
     * @param upgradeableTiles All possible Tiles the Player can upgrade.
     * @return [x, y] coordinates of the PropertyTile the Player wishes to upgrade.
     * [return int[].size < 2 if the Player does not wish to upgrade a Tile.]
     */
    public int[] upgradeWhichTile(PropertyTile[] upgradeableTiles)
            throws RemoteException;

    /** The Model needs to know what Level the Tile is being upgraded to!
     * @param levelsAvailable All possible levels the Tile can be upgraded to.
     * @param upgradingTile The tile being upgraded.
     * @return The new Level the Player wants to upgrade their Tile to.
     */
    public int upgradeToWhatLevel(int[] levelsAvailable, PropertyTile upgradingTile)
            throws RemoteException;

    /** The Model needs to know what Tile the Player has chosen to sell!
     * @param sellableTiles A list of tiles the player is able to sell.
     * @param sellingPlayer The ID of the Player currently forced to sell.
     * @return The [x,y] position of the PropertyTile the Player has chosen to sell.
     */
    public int[] sellWhichTile(PropertyTile[] sellableTiles, PlayerID sellingPlayer)
            throws RemoteException;

    /** The Model needs to know which Player is on the receiving end of a spell!
     * @param spellCast The ID of the Spell the Player wanted to cast.
     * @return Which player receives the effect of the Spell.
     */
    public PlayerID castOnPlayer(SpellID spellCast)
            throws RemoteException;
}
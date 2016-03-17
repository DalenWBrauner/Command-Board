package model.networking;

import java.rmi.RemoteException;

import model.tile.PropertyTile;
import shared.enums.CardShape;
import shared.enums.CardinalDirection;
import shared.enums.PlayerID;
import shared.enums.SpellID;
import shared.interfaces.PlayerRepresentative;

public class SpyPlayer implements PlayerRepresentative {
	private static final long serialVersionUID = -6191334219536090784L;

	private Coordinator myBoss;
    private PlayerRepresentative myTarget;
    private final int playerNumber;
    private int questionNumber;

    /** Creates a Spy that reports all of the player's actions back
     * to the coordinator.
     *
     * @param boss The Coordinator receiving the player's actions.
     * @param target The Player being spied on.
     */
    public SpyPlayer(Coordinator boss, PlayerRepresentative target, int playerID) {
        myBoss = boss;
        myTarget = target;
        questionNumber = 0;
        playerNumber = playerID;
    }

    @Override
    public SpellID getSpellCast(SpellID[] availableSpells)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        SpellID theirChoice = myTarget.getSpellCast(availableSpells);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public int getUsersRoll()
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        int theirChoice = myTarget.getUsersRoll();

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public CardinalDirection forkInTheRoad(CardinalDirection[] availableDirections)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        CardinalDirection theirChoice = myTarget.forkInTheRoad(availableDirections);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public boolean buyThisTile(PropertyTile tileForPurchase)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        boolean theirChoice = myTarget.buyThisTile(tileForPurchase);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public CardShape placeWhichCard(CardShape[] cardsOwned)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        CardShape theirChoice = myTarget.placeWhichCard(cardsOwned);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public CardShape swapCardOnThisTile(CardShape[] cardsOwned, PropertyTile tileForSwapping)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        CardShape theirChoice = myTarget.swapCardOnThisTile(cardsOwned, tileForSwapping);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public int[] swapCardOnWhichTile(PropertyTile[] swappableTiles)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        int[] theirChoice = myTarget.swapCardOnWhichTile(swappableTiles);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public int[] upgradeWhichTile(PropertyTile[] upgradeableTiles)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        int[] theirChoice = myTarget.upgradeWhichTile(upgradeableTiles);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public int upgradeToWhatLevel(int[] levelsAvailable, PropertyTile upgradingTile)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        int theirChoice = myTarget.upgradeToWhatLevel(levelsAvailable, upgradingTile);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public int[] sellWhichTile(PropertyTile[] sellableTiles, PlayerID sellingPlayer)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        int[] theirChoice = myTarget.sellWhichTile(sellableTiles, sellingPlayer);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }

    @Override
    public PlayerID castOnPlayer(SpellID spellCast)
            throws RemoteException {

        // Tell the player it's their turn but intercept their response
        PlayerID theirChoice = myTarget.castOnPlayer(spellCast);

        // Report back to HQ
        //System.out.println("SPY: Reporting decision to HQ...");
        myBoss.reportBack(playerNumber, questionNumber, theirChoice);
        questionNumber++;

        // Continue pretending I'm the player
        return theirChoice;
    }
}

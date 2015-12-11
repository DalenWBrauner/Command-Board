package model.networking;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

// A coordinator is someone who prepares all the Clients to
// play simultaneously and with one another
public interface Coordinator extends Remote {

    /** Returns your ID if you're given a slot,
     * returns a negative number if the game is full. */
    public int canIPlay()
            throws RemoteException;

    /** Returns an array of IDs if you're given a slot,
     * returns a length-1 array of negatives if the game is full. */
    public int[] canIPlay(int numPlayers)
            throws RemoteException;

    /** This function doesn't return control until the game begins! */
    public void imReady(int ID)
            throws RemoteException;

    /** imReady() but for multiple players. */
    public void imReady(int[] IDs)
            throws RemoteException;

//  /** Pass in your ID, confirming you no longer wish to play.
//  * I probably won't actually implement this yet. */
// public void IDecidedNotToPlay(int ID)
//         throws RemoteException;

    /** Someone informs you of what's happening on a given turn. */
    public void reportBack(int playerN, int turnN, Object theirChoice)
            throws RemoteException;

    /** Returns the operation that occured on a given turn and
     * blocks until that information is available. */
    public Object whatHappened(int playerN, int turnN)
            throws RemoteException;

    /** Returns the maximum number of players in a game. */
    int maxPlayers()
            throws RemoteException;

    /** Informs the coordinator you're done playing. */
    public void goodGame(int ID)
            throws RemoteException, NotBoundException;

    /** Informs the coordinator a number of players are done playing. */
    public void goodGame(int[] ID)
            throws RemoteException, NotBoundException;
}

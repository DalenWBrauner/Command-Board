package shared;

import java.util.Observable;
import java.util.Observer;

/** The WatchTower object exists to serve the Observer Pattern.
 * Simply put, anytime anything the WatchTower subscribes to is updated,
 * it updates all of its observers.
 *
 * The problem it solves is this:
 * Suppose you have a group of observable objects. These observable objects are related enough
 * that in order to be up-to-date, you need to observe them all. Also suppose these observable objects
 * are constantly changing; new ones are being created, old ones are being depreciated, etc.
 *
 * Now consider an observer. If you have one observer, you could loop through your observables
 * (assuming you have them all in a list somewhere) and register the observer to all of them.
 * Any time a new observable appears, you need to go find that observer and make sure the two are
 * registered.
 *
 * Now consider multiple observers. Consider observers being registered and unregistered just as
 * dynamically as the observables themselves. For each observer, it must be registered to ALL
 * observables! And for each observable, it must be registered to all observers!
 *
 * The WatchTower solves this problem by being a go-between or a "knot" between the Observers
 * and the Observables.
 * To the Observers, the WatchTower represents a group of Observables.
 * To the Observables, the WatchTower represents a group of Observers.
 *
 * Observers must only register themselves to one thing- the WatchTower- and nothing else,
 * in order to get notifications from all of the things the WatchTower is registered to.
 *
 * Observables must only register one observer- the WatchTower- and nothing else,
 * in order to send its notifications to all the things the WatchTower has registered.
 *
 * @author Dalen W. Brauner
 */
public class WatchTower extends Observable implements Observer {

    /** Passes on its updates to its own observers. */
    @Override
    public void update(Observable arg0, Object arg1) {
        System.out.println("WatchTower.update()");
        setChanged();
        notifyObservers();
    }
}

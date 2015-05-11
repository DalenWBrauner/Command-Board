package test.threading;

/** This small-scale concurrency test is designed to emulate
 * our potential concurrency issues as concisely as possible.
 *
 * The issue may not be clear just from running the code, or it may
 * seem obviously clear; either way, it is important that this code
 * exists and can be referenced as an example of what our code should
 * do and what our code should not do.
 *
 * The OmegaThread represents the Model.
 * The AlphaThread represents the View.
 * @author Dalen W. Brauner
 *
 * The problem is that the Model has a reference to the View, even though
 * the Model is on a separate thread. Therefore the Model can call functions
 * of the View, and the View's functions will still be called as normal
 * within the Model's thread.
 * This means that the View could be in the midst of ANYTHING, and the Model
 * could interrupt it with a DIFFERENT function call of the View.
 * i.e. the View could have any of its functions interrupt any of its other
 * functions at literally any time. The problems with this should be obvious.
 *
 * We must be careful in solving this concurrency issue, however; blind
 * synchronizations will only "starve" the Model and prevent it from ever
 * calling any function of the View, freezing the game for excessive
 * periods of time.
 *
 * tl;dr Any object of the View that is passed to the Model needs to be
 * heavily scrutinized (and synchronized) to remove concurrency issues.
 */
public class ConcurrencyTest {

    public static void main(String[] args) {
        /** Both of these run infinitely */
        //problem();
        //solution();
    }

    /** Our problem is that our threads can run over each other,
     * and sometimes even change data values as they're being used.
     *
     * You can find evidence of the problem because on occasion
     */
    public static void problem() {

        AlphaThread alpha = new AlphaThreadBroken();
        OmegaThread omega = new OmegaThread();

        omega.setAlpha(alpha);

        ((Thread) alpha).start();
        omega.start();

    }

    /** Our solution is either synchronizing any method that accesses
     * stored values, or to write specific (b)locks that do the same thing. */
    public static void solution() {

        AlphaThread alpha = new AlphaThreadFixed();
        OmegaThread omega = new OmegaThread();

        omega.setAlpha(alpha);

        ((Thread) alpha).start();
        omega.start();

    }

}

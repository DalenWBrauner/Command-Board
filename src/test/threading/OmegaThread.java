package test.threading;

public class OmegaThread extends Thread {
    private int switchesFlipped = 0;
    private AlphaThread myAlpha = null;

    @Override
    public void run() {

        // Every half-second
        while (true) {
            waitHalfASec();

            // Flip the switch and blab about it
            System.out.println("o: omega about to flip switch");
            flipSwitch();
            System.out.println("o: omega printing once:  "+switchesFlipped);
            System.out.println("o: omega printing again: "+switchesFlipped);
        }
    }

    public void setAlpha(AlphaThread alpha) {
        myAlpha = alpha;
    }

    /** Flips alpha's boolean value and blabs about it */
    private void flipSwitch() {
        boolean val = myAlpha.flipSwitch();
        switchesFlipped++;

        System.out.println("o: omega flipped the switch:");
        System.out.println("o: omega got "+val+"!");
    }

    /** Attempts to wait half of a second */
    private void waitHalfASec() {
        try {
            sleep(500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


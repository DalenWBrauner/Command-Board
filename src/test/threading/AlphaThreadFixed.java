package test.threading;

public class AlphaThreadFixed extends Thread implements AlphaThread {

    private boolean switchPosition = true;

    @Override
    public void run() {
        // Every 2 seconds
        while (true) {

            // We wait a few seconds
            // This method is not synchronized
            // So other threads can call
            // our synchronized methods no problem
            waitABit();

            // This block is synchronized
            // None of our other synchronized blocks
            // or methods may be active until this is finished
            // Alternatively, we can call dontInterrupt()
            synchronized (this){
                waitABit();
            }

            // We wait a few seconds again
            // Again, this can and will be interrupted
            waitABit();

            // This is a synchronized method
            // No other synchronized block or method will be called
            // While blab is running
            blab();
        }
    }

    /** Flips its boolean value and blabs about it */
    @Override
    public synchronized boolean flipSwitch() {
        System.out.println("a: begin flipSwitch()");
        switchPosition = !switchPosition;

        System.out.println("a: alpha's switch is flipped:");
        System.out.println("a: from "+(!switchPosition)+" to "+switchPosition);

        System.out.println("a: end   flipSwitch()");
        return switchPosition;
    }

    @SuppressWarnings("unused")
    private synchronized void dontInterrupt() {
        waitABit();
    }

    /** Attempts to wait half of a second */
    private void waitABit() {
        System.out.println("a: Begin waitABit()");
        try {
            sleep(600);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("a: End   waitABit()");
    }

    private synchronized void blab() {
        System.out.println("a: begin blab()");
        System.out.println("a: alpha printing once:  "+switchPosition);
        waitABit();
        System.out.println("a: alpha printing again: "+switchPosition);
        System.out.println("a: end   blab()");
    }
}


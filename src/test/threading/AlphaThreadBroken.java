package test.threading;

public class AlphaThreadBroken extends Thread implements AlphaThread{

    private boolean switchPosition = true;

    @Override
    public void run() {
        // Every .2 seconds
        while (true) {
            waitABit();
            blab();
        }
    }

    /** Flips its boolean value and blabs about it */
    @Override
    public boolean flipSwitch() {
        System.out.println("a: begin flipSwitch()");
        switchPosition = !switchPosition;

        System.out.println("a: alpha's switch is flipped:");
        System.out.println("a: from "+(!switchPosition)+" to "+switchPosition);

        System.out.println("a: end   flipSwitch()");
        return switchPosition;
    }

    /** Attempts to wait half of a second */
    private void waitABit() {
        try {
            sleep(600);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void blab() {
        System.out.println("a: begin blab()");
        System.out.println("a: alpha printing once:  "+switchPosition);
        waitABit();
        System.out.println("a: alpha printing again: "+switchPosition);
        System.out.println("a: end   blab()");
    }
}


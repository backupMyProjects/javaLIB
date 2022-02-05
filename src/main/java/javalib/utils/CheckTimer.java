package javalib.utils;

import static java.lang.System.out;

/**
 *
 * @author leo created at 2014/8/27 下午 05:08:24
 */
public class CheckTimer extends Thread {

    protected int checkRate = 100;
    private int timeout;
    private int timeoutElapsed;
    private boolean keepChecking = true;

    /**
     * @param	timeout	: setup whole timeout
     */
    public CheckTimer(int timeout) {
        // init status
        this.timeout = timeout;
        this.timeoutElapsed = 0;
    }

    /**
     * Resets the timer
     */
    public synchronized void reset() {
        timeoutElapsed = 0;
    }

    /**
     * Performs timer specific code
     */
    public void run() {
        // Keep looping
        out.println("checking...");
        while (keepChecking) {
            // Put the timer to sleep
            try {
                Thread.sleep(checkRate);
            } catch (InterruptedException ioe) {
                continue;
            }

            // Use 'synchronized' to prevent conflicts
            synchronized (this) {
                // Increment time remaining
                timeoutElapsed += checkRate;

                // Check to see if the time has been exceeded
                if (timeoutElapsed > timeout) {
                    // Trigger a timeout
                    timeout();
                }
            }

        }
    }

    // Override this function
    public void timeout() {
        System.err.println("Network timeout, terminating...");
        System.exit(1);
    }
    
    public void finished() {
        keepChecking = false;
    }
}

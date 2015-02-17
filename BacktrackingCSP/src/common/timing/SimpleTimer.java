/**
 * Matt Costi - mscosti  
 * Rob Dabrowski - rpdabrowski
 * 
 * CS 4341 C 2015
 * Prof. Niel Heffernan
 * WPI 
 */
package common.timing;


/**
 * A singleton implementation of a stop watch.
 * 
 */
public class SimpleTimer {
	private static SimpleTimer instance = new SimpleTimer();

	/** The private constructor */
	private SimpleTimer() {
	}

	/** @return the instance of the CountdownTimer */
	public static SimpleTimer getInstance() {
		return instance;
	}

	long start, stop;

	/** Starts the count down timer */
	public void start() {
		start = System.nanoTime();
	}

	/** Stops the timer and resets */
	public void stop() {
		stop = System.nanoTime();
		elapsed_milli = (stop - start) / 1000000;
		elapsed_micro = (stop - start) / 1000;
	}

	/** The total time elapsed in milliseconds */
	public static long elapsed_milli, elapsed_micro;

}

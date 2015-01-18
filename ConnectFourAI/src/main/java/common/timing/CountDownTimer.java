package common.timing;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A singleton implementation of a countdown timer for AI players to know how
 * much time they have left.
 * 
 * 
 * 
 * TODO Additional timer parameters
 * 
 * Update period for internal timer (precision of timer measurements) Calculate
 * on request vs. calculate at every time interval
 * 
 * 
 * TODO Additional timer features
 * 
 * Callback functions for killing decision processes
 * 
 */
public class CountDownTimer {
	private static int timeLimit_seconds, timeLimit_milli, period;
	private static CountDownTimer instance = new CountDownTimer();
	private static String name;

	/** The timer used to handle time */
	private Timer t;

	/** The private constructor */
	private CountDownTimer() {
	}

	/** @return the instance of the CountdownTimer */
	public static CountDownTimer getInstance() {
		return instance;
	}

	/**
	 * Initialize the count down timer with configuration details and parameters
	 * given through args.
	 * 
	 * @param playerName
	 * @param timeLimit
	 */
	public void init(String playerName, int timeLimit, List<String> args) {
		timeLimit_seconds = timeLimit;
		timeLimit_milli = timeLimit * 1000;
		period = 100; // milliseconds
		name = playerName + "-timer";
		t = new Timer(name);

		// // Check for parameters
		// if (args.contains("--unimplemented-timer-argument")){
		//
		// }

	}

	/** Starts the count down timer */
	public void start() {
		elapsed_milli = 0;
		elapsed_seconds = 0;
		t.scheduleAtFixedRate(new CountDownTimerTask(), 0, period);
	}

	/** Starts the count down timer */
	public void stop() {
		t.cancel();
		t = new Timer(name); // Make a new timer because cancel kills the thread
	}

	/** The total time elapsed in milliseconds */
	public static int elapsed_milli;
	/** The total time elapsed in seconds */
	public static int elapsed_seconds;
	/** The time elapsed as a % */
	public static float elapsed_percent;
	/** The time remaining in milliseconds */
	public static int remaining_milli;
	/** The time remaining in seconds */
	public static int remaining_seconds;

	/** Callback for updating the time */
	private void update_time() {
		// Calculate how much time has passed
		elapsed_milli += period; // The timer is assumed to be running on a
									// fixed period
		elapsed_seconds += elapsed_milli % 1000;

		// Calculate how much time is left in case anyone cares
		remaining_milli = timeLimit_milli - elapsed_milli;
		remaining_seconds = timeLimit_seconds - elapsed_seconds;

		// Calculate % of time elapsed
		elapsed_percent = (float) elapsed_milli / timeLimit_milli;
	}

	/** A timer task implementation used to update the time */
	class CountDownTimerTask extends TimerTask {

		@Override
		public void run() {
			update_time();
		}
	}

	/** Close up the timer */
	public void close() {
		t.cancel();
		t = null;
	}
}

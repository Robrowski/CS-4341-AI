package common.timing;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import common.FileLogger;

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
	public static int MIN_MILLIS_TO_BACK_OUT;
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

		// Clock timer optimization - keeps clock from going too fast
		if (timeLimit > 20)
			period = 1000;
		else if (timeLimit > 10)
			period = 250;
		else if (timeLimit > 5)
			period = 200;
		else if (timeLimit > 2)
			period = 100;
		else if (timeLimit > 1)
			period = 50;
		else
			period = 5;

		MIN_MILLIS_TO_BACK_OUT = period * 2;
		name = playerName + "-timer";
		t = new Timer(name);

		// Read for a max depth and the next number after it
		if (args.contains("CLOCKPERIOD=")) {
			String clock_period_arg = args
					.get(args.indexOf("CLOCKPERIOD=") + 1);
			try {
				period = Integer.parseInt(clock_period_arg);

			} catch (NumberFormatException mne) {
				FileLogger.getInstance().println(
						"Couldn't read the argument for CLOCKPERIOD= ... arg = "
								+ clock_period_arg);
			}
		}
		FileLogger.getInstance().println("Clock period set to " + period);

	}

	/** Starts the count down timer */
	public void start() {
		elapsed_milli = 0;
		elapsed_seconds = 0;
		remaining_milli = timeLimit_milli;
		remaining_seconds = timeLimit_seconds;
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

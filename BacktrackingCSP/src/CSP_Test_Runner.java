import java.util.Arrays;
import java.util.List;

import common.timing.SimpleTimer;


public class CSP_Test_Runner {
	public static final SimpleTimer time = SimpleTimer.getInstance();

	static final String filePath = "sample_logs/";
	static final String[] files = { "input1.txt", "input2.txt", "input3.txt",
			"input4.txt", "input5.txt", "input5_reordered.txt", "input6.txt",
			"input7_rob.txt" };

	public static void main(String[] args) {
		List<String> Largs = Arrays.asList(args);
		if (Largs.contains("REPEAT1k")) {
			batch_test();
			return;
		}

		/** Step 1: Read in the input file and arg for algorithm type. */
		if (args.length < 1) {
			System.err.println("Need to specify the file path");
		}

		// Parse arguments
		if (Largs.contains("FC")) {

		}
		if (Largs.contains("MCV")) {
			SolveCSP.mode = SolveCSP.MCV;
		}
		if (Largs.contains("ABC")) {
			SolveCSP.ABC_mode = true;
		}
		if (Largs.contains("LCV")) {
			if (SolveCSP.mode != SolveCSP.NORMAL) {
				System.err.println("Too many algorithms specified...");
				return;
			}
			SolveCSP.mode = SolveCSP.LCV;
		}
		// Read the problem in and solve it
		ProblemParser cp = new ProblemParser(args[0], false);
		time.start();
		SolveCSP.solve(cp);
		time.stop();
		System.out.println("Time Taken: " + SimpleTimer.elapsed_micro + " us");
		System.out.println("Time Taken: " + SimpleTimer.elapsed_milli + " ms");

		return;
	}

	static final int num_tests = 3000;

	/** Runs a super duper batch test! */
	private static void batch_test() {
		SolveCSP.batch_mode = true;
		// For each of the input files
		for (String f : files) {
			// Read the problem in
			ProblemParser cp = new ProblemParser(filePath + f, false);
			System.out.println(f);

			// For each of the known configurations
			for (int configNum = 0; configNum < 12; configNum++) {

				// Set up configurations
				SolveCSP.ABC_mode = configNum < 6; // ABC mode for first 6
				SolveCSP.FC = false; // default
				SolveCSP.mode = SolveCSP.NORMAL;

				switch (configNum){
				case 1:
					SolveCSP.FC = true;
					break;
				case 2:
					SolveCSP.mode = SolveCSP.MCV;
					break;
				case 3:
					SolveCSP.mode = SolveCSP.LCV;
					break;
				case 4:
					SolveCSP.mode = SolveCSP.MCV;
					SolveCSP.FC = true;
					break;
				case 5:
					SolveCSP.mode = SolveCSP.LCV;
					SolveCSP.FC = true;
					break;
				case 7:
					SolveCSP.FC = true;
					break;
				case 8:
					SolveCSP.mode = SolveCSP.MCV;
					break;
				case 9:
					SolveCSP.mode = SolveCSP.LCV;
					break;
				case 10:
					SolveCSP.mode = SolveCSP.MCV;
					SolveCSP.FC = true;
					break;
				case 11:
					SolveCSP.mode = SolveCSP.LCV;
					SolveCSP.FC = true;
					break;
				default:
					break;

				}

				System.out.format("	%,2d", configNum);

				int[] results = new int[num_tests];
				long[] times = new long[num_tests];
				long sum = 0, time_avg = 0;
				// Repeatedly solve the problem and record results
				for (int t = 0; t < num_tests; t++) {
					time.start();
					results[t] = SolveCSP.solve(cp);
					time.stop();
					// System.out.println(SimpleTimer.elapsed_milli);
					times[t] = SimpleTimer.elapsed_micro;
					time_avg += SimpleTimer.elapsed_micro / num_tests;
					sum += results[t];
				}
				Arrays.sort(results);
				Arrays.sort(times);

				System.out.format(" Checks Avg: %,7d  Median: %,7d",
						(int) (sum / num_tests), results[num_tests / 2]);

				System.out.format("		Times (us) Avg: %,7d  Median: %,7d\n",
						(int) time_avg, times[num_tests / 2]);

			}
		}
		System.out.println("\n DONE DONE DONE");
	}

}

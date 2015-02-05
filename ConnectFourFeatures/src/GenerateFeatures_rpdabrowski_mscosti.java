import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import common.FileLogger;
import common.board.ScoreBoard;

import evaluators.FeatureFactory;
import evaluators.features.Feature;

public class GenerateFeatures_rpdabrowski_mscosti {
	/** TODO MORE FEATURES */
	static final String[] feature_list = { "--score-board-feature",
			"--influence-score-board-feature", "PieceCount" };

	public static void main(String[] args) {
		if (args.length != 2) {
			System.out.println("arg0 = input file, arg1 = output file");
			return;
		}

		// Read args to get filepath to txt file
		Path fFilePath = Paths.get(args[0]);

		// FileLogger - Only use this one :P
		FileLogger logger = FileLogger.getInstance();
		logger.init(args[1], new LinkedList<String>()); // No args needed


		List<Feature> features = FeatureFactory
				.makeFeatures(feature_list, args);

		// Printing a header
		System.out.println("Num features: " + features.size());
		for (Feature f : features) {
			logger.print(f.getName() + ","); // CSV
		}
		logger.println("winner");

		// Iterate through each line of the file
		try (Scanner scanner = new Scanner(fFilePath)) {
			System.out.println("Scanning now!");
			while (scanner.hasNextLine()) {
				String l = scanner.nextLine();
				ScoreBoard sb = new ScoreBoard(7, 6, 4);
				String[] parsed = l.split(delims);
				sb.upload(parsed);

				// For each line of the file
				for (Feature f : features) {
					logger.print(f.score(sb) + ","); // CSV
				}

				// Put The result at the end
				logger.println(parsed[parsed.length - 1]);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Poop");
		}
		System.out.println("Done!");
		return;
	}

	final static String delims = ",";

}

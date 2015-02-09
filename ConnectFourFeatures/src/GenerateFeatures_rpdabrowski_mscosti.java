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
	static final String[] feature_list = { "PieceCount", "OneTurnWin",
			"Connect2", "individual-empty", "individual-occupied",
			"influence-score-board", "score-board-occupied" };

	static boolean include_raw = false;
	static String raw_header = "a1,a2,a3,a4,a5,a6,b1,b2,b3,b4,b5,b6,c1,c2,c3,c4,c5,c6,d1,d2,d3,d4,d5,d6,e1,e2,e3,e4,e5,e6,f1,f2,f3,f4,f5,f6,g1,g2,g3,g4,g5,g6,result,";

	public static void main(String[] args) {
		System.out.println("arg0 = input file, arg1 = output file");
		System.out.println("arg 2 = 'raw' or nothing");
		if (args.length < 2) {
			return;
		}

		// Read args to get filepath to txt file
		Path fFilePath = Paths.get(args[0]);
		if (args.length == 3)
			include_raw = args[2].equals("raw");

		// FileLogger - Only use this one :P
		FileLogger logger = FileLogger.getInstance();
		logger.init(args[1], new LinkedList<String>()); // No args needed


		List<Feature> features = FeatureFactory
				.makeFeatures(feature_list, args);

		// Printing a header
		System.out.println("Num features: " + features.size());
		if (include_raw)
			logger.print(raw_header);
		for (Feature f : features) {
			logger.print(f.getName() + ","); // CSV
		}
		logger.println("winner");

		// Iterate through each line of the file
		try (Scanner scanner = new Scanner(fFilePath)) {
			System.out.println("Scanning now!");
			while (scanner.hasNextLine()) {
				String l = scanner.nextLine();
				if (include_raw)
					logger.print(l + ",");
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

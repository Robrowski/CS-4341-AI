import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import common.Bag;
import common.Item;

import constraints.Constraint;
import constraints.EqualBinary;
import constraints.MutuallyExclusiveBinary;
import constraints.NotEqualBinary;
import constraints.UnaryExclusive;
import constraints.UnaryInclusive;


public class ProblemParser {

	/* These variables are public and intended to be copied once */
	ArrayList<Constraint> constraints = new ArrayList<Constraint>();
	HashMap<String, Item> items = new HashMap<String, Item>();
	HashMap<String, Bag> bags = new HashMap<String, Bag>();
	
	public Path fFilePath;
	private boolean print;
	private static String delims = " ", sec_delim = "#####";

	/**
	 * Takes the file name specifying
	 * 
	 * @param filename
	 */
	public ProblemParser(String filename, boolean show_printstatements) {
		this.fFilePath = Paths.get(filename);
		this.print = show_printstatements;
		this.parse();
	}

	/**
	 * Causes the parser to parse the entire file
	 */
	public void parse() {
		Scanner file_scanner = makeScanner();

		int current_section = 0;
		if (print)
			System.out.println("Beginning file parsing...");
		while (file_scanner.hasNextLine()) {
			String l = file_scanner.nextLine();

			// Check for section header = #####
			// EX. first line should be a #####
			if (l.contains(sec_delim)) {
				current_section++;
				if (print)
					System.out.println("Scanning section: " + current_section);
				continue;
			}

			// Gota parse data to get money
			String[] parsed = l.split(delims);

			// Handle each of the 8 cases
			switch (current_section) {
			case 1: // Items
				items.put(parsed[0],new Item(parsed[0], Integer.parseInt(parsed[1])));
				break;
			case 2: // Bags
				bags.put(parsed[0],new Bag(parsed[0], Integer.parseInt(parsed[1])));
				break;
			case 3: // Fitting limits
				int lowerFit = Integer.parseInt(parsed[0]);
				int upperFit = Integer.parseInt(parsed[1]);

				// Update limits for all bags
				for (Bag b : bags.values()) {
					b.setLowerFit(lowerFit).setUpperFit(upperFit);
				}

				break;
			case 4: // Unary Inclusive
				UnaryInclusive ui = new UnaryInclusive(items.get(parsed[0]));
				
				for (int i = 1; i < parsed.length; i++) {
					ui.addBag(bags.get(parsed[i]));
				}
				constraints.add(ui);
				
				break;
			case 5: // Unary Exclusive
				UnaryExclusive ue = new UnaryExclusive(items.get(parsed[0]));

				for (int i = 1; i < parsed.length; i++) {
					ue.addBag(bags.get(parsed[i]));
				}
				constraints.add(ue);

				break;
			case 6: // Binary Equals
				constraints.add(new EqualBinary(items.get(parsed[0]), items
						.get(parsed[1])));
				break;
			case 7: // Binary not equals
				constraints.add(new NotEqualBinary(items.get(parsed[0]), items
						.get(parsed[1])));
				break;
			case 8: // Binary simultaneous
				constraints.add(new MutuallyExclusiveBinary(items
						.get(parsed[0]), items.get(parsed[1]), bags
						.get(parsed[2]), bags.get(parsed[3])));

				break;
			default:
				System.err
						.println("Well shit, this file has too many sections");
				throw new RuntimeException(
						"Well shit, this file has too many sections");
			}
		}
		if (print)
			System.out.println("File parsing complete.");
	}

	/**
	 * Make a new file scanner.
	 * 
	 * @return
	 */
	private Scanner makeScanner() {
		Scanner file_scanner;

		try {
			file_scanner = new Scanner(fFilePath);
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Can't open the specified file.");
			throw new RuntimeException("Give up now!");
		}
		return file_scanner;
	}
}

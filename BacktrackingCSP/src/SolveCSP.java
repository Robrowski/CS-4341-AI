import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import common.Bag;
import common.ConstraintManager;
import common.FileLogger;
import common.Item;
import common.State;

import constraints.Constraint;

public class SolveCSP {

	static Stack<State> backStack = new Stack<State>();
	static Bag[] bags;
	static Item[] items;
	static FileLogger placement_logger = new FileLogger("placement_log.txt",
			new LinkedList<String>());
	static ConstraintManager cm;

	private static final int NORMAL = 0, MCV = 1, LCV = 2;
	private static int mode = 0;

	public static void main(String[] args) {
		/** Step 1: Read in the input file and arg for algorithm type. */
		if (args.length < 1) {
			System.err.println("Need to specify the file path");
		}
		
		// Parse arguments
		List<String> Largs = Arrays.asList(args);
		if (Largs.contains("FC")){

		}
		if (Largs.contains("MCV")){
			mode = MCV;
		}	
		if (Largs.contains("LCV")){
			if (mode != NORMAL){
				System.err.println("Too many algorithms specified...");
				return;
			}
			mode = LCV;
		}

		// Read the problem in
		ProblemParser cp = new ProblemParser(args[0], true);
		cp.parse();
	
		
		// Make arrays out of everything
		Constraint[] c = cp.constraints.toArray(new Constraint[cp.constraints
				.size()]);
		items = cp.items.values().toArray(new Item[cp.items.size()]);
		bags = cp.bags.values().toArray(new Bag[cp.bags.size()]);

		// Initialize tools
		cm = new ConstraintManager(c);


		// Set up data structures
		totalNumItems = items.length;
		State intialState = new State(bags, items);
		State.initialize(bags, items);

		// Initialize stack with item 1 + each bag --- do this same thing at
		// each new level
		pushBagsOntoStack(intialState);
		
		// Statistics counter
		int fails = 0, successes = 0;

		while (!backStack.isEmpty()) {
			State to_try = backStack.pop();
			
			// Try to place the intended item in the inteded bag,
			// according to the stack
			if (!cm.placeItemInBag(to_try.intendedItem, to_try.intendedBag,
					to_try)) {
				placement_logger.println("Failed to place "
						+ to_try.intendedItem + " in " + to_try.intendedBag,
						numTabs(to_try) + 1);
				fails++;
				continue; // Stop checking a failure of a branch
			}
			successes++;
			placement_logger.println("Successfully placed "
					+ to_try.intendedItem + " in " + to_try.intendedBag,
					numTabs(to_try));

			// Check to see if we are done
			if (to_try.getItemsLeft().size() == 0) {
				placement_logger.println("Solved!", numTabs(to_try));
				System.out.println("We did it! Gaaayyyy!");
				to_try.printSolution();
				reportStats(fails, successes);
				return;
			}
			
			pushBagsOntoStack(to_try);

		}
		reportStats(fails, successes);
		System.out.println("NO SOLUTION WAS FOUND. GG UNINSTALL");
		FileLogger solution_logger = new FileLogger("SOLUTION_log.txt",
				new LinkedList<String>());
		solution_logger.println("NO SOLUTION WAS FOUND. GG UNINSTALL");
	}
	
	private static int totalNumItems = -1;

	private static int numTabs(State s) {
		return Math.max(0, totalNumItems - s.getItemsLeft().size() - 1);
	}
	
	
	/**
	 * Push options for item in bag placement onto the stack to try next
	 * 
	 * @param to_copy
	 */
	private static void pushBagsOntoStack(State to_copy) {
		if (bags.length == 0 || items.length == 0)
			return;

		/**
		 * MRV = Minimum Remaining VALUES = MCV Most Constrained VARIABLE = pick
		 * the item (variable) with the least amount of possible bags (values)
		 * to put it in
		 * 
		 * (useful only when forward checking and keeping tallies of impossible
		 * outcomes)
		 * 
		 * idea behind it: Fail fast, pick variables with out many branches
		 */

		/**
		 * DH = Degree Heuristic = tie breaker, pick the variable with the most
		 * amount of constraints associated with it
		 */
		// Fill the stack with more placements to try
		Item next_item = to_copy.getItemsLeft().get(0);
		if (mode == MCV) {
			// How use Degree Heuristic
			next_item = cm
					.DegreeHeuristic(to_copy.getMostConstrainedVariable());
		}

		/**
		 * LCV = Least Constraining VALUES = pick the bag (value) with the least
		 * amount of constraints associated with them to allow maximum
		 * flexibility
		 * 
		 * (Pick the bag with the least shit in it = more room for other shit)
		 * 
		 * idea behind it: more room to succeed
		 */
		Bag[] le_bags = bags;
		if (mode == LCV)
			le_bags = (Bag[]) to_copy.getLeastConstrainedValue().toArray(
				new Bag[bags.length]);

		// Naive Implementation
		for (Bag b : le_bags) {
			State next = to_copy.deepCopy();
			next.intendedItem = next_item;
			next.intendedBag = b;
			backStack.push(next);
		}
	}
	
	private static void reportStats(int fails, int successes) {
		placement_logger.println("\n~~~~~Statistics~~~~");
		placement_logger.println("Fails: " + fails);
		placement_logger.println("Successes: " + successes);
		placement_logger.println("Consistency Checks: " + (fails + successes));
		System.out.println("Consistency Checks: " + (fails + successes));
	}
	
	
	

}

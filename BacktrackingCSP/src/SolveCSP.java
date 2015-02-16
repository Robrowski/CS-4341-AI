import java.util.LinkedList;
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

	public static void main(String[] args) {
		/** Step 1: Read in the input file and arg for algorithm type. */
		if (args.length < 1 || args.length > 2) {
			System.err.println("Need two arguments: INPUT_FILE_NAME ALGORITHM");
		}
		ProblemParser cp = new ProblemParser(args[0], true);
		cp.parse();
		// String alg = args[1];

		// Make arrays out of everything
		Constraint[] c = cp.constraints.toArray(new Constraint[cp.constraints
				.size()]);
		items = cp.items.values().toArray(new Item[cp.items.size()]);
		bags = cp.bags.values().toArray(new Bag[cp.bags.size()]);

		// Initialize tools
		ConstraintManager cm = new ConstraintManager(c);
		FileLogger placement_logger = new FileLogger("placement_log.txt",
				new LinkedList<String>());

		// Set up data structures
		totalNumItems = items.length;
		State intialState = new State(bags, items);
		State.initialize(bags, items);

		// Initialize stack with item 1 + each bag --- do this same thing at
		// each new level
		pushBagsOntoStack(intialState);
		
		while (!backStack.isEmpty()) {
			State to_try = backStack.pop();
			
			// Try to place the intended item in the inteded bag,
			// according to the stack
			if (!cm.placeItemInBag(to_try.intendedItem, to_try.intendedBag,
					to_try)) {
				placement_logger.println("Failed to place "
						+ to_try.intendedItem + " in " + to_try.intendedBag,
						numTabs(to_try) + 1);
				continue; // Stop checking a failure of a branch
			}
			
			placement_logger.println("Successfully placed "
					+ to_try.intendedItem + " in " + to_try.intendedBag,
					numTabs(to_try));

			// Check to see if we are done
			if (to_try.getItemsLeft().size() == 0) {
				placement_logger.println("Solved!", numTabs(to_try));
				System.out.println("We did it! Gaaayyyy!");
				to_try.printSolution();
				return;
			}
			
			pushBagsOntoStack(to_try);

		}
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
		// Fill the stack with more placements to try
		Item next_item = to_copy.getItemsLeft().get(0);

		// TODO Switch statement based on algorithm type
		Bag[] le_bags = bags;

		// Naive Implementation
		for (Bag b : le_bags) {
			State next = to_copy.deepCopy();
			next.intendedItem = next_item;
			next.intendedBag = b;
			backStack.push(next);
		}
		
	}
	
	
	
	

}

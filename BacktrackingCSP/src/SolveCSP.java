import common.Bag;
import common.ConstraintManager;
import common.Item;

import constraints.Constraint;

public class SolveCSP {

	public SolveCSP() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		/** Step 1: Read in the input file and arg for algorithm type. */
		if (args.length < 1 || args.length > 2) {
			System.err.println("Need two arguments: INPUT_FILE_NAME ALGORITHM");
		}
		ProblemParser cp = new ProblemParser(args[0], true);
		String alg = args[1];

		ConstraintManager cm = new ConstraintManager((Bag[]) cp.bags.values()
				.toArray(), (Item[]) cp.items.values().toArray(),
				(Constraint[]) cp.constraints.toArray());

		/**
		 * constraint, storing each one Step 3: Start solving CSP given
		 * constraints Step 4: ?????? Step 5: PROFIT
		 */


	}

}

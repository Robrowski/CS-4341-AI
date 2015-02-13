package constraints;

import common.Bag;
import common.Item;
import common.State;

public class MutuallyExclusiveBinary implements Constraint {
	public Item itemA, itemB;
	public Bag bagA, bagB;

	/**
	 * Make a mutually exclusive binary constraint, that says that if itemA is
	 * an bagA, then itemB cannot be in bagB
	 * 
	 * @param itemA
	 * @param itemB
	 * @param bagA
	 * @param bagB
	 */
	public MutuallyExclusiveBinary(Item itemA, Item itemB, Bag bagA, Bag bagB) {
		this.itemA = itemA;
		this.itemB = itemB;
		this.bagA = bagA;
		this.bagB = bagB;
	}

	@Override
	public boolean isValid(State currentState, Bag bag, Item given) {

		// Check that the bag and item we are dealing with match the constraint
		if (given.name.equals(this.itemA.name)
				&& bag.name.equals(this.bagA.name)) {
			// check to see if ItemB is in BagB
			Bag bagCheck = currentState.inBag(this.itemB);
			if (bagB != null){
				// if item B is in Bag B, item A can't be put into bagA
				if (bagCheck.name.equals(this.bagB.name))
					return false;
			}
		}
		else if (given.name.equals(this.itemB.name)
				&& bag.name.equals(this.bagB.name)) {
			// check to see if ItemA is in BagA
			Bag bagCheck = currentState.inBag(this.itemA);
			if (bagA != null) {
				// if item A is in Bag A, item B can't be put into bagB
				if (bagCheck.name.equals(this.bagA.name))
					return false;
			}
		}
		return true;
	}

}

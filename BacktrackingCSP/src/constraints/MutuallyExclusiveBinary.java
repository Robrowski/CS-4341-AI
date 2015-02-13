package constraints;

import common.Bag;
import common.Item;
import common.State;

public class MutuallyExclusiveBinary implements Constraint {
	private Item itemA, itemB;
	private Bag bagA, bagB;

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
	public boolean isValid(State currentState, Bag bag, Item item) {
		// TODO Auto-generated method stub
		return false;
	}

}

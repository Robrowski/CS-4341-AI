package constraints;

import common.Bag;
import common.Item;
import common.State;

public class EqualBinary implements Constraint {

	public Item A;
	public Item B;

	/**
	 * Make an equal binary constraint, giving the two items that must be placed
	 * in the same bag
	 * 
	 * @param A
	 * @param B
	 */
	public EqualBinary(Item A, Item B) {
		this.A = A;
		this.B = B;
	}

	@Override
	public boolean isValid(State currentState, Bag bag, Item given) {
		// Check to see if if the given item is either item A or B in this
		// constraint
		if (given.name.equals(this.A.name)) {
			// If the given item is A, check to see if itemB is placed in the
			// same bag as A
			Bag bagB = currentState.inBag(this.B);
			if (bagB != null) { // not yet placed in a bag, not a problem
				if (!bagB.name.equals(bag.name))
					return false;
			}
		} else if (given.name.equals(this.B.name)) {
			// If the given item is B, check to see if itemA is placed in the
			// same bag as B
			Bag bagA = currentState.inBag(this.A);
			if (bagA != null) { // not yet placed in a bag, not a problem
				if (!bagA.name.equals(bag.name))
					return false;
			}
		}
		// either the other item is not placed, or they are in the same bag
		return true;
	}

	@Override
	public boolean appliesTo(Item given) {
		if (this.A.name.equals(given.name) || this.B.name.equals(given.name))
			return true;
		return false;
	}

	@Override
	public void forwardInvalidate(State currentState, Bag placed, Item given) {
		if (given.name.equals(this.A.name)) {
			for (Bag b : State.bags.keySet()) {
				if (!(placed.name.equals(b.name))) {
					currentState.constrainSpace(B, b);
				}
			}
		} else if (given.name.equals(this.B.name)) {
			for (Bag b : State.bags.keySet()) {
				if (!(placed.name.equals(b.name))) {
					currentState.constrainSpace(A, b);
				}
			}
		}
	}

	@Override
	public void forwardInvalidate(State currentState) {
		for (Bag b : State.bags.keySet()) {
			if (currentState.getStateValue(b, A) == -1) {
				currentState.constrainSpace(B, b);
			}
			else if (currentState.getStateValue(b, B) == -1) {
				currentState.constrainSpace(A, b);
			}
		}
	}
}

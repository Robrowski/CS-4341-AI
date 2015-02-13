package constraints;

import common.Item;
import common.State;

public class NotEqualBinary implements Constraint {

	private Item A;
	private Item B;

	/**
	 * Make a not equal binary constraint, giving the two items that are not
	 * allowed to ever be in the same bag
	 * 
	 * @param A
	 * @param B
	 */
	public NotEqualBinary(Item A, Item B) {
		this.A = A;
		this.B = B;
	}

	@Override
	public boolean isValid(State currentState) {
		// TODO Auto-generated method stub
		return false;
	}

}

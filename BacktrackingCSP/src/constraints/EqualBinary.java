package constraints;

import common.Item;

public class EqualBinary implements Constraint {

	private Item A;
	private Item B;

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

}

package constraints;

import common.Bag;
import common.Item;
import common.State;

public interface Constraint {

	boolean isValid(State currentState, Bag bag, Item item);

	boolean appliesTo(Item given);

	void forwardInvalidate(State currentState, Bag placed, Item given);

	void forwardInvalidate(State currentState);
	/**
	 * To be implemented further once we decide what methods each constraint
	 * must have
	 */
}

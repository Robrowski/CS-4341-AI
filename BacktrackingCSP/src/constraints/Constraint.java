package constraints;

import common.State;

public interface Constraint {

	boolean isValid(State currentState);

	/**
	 * To be implemented further once we decide what methods each constraint
	 * must have
	 */
}

package common;

import constraints.Constraint;

public class ConstraintManager {
	
	private Constraint[] constraints;
	private State currentState;

	/**
	 * Create a constraint manager. A ConstraintManager will be what a CSP
	 * algorithm will talk with to know when a move is invalid, among other
	 * things.
	 * 
	 * @param allBags
	 * @param allItems
	 * @param constraints
	 */
	public ConstraintManager(Bag[] allBags, Item[] allItems,
			Constraint[] constraints) {
		this.constraints = constraints;
		this.currentState = new State(allBags, allItems);
	}
	
	/**
	 * Try and place an item into the given bag. If successful, the state is
	 * updated and the function returns true. If unsuccessful, return false.
	 * 
	 * @param toPlace
	 * @param inBag
	 * @return true on success, false on failure.
	 */
	public boolean placeItemInBag(Item toPlace, Bag inBag) {
		if (canFit(toPlace,inBag)){
			for (Constraint c : constraints) {
				if (!c.isValid(currentState, inBag, toPlace))
					return false;
			}
			currentState.addItemToBag(toPlace, inBag);
		}
		else{
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks to see if an item can fit in the given bag, by checking to see how
	 * full it is in terms of weight and number of items it can hold.
	 * 
	 * @param toPlace
	 * @param inBag
	 * @return
	 */
	public boolean canFit(Item toPlace, Bag inBag){
		int[] baggedItems = currentState.getBagState(inBag);
		int bagSum = 0;
		int numItems = 0;
		for (int weight : baggedItems) {
			bagSum += weight;
		}
		if (bagSum <= inBag.weightCapacity && numItems <= inBag.upperFit)
			return true;
		else
			return false;
	}
}

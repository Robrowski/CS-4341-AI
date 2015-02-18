package common;

import java.util.ArrayList;

import constraints.Constraint;
import constraints.UnaryExclusive;
import constraints.UnaryInclusive;

public class ConstraintManager {
	
	public Constraint[] constraints;

	/**
	 * Create a constraint manager. A ConstraintManager will be what a CSP
	 * algorithm will talk with to know when a move is invalid, among other
	 * things.
	 * 
	 * @param allBags
	 * @param allItems
	 * @param constraints
	 */
	public ConstraintManager(Constraint[] constraints) {
		this.constraints = constraints;
	}
	
	/**
	 * Try and place an item into the given bag. If successful, the state is
	 * updated and the function returns true. If unsuccessful, return false.
	 * 
	 * @param toPlace
	 * @param inBag
	 * @return true on success, false on failure.
	 */
	public boolean placeItemInBag(Item toPlace, Bag inBag, State s) {
		if (canFit(toPlace, inBag, s)) {
			for (Constraint c : constraints) {
				if (!c.isValid(s, inBag, toPlace))
					return false;
			}
			s.addItemToBag(toPlace, inBag);

		}
		else{
			return false;
		}
		
		return true;
	}
	
	public void initForwardChecking(State s) {
		for (Constraint c : constraints) {
			if (c.getClass() == UnaryInclusive.class
					|| c.getClass() == UnaryExclusive.class) {

				c.forwardInvalidate(s);

			}
		}
	}

	/**
	 * Checks to see if an item can fit in the given bag, by checking to see how
	 * full it is in terms of weight and number of items it can hold.
	 * 
	 * @param toPlace
	 * @param inBag
	 * @return
	 */
	public boolean canFit(Item toPlace, Bag inBag, State s) {
		int[] baggedItems = s.getBagState(inBag);
		int bagSum = toPlace.weight;
		int numItems = 1; // Assumed current piece is placed
		for (int i = 0; i < baggedItems.length; i++) {
			bagSum += baggedItems[i];
			if (baggedItems[i] > 0)
				numItems++;
		}
		if (bagSum <= inBag.weightCapacity && numItems <= inBag.upperFit)
			return true;
		else
			return false;
	}

	/**
	 * Degree Heuristic - MCV tie breaker
	 * 
	 * In the event that two or more variables are tied for most constrained,
	 * then pick the one with the most constraint rules that include it.
	 * 
	 * @param items
	 * @return
	 */
	public Item DegreeHeuristic(ArrayList<Item> items) {
		int maxDegree = -1;

		Item mostConstrainedByRules = items.get(0);
		for (Item i : items) {
			int degree = 0;
			for (Constraint c : constraints) {
				if (c.appliesTo(i)) degree++;
			}
			if (degree > maxDegree) {
				maxDegree = degree;
				mostConstrainedByRules = i;
			}
		}

		return mostConstrainedByRules;
	}

	/**
	 * Checks that the given state is valid.
	 * 
	 * Validity 1: all bags are > 90% full Validity 2: all bags have the minimum
	 * number of items.
	 * 
	 * @param s
	 * @return
	 */
	public boolean solutionIsValid(State s, FileLogger l) {
		for (int bag_id = 0; bag_id < State.bags.size(); bag_id++) {

			Bag b = State.bags_by_id.get(bag_id);

			int num_items = 0, total_weight = 0;
			for (int item_id = 0; item_id < State.items_by_id.size(); item_id++) {
				if (s.stateTable[bag_id][item_id] <= 0)
					continue;
				num_items++;
				total_weight += s.stateTable[bag_id][item_id];
			}

			// Minimum weight of 90%
			if (((float) total_weight / b.weightCapacity) < 0.90) {
				l.println("Weight capactity not met");
				return false;
			}

			// Minimum number of items.
			if (num_items < b.lowerFit) {
				l.println("Not enough items in bag: " + b.toString());
				return false;
			}
		}

		return true;
	}
}

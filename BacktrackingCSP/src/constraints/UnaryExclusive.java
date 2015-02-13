package constraints;

import java.util.ArrayList;
import java.util.Arrays;

import common.Bag;
import common.Item;
import common.State;

public class UnaryExclusive implements Constraint {

	public Item item;
	public ArrayList<Bag> bags;

	/**
	 * make a Unary Exclusive Constraint on an Item, and add the bags that are
	 * constrained later
	 * 
	 * @param item
	 */
	public UnaryExclusive(Item item) {
		this.item = item;
		bags = new ArrayList<Bag>();
	}

	/**
	 * Make a Unary Exclusive Constraint on an Item, and the bags that the item
	 * is not allowed to be in
	 * 
	 * @param item
	 * @param bags
	 */
	public UnaryExclusive(Item item, Bag[] bags) {
		this.item = item;
		this.bags = new ArrayList<Bag>(Arrays.asList(bags));
	}

	/**
	 * add a new bag that the Item is not allowed to be in
	 * 
	 * @param newBag
	 */
	public void addBag(Bag newBag) {
		bags.add(newBag);
	}

	@Override
	public boolean isValid(State currentState, Bag bag, Item given) {
		// if the item given is the same as the item this constraint deals with,
		// check if the item cant go in the given bag
		if (given.name.equals(this.item.name)) {
			if (this.bags.contains(bag)) {
				return false;
			}
		}
		return true;
	}

}

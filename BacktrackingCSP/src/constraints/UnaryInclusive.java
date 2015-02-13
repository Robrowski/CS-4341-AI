package constraints;

import java.util.ArrayList;
import java.util.Arrays;

import common.Bag;
import common.Item;
import common.State;

public class UnaryInclusive implements Constraint {

	private Item item;
	private ArrayList<Bag> bags;

	/**
	 * Make a Unary Inclusive Constraint on an Item, and add the bags that are
	 * constrained later
	 * 
	 * @param item
	 */
	public UnaryInclusive(Item item) {
		this.item = item;
		bags = new ArrayList<Bag>();
	}

	/**
	 * Make a Unary Inclusive Constraint on an Item, and the bags the item is
	 * only allowed to be in
	 * 
	 * @param item
	 * @param bags
	 */
	public UnaryInclusive(Item item, Bag[] bags) {
		this.item = item;
		this.bags = new ArrayList<Bag>(Arrays.asList(bags));
	}

	/**
	 * Add a new bag that the item is allowed to be in
	 * 
	 * @param newBag
	 */
	public void addBag(Bag newBag) {
		bags.add(newBag);
	}

	@Override
	public boolean isValid(State currentState) {
		// TODO Auto-generated method stub
		return false;
	}

}

package constraints;

import java.util.ArrayList;
import java.util.Arrays;

import common.Bag;
import common.Item;

public class UnaryExclusive implements Constraint {

	private Item item;
	private ArrayList<Bag> bags;

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

}

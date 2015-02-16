package common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class State {

	static Map<Bag, Integer> bags;
	static Map<Item, Integer> items;
	static Map<Integer, Bag> bags_by_id;
	static Map<Integer, Item> items_by_id;

	public ArrayList<Item> itemsLeft;

	public Item intendedItem;
	public Bag intendedBag;

	/**
	 * The stateTable is an array of [#bags][#items], so we can lookup if an
	 * Item is currently in a bag, and if so, the value at [bag][item] will be
	 * the item's weight. A value of 0 indicates an Item is not in that bag, and
	 * a value of -1 indicates it is impossible for an item to be in that bag.
	 * 
	 * Example of a completely known state. Depending on the algorithm, you may
	 * not choose to update impossible states.
	 * 
	 * 				A |	B |	C | D
	 *  	bag_a[	-1	-1	4	-1]
	 *  	bag_b[	7   4	-1  0 ]
	 *  	bag_c[	-1	-1	-1  5 ]
	 */
	int[][] stateTable;

	public State() {

	}

	public State(Bag[] allBags, Item[] allItems) {
		itemsLeft = new ArrayList<Item>(Arrays.asList(allItems));
		stateTable = new int[allBags.length][allItems.length];
	}
	
	/**
	 * A State represents where everything is in the world. It keeps track of
	 * all of the Items that are currently in bags, and serves the dual purpose
	 * of keeping track of knowledge of impossible states.
	 * 
	 * @param allBags
	 * @param allItems
	 * @return
	 */
	public static void initialize(Bag[] allBags, Item[] allItems) {
		// Initialize the hashmaps to easily lookup the index a bag
		// or item is in the constraint space table
		bags = new HashMap<Bag, Integer>();
		bags_by_id = new HashMap<Integer, Bag>();

		items = new HashMap<Item, Integer>();
		items_by_id = new HashMap<Integer, Item>();

		int index = 0;
		for (Bag bag : allBags) {
			bags_by_id.put(index, bag);
			bags.put(bag, index);
			index += 1;
		}
		index = 0;
		for (Item item : allItems) {
			items_by_id.put(index, item);
			items.put(item, index);
			index += 1;
		}
	}

	public State deepCopy() {
		State s = new State();
		s.itemsLeft = new ArrayList<Item>(itemsLeft);
		int numBags = bags.keySet().size();
		int numItems = items.keySet().size();

		int[][] newStateTable = new int[numBags][numItems];
		for (int i = 0; i < numBags; i++)
			newStateTable[i] = Arrays.copyOf(this.stateTable[i], numItems);

		s.stateTable = newStateTable;
		return s;
	}

	/**
	 * Add an item to the bag by updating the stateTable
	 * 
	 * @param toPlace
	 * @param inBag
	 */
	public void addItemToBag(Item toPlace, Bag inBag) {
		int bagIndex = bags.get(inBag);
		int itemIndex = items.get(toPlace);

		stateTable[bagIndex][itemIndex] = toPlace.weight;
		itemsLeft.remove(toPlace);
	}

	/**
	 * Get a list of all items that haven't been placed in a bag yet
	 */

	public ArrayList<Item> getItemsLeft() {
		return itemsLeft;
	}

	/**
	 * Return a bag's state, or the bag's row in the table.
	 * 
	 * @param bag
	 * @return
	 */
	public int[] getBagState(Bag bag) {
		return stateTable[bags.get(bag)];
	}

	/**
	 * Checks to see if the an item is currently placed inside of any bag, and
	 * if it is, return the bag it is in. If it hasn't been placed, return Null
	 * 
	 * @param item
	 * @return
	 */
	public Bag inBag(Item item) {
		int itemIndex = items.get(item);
		for (Bag b : bags.keySet()) {
			int[] bagState = getBagState(b);

			// if the bag has a weight of over 0, we know the item is in the bag
			if (bagState[itemIndex] > 0) {
				return b;
			}
		}
		return null;
	}

	/** Prints the solution as described in the assignment */
	public void printSolution() {
		FileLogger solution_logger = new FileLogger("SOLUTION_log.txt",
				new LinkedList<String>());

		for (int bag_id = 0; bag_id < bags.size(); bag_id++) {
						
			Bag b = bags_by_id.get(bag_id);
			solution_logger.print("Bag: " + b.name + "  Items: ");
			
			int num_items = 0, total_weight = 0;
			for (int item_id = 0; item_id < items_by_id.size(); item_id++) {
				if (stateTable[bag_id][item_id] <= 0)
					continue;
				Item i = items_by_id.get(item_id);
				solution_logger.print(i.name + " ");
				num_items++;
				total_weight += stateTable[bag_id][item_id];
			}
			solution_logger.println("\nNumber of Items: " + num_items);
			solution_logger.println("Total Weight: " + total_weight + "/"
					+ b.weightCapacity);
			solution_logger.println("Wasted Capacity: "
					+ (b.weightCapacity - total_weight) + "\n");
		}
	}

}

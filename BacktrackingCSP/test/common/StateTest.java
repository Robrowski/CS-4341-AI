package common;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class StateTest {
	Bag a = new Bag("a", 10);
	Bag b = new Bag("b", 10);
	Item X = new Item("X", 5);
	Item Y = new Item("Y", 3);

	Bag[] bags;
	Item[] items;

	@Before
	public void setUp() throws Exception {

		bags = new Bag[] { a, b };
		items = new Item[] { X, Y };

	}

	@Test
	public void initStateTest() {
		State newState = new State(bags, items);
		assertEquals(2, newState.stateTable.length);
		assertEquals(2, newState.stateTable[0].length);
	}

	@Test
	public void addItemXtoBag_a() {
		State newState = new State(bags, items);

		newState.addItemToBag(X, a);
		Bag in = newState.inBag(X);
		assertEquals("a", in.name);
	}

}

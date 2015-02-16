package constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;
import common.State;

public class EqualBinaryConstraint {

	Bag a = new Bag("a", 10);
	Bag b = new Bag("b", 10);
	Item X = new Item("X", 5);
	Item Y = new Item("Y", 3);
	Item Z = new Item("Z", 4);

	Bag[] bags;
	Item[] items;

	@Before
	public void setUp() throws Exception {
		bags = new Bag[] { a, b };
		items = new Item[] { X, Y, Z };
		State.initialize(bags, items);
	}

	@Test
	public void testInitEqualBinaryConstraint() {
		EqualBinary equalBinary = new EqualBinary(X, Y);

		assertEquals("X", equalBinary.A.name);
		assertEquals("Y", equalBinary.B.name);
	}

	@Test
	public void testXYEmptyState() {
		EqualBinary equalBinary = new EqualBinary(X, Y);
		State emptyState = new State(bags, items);
		assertTrue(equalBinary.isValid(emptyState, a, X));
	}

	@Test
	public void testXY_YinBag_a() {
		EqualBinary equalBinary = new EqualBinary(X, Y);
		State newState = new State(bags, items);
		newState.addItemToBag(Y, a);
		assertTrue(equalBinary.isValid(newState, a, X));
		assertFalse(equalBinary.isValid(newState, b, X));
	}

	@Test
	public void testUnconstrainted() {
		EqualBinary equalBinary = new EqualBinary(X, Y);
		State newState = new State(bags, items);

		newState.addItemToBag(Y, a);
		assertTrue(equalBinary.isValid(newState, b, Z));
		assertTrue(equalBinary.isValid(newState, a, Z));
	}

}

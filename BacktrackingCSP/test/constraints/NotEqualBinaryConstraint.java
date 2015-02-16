package constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;
import common.State;

public class NotEqualBinaryConstraint {

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
	public void testInitNotEqualBinaryConstraint() {
		NotEqualBinary notEqualBinary = new NotEqualBinary(X, Y);

		assertEquals("X", notEqualBinary.A.name);
		assertEquals("Y", notEqualBinary.B.name);
	}

	@Test
	public void testXYEmptyState() {
		NotEqualBinary notEqualBinary = new NotEqualBinary(X, Y);
		State emptyState = new State(bags, items);
		assertTrue(notEqualBinary.isValid(emptyState, a, X));
	}

	@Test
	public void testXY_YinBag_a() {
		NotEqualBinary notEqualBinary = new NotEqualBinary(X, Y);
		State newState = new State(bags, items);
		newState.addItemToBag(Y, a);
		assertFalse(notEqualBinary.isValid(newState, a, X));
		assertTrue(notEqualBinary.isValid(newState, b, X));
	}

	@Test
	public void testUnconstrainted() {
		NotEqualBinary notEqualBinary = new NotEqualBinary(X, Y);
		State newState = new State(bags, items);

		newState.addItemToBag(Y, a);
		assertTrue(notEqualBinary.isValid(newState, b, Z));
		assertTrue(notEqualBinary.isValid(newState, a, Z));
	}

}

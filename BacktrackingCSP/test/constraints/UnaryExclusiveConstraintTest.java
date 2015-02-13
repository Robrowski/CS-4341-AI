package constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;
import common.State;

public class UnaryExclusiveConstraintTest {

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
	public void testInitUnaryExclusive() {
		UnaryExclusive unaryExcl = new UnaryExclusive(Y, new Bag[] { a });

		assertEquals("Y", unaryExcl.item.name);
		assertEquals(1, unaryExcl.bags.size());
	}

	@Test
	public void testItemYNotAllowedInBag_a() {
		UnaryExclusive unaryExcl = new UnaryExclusive(Y, new Bag[] { a });
		State emptyState = new State(bags, items);

		assertFalse(unaryExcl.isValid(emptyState, a, Y));
		assertTrue(unaryExcl.isValid(emptyState, b, Y));
	}

	@Test
	public void testValidForItemNotInConstraint() {
		UnaryExclusive unaryExcl = new UnaryExclusive(Y, new Bag[] { a });
		State emptyState = new State(bags, items);

		assertTrue(unaryExcl.isValid(emptyState, b, X));
		assertTrue(unaryExcl.isValid(emptyState, a, X));
	}

}

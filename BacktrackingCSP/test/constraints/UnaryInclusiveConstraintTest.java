package constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;
import common.State;

public class UnaryInclusiveConstraintTest {

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
	public void testInitUnaryInclusiveConstraint() {
		UnaryInclusive unaryInc = new UnaryInclusive(X, new Bag[] { a });

		assertEquals("X", unaryInc.item.name);
		assertEquals(1, unaryInc.bags.size());
	}

	@Test
	public void testItemXConstrainedToBag_a() {
		UnaryInclusive unaryInc = new UnaryInclusive(X, new Bag[] { a });
		State newState = new State(bags, items);

		assertTrue(unaryInc.isValid(newState, a, X));
		assertFalse(unaryInc.isValid(newState, b, X));
	}

	@Test
	public void testItemNotConstrainted() {
		UnaryInclusive unaryInc = new UnaryInclusive(X, new Bag[] { a });
		State newState = new State(bags, items);

		assertTrue(unaryInc.isValid(newState, a, Y));
		assertTrue(unaryInc.isValid(newState, b, Y));

	}

}

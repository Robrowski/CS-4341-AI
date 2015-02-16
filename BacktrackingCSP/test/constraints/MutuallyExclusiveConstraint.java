package constraints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;
import common.State;

public class MutuallyExclusiveConstraint {

	Bag x = new Bag("x", 10);
	Bag y = new Bag("y", 10);
	Bag z = new Bag("z", 10);
	Item X = new Item("X", 5);
	Item Y = new Item("Y", 3);
	Item Z = new Item("Z", 4);

	Bag[] bags;
	Item[] items;

	@Before
	public void setUp() throws Exception {
		bags = new Bag[] { x, y, z };
		items = new Item[] { X, Y, Z };
		State.initialize(bags, items);
	}

	@Test
	public void testInitMutuallyExclusiveConstraint() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(X, Y,
				x, y);

		assertEquals("X", mutuallyExclusive.itemA.name);
		assertEquals("Y", mutuallyExclusive.itemB.name);
		assertEquals("x", mutuallyExclusive.bagA.name);
		assertEquals("y", mutuallyExclusive.bagB.name);

	}

	@Test
	public void testXinbag_x_BeforeYinbag_y() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(X, x);
		assertFalse(mutuallyExclusive.isValid(s, y, Y));
	}

	@Test
	public void testXinbag_x_BeforeYinbag_x() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(X, x);
		assertTrue(mutuallyExclusive.isValid(s, x, Y));
	}

	@Test
	public void testYinbag_y_BeforeXinbag_x() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(Y, y);
		assertFalse(mutuallyExclusive.isValid(s, x, X));
	}

	@Test
	public void testYinbag_y_BeforeXinbag_y() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(Y, y);
		assertTrue(mutuallyExclusive.isValid(s, y, X));
	}

	@Test
	public void testYinbag_z_BeforeXinbag_x() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(Y, z);
		assertTrue(mutuallyExclusive.isValid(s, x, X));
	}

	@Test
	public void testXinbag_z_BeforeYinbag_y() {
		MutuallyExclusiveBinary mutuallyExclusive = new MutuallyExclusiveBinary(
				X, Y, x, y);
		State s = new State(bags, items);

		s.addItemToBag(X, z);
		assertTrue(mutuallyExclusive.isValid(s, y, Y));
	}

}

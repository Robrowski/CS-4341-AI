package common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constraints.Constraint;
import constraints.EqualBinary;
import constraints.UnaryInclusive;

public class ConstraintManagerTest {

	Bag a = new Bag("a", 10, 1, 3);
	Bag b = new Bag("b", 10, 1, 3);
	Bag c = new Bag("c", 10, 0, 2);
	Bag d = new Bag("d", 3, 0, 1);
	Item X = new Item("X", 5);
	Item Y = new Item("Y", 3);
	Item Z = new Item("Z", 4);
	Item W = new Item("W", 1);

	Bag[] bags;
	Item[] items;
	Constraint[] constraints;

	@Before
	public void setUp() throws Exception {
		bags = new Bag[] { a, b, c, d };
		items = new Item[] { X, Y, Z, W };
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };
	}

	@Test
	public void testInitConstraintManager() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);

		assertEquals(2, manager.constraints.length);
	}

	@Test
	public void testCanFit() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		assertTrue(manager.canFit(X, a));
	}

	@Test
	public void testCannotFit_weight() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		assertFalse(manager.canFit(X, d));
	}

	@Test
	public void testMultipleItemsCanFit() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		manager.currentState.addItemToBag(X, a);
		boolean result = manager.canFit(Y, a);

		assertTrue(result);

	}

	@Test
	public void testMultpleItemsCannotFit_WeightLimit() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		manager.currentState.addItemToBag(X, a);
		manager.currentState.addItemToBag(Y, a);

		boolean result = manager.canFit(Z, a); // total 12

		assertFalse(result);
	}
	
	@Test
	public void testMultipleItemsCannotFit_BagLimit() {
		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		manager.currentState.addItemToBag(X, c);
		manager.currentState.addItemToBag(Y, c);

		boolean result = manager.canFit(W, c); // upper limit is 2 items

		assertFalse(result);
	}

	@Test
	public void testPlaceItemValidConstraint() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		boolean result = manager.placeItemInBag(Z, b);

		assertTrue(result);

	}

	@Test
	public void testPlaceItemInvalidConstraint() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		boolean result = manager.placeItemInBag(Z, a);

		assertFalse(result);

	}

	@Test
	public void testValidTwoConstraints() {
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		manager.placeItemInBag(Z, b);
		manager.placeItemInBag(X, a);
		boolean result = manager.placeItemInBag(Y, a);

		assertTrue(result);

	}

	@Test
	public void testInvalidTwoConstraints() {
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(bags, items,
				constraints);
		manager.placeItemInBag(Z, b);
		manager.placeItemInBag(X, a);
		boolean result = manager.placeItemInBag(Y, b);

		assertFalse(result);

	}

}

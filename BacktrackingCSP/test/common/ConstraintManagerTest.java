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

	State state;
	Constraint[] constraints;

	@Before
	public void setUp() throws Exception {
		bags = new Bag[] { a, b, c, d };
		items = new Item[] { X, Y, Z, W };
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };
		State.initialize(bags, items);
		state = new State(bags, items);
	}

	@Test
	public void testInitConstraintManager() {
		ConstraintManager manager = new ConstraintManager(constraints);

		assertEquals(2, manager.constraints.length);
	}

	@Test
	public void testCanFit() {
		ConstraintManager manager = new ConstraintManager(constraints);
		assertTrue(manager.canFit(X, a, state));
	}

	@Test
	public void testCannotFit_weight() {
		ConstraintManager manager = new ConstraintManager(constraints);
		assertFalse(manager.canFit(X, d, state));
	}

	@Test
	public void testMultipleItemsCanFit() {
		ConstraintManager manager = new ConstraintManager(constraints);
		manager.placeItemInBag(X, a, state);
		boolean result = manager.canFit(Y, a, state);

		assertTrue(result);

	}

	@Test
	public void testMultpleItemsCannotFit_WeightLimit() {
		ConstraintManager manager = new ConstraintManager(constraints);
		manager.placeItemInBag(X, a, state);
		manager.placeItemInBag(Y, a, state);

		boolean result = manager.canFit(Z, a, state); // total 12

		assertFalse(result);
	}

	@Test
	public void testMultipleItemsCannotFit_BagLimit() {
		ConstraintManager manager = new ConstraintManager(constraints);
		manager.placeItemInBag(X, c, state);
		manager.placeItemInBag(Y, c, state);

		boolean result = manager.canFit(W, c, state); // upper limit is 2 items

		assertFalse(result);
	}

	@Test
	public void testPlaceItemValidConstraint() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		boolean result = manager.placeItemInBag(Z, b, state);

		assertTrue(result);

	}

	@Test
	public void testPlaceItemInvalidConstraint() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		boolean result = manager.placeItemInBag(Z, a, state);

		assertFalse(result);

	}

	@Test
	public void testValidTwoConstraints() {
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.placeItemInBag(Z, b, state);
		manager.placeItemInBag(X, a, state);
		boolean result = manager.placeItemInBag(Y, a, state);

		assertTrue(result);

	}

	@Test
	public void testInvalidTwoConstraints() {
		Constraint XY_BinaryEqual = new EqualBinary(X, Y);
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { XY_BinaryEqual, Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.placeItemInBag(Z, b, state);
		manager.placeItemInBag(X, a, state);
		boolean result = manager.placeItemInBag(Y, b, state);

		assertFalse(result);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testInitUnaryInclusive() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryInclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		 int[][] expectedStateTable = { 
				 { 0, 0, -1, 0 }, 
				 { 0, 0, 0, 0 }, 
				 { 0, 0, -1, 0 },
				 { 0, 0, -1, 0 } };

		// int bagIdx_b = state.bags.get(b);
		// int bagIdx_a = state.bags.get(a);
		// int bagIdx_c = state.bags.get(c);
		// int bagIdx_d = state.bags.get(d);
		// int itemIdx_Z = state.items.get(Z);
		// assertEquals(0, state.stateTable[bagIdx_b][itemIdx_Z]);
		// assertEquals(-1, state.stateTable[bagIdx_a][itemIdx_Z]);
		// assertEquals(-1, state.stateTable[bagIdx_c][itemIdx_Z]);
		// assertEquals(-1, state.stateTable[bagIdx_d][itemIdx_Z]);
		
		assertEquals(expectedStateTable, state.stateTable);
	}

}

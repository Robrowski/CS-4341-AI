package common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import constraints.Constraint;
import constraints.EqualBinary;
import constraints.MutuallyExclusiveBinary;
import constraints.NotEqualBinary;
import constraints.UnaryExclusive;
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

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testInitTwoUnaryInclusive() {
		Constraint Z_UnaryInclusive_b = new UnaryInclusive(Z, b);
		Constraint X_UnaryInclusive_c = new UnaryInclusive(X, c);
		constraints = new Constraint[] { Z_UnaryInclusive_b, X_UnaryInclusive_c };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ -1, 0, -1, 0 }, 
				{ -1, 0, 0, 0 },
				{ 0, 0, -1, 0 }, 
				{ -1, 0, -1, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInitUnaryInclusive_MultipleBags() {
		UnaryInclusive Z_UnaryInclusive_bc = new UnaryInclusive(Z);
		Z_UnaryInclusive_bc.addBag(b);
		Z_UnaryInclusive_bc.addBag(c);
		constraints = new Constraint[] { Z_UnaryInclusive_bc};

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		 int[][] expectedStateTable = { 
				 { 0, 0, -1, 0 }, 
				 { 0, 0, 0, 0 }, 
				 { 0, 0, 0, 0 },
				 { 0, 0, -1, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testInitUnaryExclusive() {
		Constraint Z_UnaryExclusive_b = new UnaryExclusive(Z, b);
		constraints = new Constraint[] { Z_UnaryExclusive_b };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, -1, 0 },
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInitTwoUnaryExclusive() {
		Constraint Z_UnaryExclusive_b = new UnaryExclusive(Z, b);
		UnaryExclusive Y_UnaryExclusive_ad = new UnaryExclusive(Y, a);
		Y_UnaryExclusive_ad.addBag(d);
		constraints = new Constraint[] { Z_UnaryExclusive_b,
				Y_UnaryExclusive_ad };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ 0, -1, 0, 0 }, 
				{ 0, 0, -1, 0 },
				{ 0, 0, 0, 0 }, 
				{ 0, -1, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testInitEqualBinary_NoOtherConstraints() {
		Constraint XY_BinaryEqual = new EqualBinary(X,Y);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testInitEqualBinary_AndUnaryExclusive() {
		Constraint XY_BinaryEqual = new EqualBinary(X,Y);
		Constraint X_UnaryExclusive_a = new UnaryExclusive(X, a);
		constraints = new Constraint[] { XY_BinaryEqual, X_UnaryExclusive_a };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ -1, -1, 0, 0 }, 
				{ 0, 0, 0, 0 },
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testInitEqualBinary_AndUnaryInclusive() {
		Constraint XY_BinaryEqual = new EqualBinary(X,Y);
		Constraint X_UnaryInclusive_a = new UnaryInclusive(X, a);
		constraints = new Constraint[] { XY_BinaryEqual, X_UnaryInclusive_a };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ 0, 0, 0, 0 }, 
				{ -1, -1, 0, 0 },
				{ -1, -1, 0, 0 }, 
				{ -1, -1, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testManyConstraints() {
		Constraint ZY_BinaryEqual = new EqualBinary(Z, Y);
		Constraint XZ_BinaryEqual = new EqualBinary(X,Z);
		Constraint W_UnaryInclusive_c = new UnaryInclusive(W, c);
		UnaryExclusive Y_UnaryExclusive_bd = new  UnaryExclusive(Y,b);
		Y_UnaryExclusive_bd.addBag(d);
		constraints = new Constraint[] { XZ_BinaryEqual, ZY_BinaryEqual,
				W_UnaryInclusive_c, Y_UnaryExclusive_bd };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);

		int[][] expectedStateTable = { 
				{ 0,  0, 0, -1 }, 
				{ -1, -1, -1, -1 },
				{ 0,  0, 0,  0 }, 
				{ -1, -1, -1, -1 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneNotEqualBinary_XbeforeY() {
		Constraint XY_BinaryNotEqual = new NotEqualBinary(X,Y);
		constraints = new Constraint[] { XY_BinaryNotEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(X, c, state);
		manager.forwardCheckUpdate(state, c, X);

		int[][] expectedStateTable = { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 },
				{ 5, -1, 0, 0 }, 
				{ 0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneNotEqualBinary_YbeforeX() {
		Constraint XY_BinaryNotEqual = new NotEqualBinary(X,Y);
		constraints = new Constraint[] { XY_BinaryNotEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(Y, c, state);
		manager.forwardCheckUpdate(state, c, Y);

		int[][] expectedStateTable = { 
				{ 0, 0, 0, 0 }, 
				{ 0, 0, 0, 0 },
				{ -1, 3, 0, 0 }, 
				{ 0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneEqualBinary_XbeforeY() {
		Constraint XY_BinaryEqual = new EqualBinary(X,Y);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(X, c, state);
		manager.forwardCheckUpdate(state, c, X);

		int[][] expectedStateTable = { 
				{ 0, -1, 0, 0 }, 
				{ 0, -1, 0, 0 },
				{ 5,   0, 0, 0 },
				{ 0, -1, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneEqualBinary_YbeforeX() {
		Constraint XY_BinaryEqual = new EqualBinary(X,Y);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(Y, c, state);
		manager.forwardCheckUpdate(state, c, Y);

		int[][] expectedStateTable = { 
				{ -1, 0, 0, 0 }, 
				{ -1, 0, 0, 0 },
				{ 0,  3, 0, 0 },
				{ -1, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneMutuallyExclusive_YbeforeX_differentBags() {
		Constraint XY_BinaryEqual = new MutuallyExclusiveBinary(X, Y, a, b);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(Y, b, state);
		manager.forwardCheckUpdate(state, b, Y);

		int[][] expectedStateTable = { 
				{ -1, 0, 0, 0 }, 
				{  0, 3, 0, 0 },
				{  0, 0, 0, 0 },
				{  0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testOneMutuallyExclusive_YbeforeX_SameBag() {
		Constraint XY_BinaryEqual = new MutuallyExclusiveBinary(X, Y, b, b);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(Y, b, state);
		manager.forwardCheckUpdate(state, b, Y);

		int[][] expectedStateTable = { 
				{  0, 0, 0, 0 }, 
				{ -1, 3, 0, 0 },
				{  0, 0, 0, 0 },
				{  0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testOneMutuallyExclusive_XbeforeY_differentBags() {
		Constraint XY_BinaryEqual = new MutuallyExclusiveBinary(X, Y, a, b);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(X, a, state);
		manager.forwardCheckUpdate(state, a, X);

		int[][] expectedStateTable = { 
				{  5, 0, 0, 0 }, 
				{  0,-1, 0, 0 },
				{  0, 0, 0, 0 },
				{  0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testOneMutuallyExclusive_XbeforeY_sameBag() {
		Constraint XY_BinaryEqual = new MutuallyExclusiveBinary(X, Y, a, a);
		constraints = new Constraint[] { XY_BinaryEqual };

		ConstraintManager manager = new ConstraintManager(constraints);
		manager.initForwardChecking(state);
		manager.placeItemInBag(X, a, state);
		manager.forwardCheckUpdate(state, a, X);

		int[][] expectedStateTable = { 
				{  5,-1, 0, 0 }, 
				{  0, 0, 0, 0 },
				{  0, 0, 0, 0 },
				{  0, 0, 0, 0 } };

		assertEquals(expectedStateTable, state.stateTable);
	}
}

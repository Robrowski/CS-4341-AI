package common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class StateTest {
	Bag a = new Bag("a", 10);
	Bag b = new Bag("b", 10);
	Bag c = new Bag("c", 10);
	Item X = new Item("X", 5);
	Item Y = new Item("Y", 3);
	Item Z = new Item("Z", 3);

	Bag[] bags;
	Item[] items;

	@Before
	public void setUp() throws Exception {

		bags = new Bag[] { a, b, c };
		items = new Item[] { X, Y, Z };
		State.initialize(bags, items);

	}

	@Test
	public void initStateTest() {
		State newState = new State(bags, items);
		assertEquals(bags.length, newState.stateTable.length);
		assertEquals(items.length, newState.stateTable[0].length);
	}

	@Test
	public void addItemXtoBag_a() {
		State newState = new State(bags, items);

		newState.addItemToBag(X, a);
		Bag in = newState.inBag(X);
		assertEquals("a", in.name);
	}

	@Test
	public void checkItemXInNoBags() {
		State newState = new State(bags, items);

		Bag in = newState.inBag(X);
		assertEquals(null, in);
	}

	@Test
	public void mcvEmptyStateTable() {
		State newState = new State(bags, items);

		ArrayList<Item> mostConstrained = newState.getMostConstrainedVariable();
		assertEquals(bags.length, mostConstrained.size());
	}

	@Test
	public void mcvOneImpossibleMove() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, 0, 0 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;

		ArrayList<Item> mostConstrained = newState.getMostConstrainedVariable();

		assertEquals(1, mostConstrained.size());
		assertEquals("Z", mostConstrained.get(0).name);

	}

	@Test
	public void mcvTwoImpossibleMoves_TIE() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, -1, 0 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;

		ArrayList<Item> mostConstrained = newState.getMostConstrainedVariable();

		assertEquals(2, mostConstrained.size());
		assertTrue(mostConstrained.contains(Z));
		assertTrue(mostConstrained.contains(Y));

	}

	@Test
	public void mcvThreeImpossibleMoves_noTie() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, -1, -1 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;

		ArrayList<Item> mostConstrained = newState.getMostConstrainedVariable();

		assertEquals(1, mostConstrained.size());
		assertEquals("Z", mostConstrained.get(0).name);
	}

	@Test
	public void lcvEmptyStateTable() {
		State newState = new State(bags, items);

		ArrayList<Bag> leastConstrained = newState.getLeastConstrainedValue();
		assertEquals(items.length, leastConstrained.size());
	}

	@Test
	public void lcvOneImpossibleMove_TIE() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, 0, 0 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;
		ArrayList<Bag> leastConstrained = newState.getLeastConstrainedValue();
		assertEquals(3, leastConstrained.size());
		assertTrue(leastConstrained.contains(b));
		assertTrue(leastConstrained.contains(c));

	}

	@Test
	public void lcvTwoImpossibleMoves_noTie() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, 0, -1 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;
		ArrayList<Bag> leastConstrained = newState.getLeastConstrainedValue();
		assertEquals(3, leastConstrained.size());
		assertTrue(leastConstrained.contains(c));

	}
	
	@Test
	public void lcvTwoItemPlaced_noTie() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, 20 }, { 10, 0, 0 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;
		ArrayList<Bag> leastConstrained = newState.getLeastConstrainedValue();
		assertEquals(3, leastConstrained.size());
		assertTrue(leastConstrained.contains(c));

	}

	@Test
	public void lcvOrderTest() {
		State newState = new State(bags, items);
		int[][] newStateTable = { { 0, 0, -1 }, { 0, -1, -1 }, { 0, 0, 0 } };

		newState.stateTable = newStateTable;
		ArrayList<Bag> leastConstrained = newState.getLeastConstrainedValue();

		assertEquals(3, leastConstrained.size());
		assertEquals(c, leastConstrained.get(0));
		assertEquals(a, leastConstrained.get(1));
		assertEquals(b, leastConstrained.get(2));
	}
}

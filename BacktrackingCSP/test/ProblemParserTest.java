import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import common.Bag;
import common.Item;

import constraints.Constraint;

public class ProblemParserTest {

	/* Input 1 properly parsed */
	List<Constraint> constraints = new ArrayList<Constraint>();
	List<Item> items = new ArrayList<Item>();
	List<Bag> bags = new ArrayList<Bag>();

	@Before
	public void setUp() {
		items.add(new Item("D", 14));
		items.add(new Item("C", 14));

		bags.add(new Bag("p", 15));
		bags.add(new Bag("q", 15));

	}

	@Test
	public void test_read_items(){
		ProblemParser cp = new ProblemParser("sample_logs/input1.txt", false);
		cp.parse();

		
		assertEquals("Didn't even get the right number of items", items.size(),
				cp.items.size());
		
		for (Item i : items) {
			assertTrue(cp.items.values().contains(i));
		}
	}

	@Test
	public void test_read_bags() {
		ProblemParser cp = new ProblemParser("sample_logs/input1.txt", false);
		cp.parse();

		assertEquals("Didn't even get the right number of items", bags.size(),
				cp.bags.size());

		for (Bag i : bags) {
			assertTrue(cp.bags.values().contains(i));
		}
	}


	
	@Test
	public void test_read_constraints() {
		ProblemParser cp = new ProblemParser("sample_logs/input1.txt", false);
		cp.parse();

		assertEquals("Didn't even get the right number of items",
 2,
				cp.constraints.size());

		// TODO check that the constraints are all good
	}

	@Test
	public void test_read_items2() {
		ProblemParser cp = new ProblemParser("sample_logs/input2.txt", false);
		cp.parse();

		assertEquals("Didn't get the right number of items", 1,
				cp.items.size());

		cp = new ProblemParser("sample_logs/input3.txt", false);
		cp.parse();

		assertEquals("Didn't get the right number of items", 0, cp.items.size());

	}

	@Test
	public void test_read_bags2() {
		ProblemParser cp = new ProblemParser("sample_logs/input2.txt", false);
		cp.parse();

		assertEquals("Didn't get the right number of bags", 2,
				cp.bags.size());

		cp = new ProblemParser("sample_logs/input3.txt", false);
		cp.parse();

		assertEquals("Didn't get the right number of bags", 0, cp.bags.size());
	}

	@Test
	public void test_read_big_file() {
		ProblemParser cp = new ProblemParser("sample_logs/input7_rob.txt",
				false);
		cp.parse();

		assertEquals("Didn't get the right number of items", 6, cp.items.size());
		assertEquals("Didn't get the right number of bags", 8, cp.bags.size());
		assertEquals("Didn't get the right number of constraints", 6,
				cp.constraints.size());
	}

}

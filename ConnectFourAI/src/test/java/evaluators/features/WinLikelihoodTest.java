package evaluators.features;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import evaluators.Evaluator;
import evaluators.EvaluatorFactory;

public class WinLikelihoodTest {
	
	Evaluator winLikelihoodEvaluator;

	@Before
	public void setUp() throws Exception {
		EvaluatorFactory factory = new EvaluatorFactory();
		String[] winLikelihood = {"WinLikelihood"};
		winLikelihoodEvaluator = factory.makeEvaluator(winLikelihood);
	}

	@Test
	public void test_empty_1x1_connect1() {
		fail("Not yet implemented");
	}

}

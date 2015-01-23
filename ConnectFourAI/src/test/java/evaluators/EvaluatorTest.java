package evaluators;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EvaluatorTest {
	
	EvaluatorFactory factory;
	
	@Rule
    public ExpectedException thrown= ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		factory = new EvaluatorFactory();
	}

	@Test
	public void test_make_empty_evaluator() {
		thrown.expectMessage("No Features Given");
		String[] emptyStringArr = new String[0];
		Evaluator newEval = factory.makeEvaluator(emptyStringArr, 1);
	}
	
	@Test
	public void test_make_invalid_feature_eval() {
		thrown.expectMessage("Unrecognized");
		String[] unrecognizedFeature = {"Unrecognized"};
		Evaluator newEval = factory.makeEvaluator(unrecognizedFeature, 1);
	}
	
	@Test
	public void test_make_1_valid_feature_eval() {
		String[] oneFeature = {"WinLikelihood"};
		Evaluator newEval = factory.makeEvaluator(oneFeature, 1);
		assertEquals(newEval.getFeatures().length,1);
	}
	
}

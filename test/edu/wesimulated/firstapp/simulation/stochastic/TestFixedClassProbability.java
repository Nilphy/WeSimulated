package edu.wesimulated.firstapp.simulation.stochastic;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Assert;
import org.junit.Test;

public class TestFixedClassProbability {

	@Test
	public void doublesStringsYMaps() {
		Map<String, String> classProbability = new TreeMap<>();
		classProbability.put("0.5", "classTest");
		String testDouble = "0.5";
		Double number = Double.parseDouble(testDouble);
		System.out.println(number.toString());
		Assert.assertEquals("classTest", classProbability.get(number.toString()));
	}
	
	@Test
	public void evaluationOfAccumulatedProbabilityFunction() {
		Map<String, String> configValues = new HashMap<>();
		configValues.put("dog", "0.7");
		configValues.put("cat", "0.1");
		configValues.put("bird", "0.2");
		StochasticMethodConfig config = new StochasticMethodConfig(configValues);
		FixedClassProbability instanceToTest = new FixedClassProbability(config, "test");
		String calculatedClassification = instanceToTest.findClassificationForAccumulatedProbability(0.05).getClassifictation().getName();
		Assert.assertTrue(calculatedClassification.equals("cat"));
		calculatedClassification = instanceToTest.findClassificationForAccumulatedProbability(0.15).getClassifictation().getName();
		Assert.assertTrue(calculatedClassification.equals("bird"));
		calculatedClassification = instanceToTest.findClassificationForAccumulatedProbability(0.9).getClassifictation().getName();
		Assert.assertTrue(calculatedClassification.equals("dog"));
	}
}

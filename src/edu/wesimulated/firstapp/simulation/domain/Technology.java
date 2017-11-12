package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.wesimulated.simulationmotor.DateUtils;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Technology implements NumericallyModeledEntity {

	static public final Date PHP_START_DATE = DateUtils.dayStringToDate("1995-06-08");
	static public final Float PHP_LEARNING_CURVE = 0.06f;
	static public final Float PHP_VERBOSITY = 0.05f;
	static public final Date JAVA_START_DATE = DateUtils.dayStringToDate("1996-01-23");
	static public final Float JAVA_LEARNING_CURVE = 0.04f;
	static public final Float JAVA_VERBOSITY = 0.07f;
	static public final Date JAVASCRIPT_START_DATE = DateUtils.dayStringToDate("1996-03-01");
	static public final Float JAVASCRIPT_LEARNING_CURVE = 0.05f;
	static public final Float JAVASCRIPT_VERBOSITY = 0.05f;

	private Long monthsInTheIndustry;
	private Float learningCurveModOne;
	private Float verbosityModOne;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> out = new HashMap<>();
		out.put(TechnologyCharacteristic.MONTHS_IN_THE_INDUSTRY, new EntryValue(Type.Long, this.getMonthsInTheIndustry()));
		out.put(TechnologyCharacteristic.LEARNING_CURVE_MOD_ONE, new EntryValue(Type.Float, this.getLearningCurveModOne()));
		out.put(TechnologyCharacteristic.VERBOSITY_MOD_ONE, new EntryValue(Type.Float, this.getVerbosityModOne()));
		return out;
	}

	public Long getMonthsInTheIndustry() {
		return monthsInTheIndustry;
	}

	public void setMonthsInTheIndustry(Long monthsInTheIndustry) {
		this.monthsInTheIndustry = monthsInTheIndustry;
	}

	public Float getLearningCurveModOne() {
		return learningCurveModOne;
	}

	public void setLearningCurveModOne(Float learningCurveModOne) {
		this.learningCurveModOne = learningCurveModOne;
	}

	public Float getVerbosityModOne() {
		return verbosityModOne;
	}

	public void setVerbosityModOne(Float verbosityModOne) {
		this.verbosityModOne = verbosityModOne;
	}
}

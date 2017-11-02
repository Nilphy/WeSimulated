package edu.wesimulated.firstapp.simulation.domain;

import java.util.Map;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;

public class Technology implements NumericallyModeledEntity {

	private Integer monthsInTheIndustry;
	private Float learningCurveModOne;
	private Float verbosityModeOne;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		// TODO extractValues
		return null;
	}

}

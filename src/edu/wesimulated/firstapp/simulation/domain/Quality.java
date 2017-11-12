package edu.wesimulated.firstapp.simulation.domain;

import java.util.HashMap;
import java.util.Map;

import edu.wesimulated.firstapp.simulation.stochastic.EntryValue;
import edu.wesimulated.firstapp.simulation.stochastic.NumericallyModeledEntity;
import edu.wesimulated.firstapp.simulation.stochastic.EntryValue.Type;

public class Quality implements NumericallyModeledEntity {

	public static final Float COVERAGE = 0.1f;
	public static final Float PERCENTAJE_CRITICAL_BUGS = 0.05f;
	public static final Float PERCENTAJE_MINOR_BUGS = 0.2f;
	private Float percentajeOfCriticalBugs;
	private Float percentajeOfMinorBugs;
	private Float coverage;

	@Override
	public Map<Characteristic, EntryValue> extractValues() {
		Map<Characteristic, EntryValue> values = new HashMap<>();
		values.put(ProjectCharacteristic.PERCENTAJE_CRITICAL_BUGS, new EntryValue(Type.FLOAT, this.percentajeOfCriticalBugs));
		values.put(ProjectCharacteristic.PERCENTAJE_MINOR_BUGS, new EntryValue(Type.FLOAT, this.percentajeOfMinorBugs));
		values.put(ProjectCharacteristic.CONVERAGE, new EntryValue(Type.FLOAT, this.coverage));
		return values;
	}

	public Float getCoverage() {
		return coverage;
	}

	public void setCoverage(Float coverage) {
		this.coverage = coverage;
	}

	public Float getPercentajeOfMinorBugs() {
		return percentajeOfMinorBugs;
	}

	public void setPercentajeOfMinorBugs(Float percentajeOfMinorBugs) {
		this.percentajeOfMinorBugs = percentajeOfMinorBugs;
	}

	public Float getPercentajeOfCriticalBugs() {
		return percentajeOfCriticalBugs;
	}

	public void setPercentajeOfCriticalBugs(Float percentajeOfCriticalBugs) {
		this.percentajeOfCriticalBugs = percentajeOfCriticalBugs;
	}

}

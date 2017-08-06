package edu.wesimulated.firstapp.simulation.domain.avature.highlyinterruptiblerole;

import com.wesimulated.simulationmotor.des.Prioritized.Priority;

public class MessageValuator {

	private int lowBoundary;
	private int mediumBoundary;

	public MessageValuator(int lowBoundary, int mediumBoundary) {
		super();
		this.lowBoundary = lowBoundary;
		this.mediumBoundary = mediumBoundary;
	}

	public enum ValueLevel {
		LOW, MED, HIGH;
	}

	public ValueLevel fromMinutes(Integer minutes) {
		if (minutes < this.lowBoundary) {
			return ValueLevel.LOW;
		}
		if (minutes < this.mediumBoundary) {
			return ValueLevel.MED;
		}
		return ValueLevel.HIGH;
	}

	public Float getAssociatedPriority(Integer minutes) {
		ValueLevel antiquity = this.fromMinutes(minutes);
		return getAssociatedPriority(antiquity);
	}

	static public Float getAssociatedPriority(ValueLevel antiquity) {
		switch (antiquity) {
		case LOW:
			return Priority.LOW.get();
		case MED:
			return Priority.MED.get();
		case HIGH:
			return Priority.HIGH.get();
		default:
			throw new IllegalStateException("The priority given has not been considered in getAssociatedPriority method");
		}
	}

	public int getMinutes(ValueLevel antiquity) {
		switch (antiquity) {
		case LOW:
			return this.lowBoundary;
		case MED:
			return this.mediumBoundary;
		default:
			throw new UnsupportedOperationException();
		}
	}
}

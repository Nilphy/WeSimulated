package edu.wesimulated.firstapp.simulation.domain.mywork.highlyinterruptiblerole;

import com.wesimulated.simulationmotor.des.Prioritized;

public interface HighlyInterruptibleRolePrioritized extends Prioritized {

	public Float getMaxPriority();
	
	public default Float calculateEscalatedPriority() {
		return this.escalatePriority(this.calculatePriority());
	}
	
	default Float escalatePriority(Float priority) {
		return priority * this.getMaxPriority();
	}
}

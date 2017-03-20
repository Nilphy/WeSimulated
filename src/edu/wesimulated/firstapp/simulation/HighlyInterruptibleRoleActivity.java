package edu.wesimulated.firstapp.simulation;

import java.util.Date;

import com.wesimulated.simulationmotor.des.Prioritized;

public interface HighlyInterruptibleRoleActivity extends Prioritized {

	public Date process(HighlyInterruptibleRole highlyInterruptibleRole);

	public Float MaxPriority();
	
	public default Float calculateEscalatedPriority() {
		return this.escalatePriority(this.calculatePriority());
	}
	
	default Float escalatePriority(Float priority) {
		return priority * this.MaxPriority();
	}
}

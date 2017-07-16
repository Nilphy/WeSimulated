package edu.wesimulated.firstapp.simulation.domain;

import com.wesimulated.simulationmotor.des.COperation;

/**
 * The difference between a maintenance task and a risk is that maintenance
 * tasks use to happen more than once. When an ocurrence ends it calculates if
 * another one is added to the project for the future
 * 
 * @author Carolina
 *
 */
public abstract class MaintenanceTask extends COperation {
	
	public abstract long getPeriodInMinutes();
}

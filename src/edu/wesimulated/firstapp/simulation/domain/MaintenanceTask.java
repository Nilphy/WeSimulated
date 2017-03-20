package edu.wesimulated.firstapp.simulation.domain;

import java.util.Date;

import com.wesimulated.simulationmotor.des.COperation;

/**
 * The difference between a maintenance task and a risk is that maintenance
 * tasks use to happen more than once. When an ocurrence ends it calculates if
 * another one is added to the project for the future
 * 
 * @author Carolina
 *
 */
public class MaintenanceTask extends COperation {

	@Override
	public boolean testIfRequirementsAreMet() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void doAction() {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getDateOfOccurrence() {
		// TODO Auto-generated method stub
		return null;
	}

}

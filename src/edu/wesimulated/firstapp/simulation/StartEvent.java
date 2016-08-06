package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;


public class StartEvent extends SimulationEvent {

	@Override
	public void updateSimulation(OperationBasedSimulator personRolSimulator, AbstractFederate personFederate) {
		if (personRolSimulator != null) {
			personRolSimulator.startExecution();
		}
	}
}

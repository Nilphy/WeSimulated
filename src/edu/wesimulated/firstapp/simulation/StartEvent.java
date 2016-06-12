package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;


public class StartEvent extends SimulationEvent {

	@Override
	public void updateSimulation(Simulator personRolSimulator, AbstractFederate personFederate) {
		if (personRolSimulator != null) {
			personRolSimulator.startExecution();
		}
	}
}

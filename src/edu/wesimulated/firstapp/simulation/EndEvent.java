package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.exception.SimulationStillRunningException;

public class EndEvent extends SimulationEvent {

	@Override
	public void updateSimulation(PersonRolSimulator personRolSimulator, AbstractFederate abstractFederate) {
		if (personRolSimulator != null && personRolSimulator.isRunning()) {
			throw new SimulationStillRunningException();
		}
		abstractFederate.resignFromFederation();
	}
}

package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.exception.SimulationStillRunningException;

public class EndEvent extends SimulationEvent {

	@Override
	public void updateSimulation(PersonSimulator personSimulator, AbstractFederate abstractFederate) {
		if (personSimulator != null && personSimulator.isRunning()) {
			throw new SimulationStillRunningException();
		}
		abstractFederate.resignFromFederation();
	}
}

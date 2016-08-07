package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulationmotor.des.OperationBasedSimulator;

import edu.wesimulated.firstapp.simulation.exception.SimulationStillRunningException;
import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;

public class EndEvent extends SimulationEvent {

	@Override
	public void updateSimulation(OperationBasedSimulator personRolSimulator, AbstractFederate abstractFederate) {
		if (personRolSimulator != null && personRolSimulator.isRunning()) {
			throw new SimulationStillRunningException();
		}
		abstractFederate.resignFromFederation();
	}
}

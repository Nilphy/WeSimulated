package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseExecutor;
import com.wesimulated.simulation.BaseSimulator;

import edu.wesimulated.firstapp.simulation.exception.SimulationStillRunningException;
import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;

public class EndEvent<T extends  BaseExecutor> extends SimulationEvent<T> {

	@Override
	public void updateSimulation(BaseSimulator<T> personRolSimulator, AbstractFederate abstractFederate) {
		if (personRolSimulator != null && personRolSimulator.isRunning()) {
			throw new SimulationStillRunningException();
		}
		abstractFederate.resignFromFederation();
	}
}

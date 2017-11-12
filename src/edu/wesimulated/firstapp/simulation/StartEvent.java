package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseExecutor;
import com.wesimulated.simulation.BaseSimulator;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;


public class StartEvent<T extends BaseExecutor> extends SimulationEvent<T> {

	@Override
	public void updateSimulation(BaseSimulator<T> personRolSimulator, AbstractFederate personFederate) {
		if (personRolSimulator != null) {
			personRolSimulator.startExecution();
		}
	}
}

package edu.wesimulated.firstapp.simulation;

import com.wesimulated.simulation.BaseSimulator;
import com.wesimulated.simulationmotor.des.OperationBasedSimulator;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;


public class StartEvent extends SimulationEvent {

	@Override
	public void updateSimulation(BaseSimulator personRolSimulator, AbstractFederate personFederate) {
		if (personRolSimulator != null) {
			personRolSimulator.startExecution();
		}
	}
}

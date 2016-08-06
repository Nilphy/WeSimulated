package edu.wesimulated.firstapp.simulation;

import edu.wesimulated.firstapp.simulation.hla.AbstractFederate;

public abstract class SimulationEvent {

	public final static SimulationEvent buildStartEvent() {
		return new StartEvent();
	}

	public final static SimulationEvent buildEndEvent() {
		return new EndEvent();
	}

	public abstract void updateSimulation(OperationBasedSimulator personRolSimulator, AbstractFederate personFederate);
}


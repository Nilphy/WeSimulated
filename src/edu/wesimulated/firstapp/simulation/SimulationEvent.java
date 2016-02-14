package edu.wesimulated.firstapp.simulation;

public abstract class SimulationEvent {

	public final static SimulationEvent buildStartEvent() {
		return new StartEvent();
	}

	public final static SimulationEvent buildEndEvent() {
		return new EndEvent();
	}

	public abstract void updateSimulation(Simulator personRolSimulator, AbstractFederate personFederate);
}


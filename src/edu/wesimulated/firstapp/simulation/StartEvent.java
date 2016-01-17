package edu.wesimulated.firstapp.simulation;


public class StartEvent extends SimulationEvent {

	@Override
	public void updateSimulation(PersonSimulator personSimulator, AbstractFederate personFederate) {
		personSimulator.startExecution();
	}
}

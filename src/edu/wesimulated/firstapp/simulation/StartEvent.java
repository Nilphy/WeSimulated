package edu.wesimulated.firstapp.simulation;


public class StartEvent extends SimulationEvent {

	@Override
	public void updateSimulation(Simulator personRolSimulator, AbstractFederate personFederate) {
		if (personRolSimulator != null) {
			personRolSimulator.startExecution();
		}
	}
}

package edu.wesimulated.firstapp.simulation.domain;

import edu.wesimulated.firstapp.model.SimulationEntity;

public interface Populatable extends Identifiable {

	public void populateFrom(SimulationEntity simulationEntity, SimulatorFactory factory);
}
